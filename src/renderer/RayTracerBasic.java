/**
 * 
 */
package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Ray;
import static primitives.Util.*;
import primitives.Vector;
import scene.Scene;

/**
 * RayTracerBasic class extends RayTracerBase abstract class
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class RayTracerBasic extends RayTracerBase {

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
		var intersections = scene.geometries.findGeoIntersections(ray);
		// return either background if there aren't intersections or the color of the
		// closestPoint among the intersection points
		return intersections == null ? scene.background : calcColor(ray.findClosestGeoPoint(intersections), ray);
	}

	/**
	 * 
	 * Calculates the color at a given point in the scene.
	 * @param intersection The point to calculate the color for
	 * @param ray the ray that intersected with the scene
	 * 
	 * @return The color at the given point
	 */
	private Color calcColor(GeoPoint intersection, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(intersection, ray));
	}

	/**
	 * 
	 * Calculates the local effects (diffuse, specular and emission) at a given
	 * intersection point.
	 * 
	 * @param gp  the intersection point
	 * @param ray the ray that intersected with the scene
	 * @return the color representing the local effects at the intersection point
	 */
	private Color calcLocalEffects(GeoPoint gp, Ray ray) {
		Color color = gp.geometry.getEmission();
		Vector v = ray.getDir();
		Vector n = gp.geometry.getNormal(gp.point);
		double nv = alignZero(n.dotProduct(v));
		double nl;
		Color iL;
		Vector l;
		if (nv == 0)
			return Color.BLACK;
		Material material = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			l = lightSource.getL(gp.point);
			nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				iL = lightSource.getIntensity(gp.point);
				color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, v, nl)));
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
		if (nl < 0)
			nl = -nl;
		return material.kD.scale(nl);

	}
}
