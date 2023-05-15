/**
 * 
 */
package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point;
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
		List<Point> intersections = scene.geometries.findIntersections(ray);
		if (intersections == null)
			return scene.background;
		return calcColor(ray.findClosestPoint(intersections));// the color of the closestPoint among the intersection
																// points
	}

	/**
	 * 
	 * Calculates the color at a given point in the scene.
	 * 
	 * @param point The point to calculate the color for
	 * @return The color at the given point
	 */
	private Color calcColor(Point point) {
		return scene.ambientLight.getIntensity();
	}
}
