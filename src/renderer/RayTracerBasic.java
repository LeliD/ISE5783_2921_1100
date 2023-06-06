/**
 * 
 */
package renderer;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

/**
 * RayTracerBasic class extends RayTracerBase abstract class
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class RayTracerBasic extends RayTracerBase {
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final Double3 INITIAL_K = new Double3(1.0);

	/**
	 * 
	 * Constructs a RayTracerBasic object with the specified scene.
	 * 
	 * @param scene The scene to be rendered
	 */
	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		// return either background if there aren't intersections or the color of the
		// closestPoint among the intersection points
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
	}

	/**
	 * 
	 * Finds the closest intersection point between a given ray and the geometries
	 * in the scene.
	 * 
	 * @param ray The ray to find intersections with.
	 * @return The closest intersection point as a GeoPoint object, or null if no
	 *         intersections are found.
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		if (ray == null)
			return null;
		var intersections = scene.geometries.findGeoIntersections(ray);
		return intersections == null ? null : //
				ray.findClosestGeoPoint(intersections);
	}

	/**
	 * 
	 * Calculates the color at a given intersection point based on local and global
	 * effects.
	 * 
	 * @param geoPoint The intersection point to calculate the color for.
	 * @param ray      The ray that intersected with the geometry.
	 * @return The calculated color at the intersection point.
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
	}

	/**
	 * 
	 * Calculates the color at a given intersection point, considering local and
	 * global effects recursively.
	 * 
	 * @param intersection The intersection point to calculate the color for.
	 * @param ray          The ray that intersected with the geometry.
	 * @param level        The current recursion level.
	 * @param k            The accumulation factor for global effects.
	 * @return The calculated color at the intersection point.
	 */
	private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(intersection, ray, k);// add local lighting effects
		return 1 == level ? color//
				: color.add(calcGlobalEffects(intersection, ray, level, k));// add global lighting effects
	}

	/**
	 * 
	 * Calculates the global effects (reflection and transparency) at a given
	 * intersection point.
	 * 
	 * @param gp    The intersection point to calculate the global effects for.
	 * @param ray   The ray that intersected with the geometry.
	 * @param level The current recursion level.
	 * @param k     The accumulation factor for global effects.
	 * @return The calculated color from global effects at the intersection point.
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		return calcGlobalEffect(constructReflectionRay(gp.point, v, n), level, k, material.kR)//
				.add(calcGlobalEffect(constructTransparencyRay(gp.point, v, n), level, k, material.kT));
	}

	/**
	 * 
	 * Calculates the global effect (reflection or transparency) for a given ray.
	 * 
	 * @param ray   The ray to calculate the global effect for.
	 * @param level The current recursion level.
	 * @param k     The accumulation factor for global effects.
	 * @param kx    The reflection or transparency factor for the material.
	 * @return The calculated color from the global effect.
	 */
	private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
		Double3 kkx = k.product(kx);
		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;
		GeoPoint gp = findClosestIntersection(ray);
		if (gp == null)// if there arn't intersection points with the secondary ray
			return scene.background.scale(kx);
		return isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK
				: calcColor(gp, ray, level - 1, kkx).scale(kx);
	}

	/**
	 * 
	 * Calculates the local lighting effects (diffuse, specular and emission) at a
	 * given intersection point.
	 * 
	 * @param gp  the intersection point
	 * @param ray the ray that intersected with the scene
	 * @param k   The transparency coefficient.
	 * @return the color resulting from the local lighting effects at the
	 *         intersection point
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
		Color color = gp.geometry.getEmission();
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = alignZero(n.dotProduct(v));
		if (nv == 0)
			return scene.background;
		Material material = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = l == null ? 0 : alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				Double3 ktr = transparency(gp, lightSource, l, n);
				// if ktr isn't greater than the minimum k - the
				if (ktr.product(k).greaterThan(MIN_CALC_COLOR_K)) {
					// point is completely shaded
					Color iL = lightSource.getIntensity(gp.point).scale(ktr);
					color = color.add( //
							iL.scale(calcDiffusive(material, nl)), //
							iL.scale(calcSpecular(material, n, l, v, nl)));
				}

			}
		}
		return color;
	}

	/**
	 * 
	 * Calculates the specular reflection color for a given material, normal, light
	 * direction, view direction, and light-normal dot product.
	 * 
	 * @param material the material properties of the geometry
	 * @param n        the surface normal vector
	 * @param l        the direction vector from the light source to the point
	 * @param nl       the dot product of the surface normal and light direction
	 *                 vectors
	 * @param v        the view direction vector
	 * @return the specular reflection color
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, Vector v, double nl) {
		Vector r = l.add(n.scale(-2 * nl));
		double minusVr = -alignZero(r.dotProduct(v));
		return minusVr <= 0 ? Double3.ZERO //
				: material.kS.scale(Math.pow(minusVr, material.nShininess));
	}

	/**
	 * 
	 * Calculates the diffuse reflection color for a given material and light-normal
	 * dot product.
	 * 
	 * @param material the material properties
	 * @param nl       the dot product of the surface normal and light direction
	 *                 vectors
	 * @return the diffuse reflection color
	 */
	private Double3 calcDiffusive(Material material, double nl) {
		return material.kD.scale(nl < 0 ? -nl : nl);

	}

	/**
	 * 
	 * Determines if a point is unshaded by a light source.
	 * 
	 * @param lightSource The light source
	 * @param gp          The geometric point
	 * @param l           The light direction vector
	 * @param n           The surface normal vector
	 * @return true if the point is unshaded, false otherwise
	 */
	@SuppressWarnings("unused")
	private boolean unshaded(LightSource lightSource, GeoPoint gp, Vector l, Vector n) {
		Vector lightDirection = l.scale(-1);
		// Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
		// Point point = gp.point.add(delta);
		Ray shadowRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(shadowRay);
		if (intersections == null)
			return true;
		double lightDistance = lightSource.getDistance(gp.point);
		for (GeoPoint g : intersections) {
			if (alignZero(g.point.distance(shadowRay.getP0()) - lightDistance) <= 0 //
					&& g.geometry.getMaterial().kT == Double3.ZERO)
				return false;
		}
		return true;
	}

	/**
	 * Calculates the transparency coefficient for a given intersection point with a
	 * light source.
	 *
	 * @param gp          The GeoPoint representing the intersection point.
	 * @param lightSource The light source illuminating the intersection point.
	 * @param l           The direction vector from the intersection point to the
	 *                    light source.
	 * @param n           The surface normal vector at the intersection point.
	 * @return The transparency coefficient.
	 */
	private Double3 transparency(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {
		Vector lightDirection = l.scale(-1);
		Ray shadowRay = new Ray(gp.point, lightDirection, n);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(shadowRay);
		Double3 ktr = Double3.ONE;
		if (intersections == null)
			return ktr;
		double lightDistance = lightSource.getDistance(gp.point);
		for (GeoPoint g : intersections) {
			// if the intersection is before the light source
			if (alignZero(g.point.distance(shadowRay.getP0()) - lightDistance) <= 0) {
				ktr = ktr.product(g.geometry.getMaterial().kT);
				// if ktr is smaller than the minimum k - the point completely
				// shaded and there is no need to continue with the loop
				if (ktr.lowerThan(MIN_CALC_COLOR_K))
					return Double3.ZERO;
			}
		}
		return ktr;
	}

	/**
	 * Constructs a transparency ray given a point, direction vector, and surface
	 * normal.
	 *
	 * @param p The point representing the intersection point.
	 * @param v The direction vector to the intersection point.
	 * @param n The surface normal vector at the intersection point.
	 * @return The transparency ray which it's head moved by DELTA length to the
	 */
	private Ray constructTransparencyRay(Point p, Vector v, Vector n) {
		return new Ray(p, v, n);
	}

	/**
	 * Constructs a reflection ray given a point, direction vector, and surface
	 * normal.
	 *
	 * @param p The point representing the intersection point.
	 * @param v The direction vector of the ray.
	 * @param n The surface normal vector at the intersection point.
	 * @return The reflection ray.
	 */
	private Ray constructReflectionRay(Point p, Vector v, Vector n) {
		double vn = v.dotProduct(n);
		if (isZero(vn))
			return null;// r?
		Vector reflectionDirection = (v.subtract(n.scale(2 * vn))).normalize();
		return new Ray(p, reflectionDirection, n);

	}
}
