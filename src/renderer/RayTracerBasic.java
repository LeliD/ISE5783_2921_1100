/**
 * 
 */
package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Ray;
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
		return intersections == null ? scene.background : calcColor(ray.findClosestGeoPoint(intersections));
	}

	/**
	 * 
	 * Calculates the color at a given point in the scene.
	 * 
	 * @param point The point to calculate the color for
	 * @return The color at the given point
	 */
	private Color calcColor(GeoPoint p) {
		return scene.ambientLight.getIntensity().add(p.geometry.getEmission());
	}
}
