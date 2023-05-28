/**
 * 
 */
package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Util;

import static primitives.Util.*;

import java.util.List;

import primitives.Vector;
import scene.Scene;

/**
 * RayTracerBasic class extends RayTracerBase abstract class
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class RayTracerBasic extends RayTracerBase {
	private static final double DELTA = 0.1;

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
	 * 
	 * @param intersection The point to calculate the color for
	 * @param ray          the ray that intersected with the scene
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
		if (nv == 0)
			return Color.BLACK;
		Material material = gp.geometry.getMaterial();
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(gp.point);
			double nl = l == null ? 0 : alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				if (unshaded(lightSource,gp,l,n)) {
					Color iL = lightSource.getIntensity(gp.point);
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
	private boolean unshaded(LightSource lightSource, GeoPoint gp, Vector l, Vector n) {
		Vector lightDirection = l.scale(-1);
		Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
		Point point = gp.point.add(delta);
		Ray shadowRay = new Ray(point, lightDirection);
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(shadowRay);
		if (intersections == null)
			return true;
		double lightDistance = lightSource.getDistance(gp.point);
		for (GeoPoint g : intersections) {
			if (alignZero(g.point.distance(shadowRay.getP0()) - lightDistance) <= 0)
				return false;
		}
		return true;
	}

}
