/**
 * 
 */
package geometries;

import primitives.*;
import java.util.List;

/**
 * The Intersectable interface represents a geometry object that can be
 * intersected by a ray.
 * 
 * 
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public interface Intersectable {

	/**
	 * 
	 * Finds intersection points on the geometry with a given ray.
	 * 
	 * @param ray the ray to find intersection points with
	 * @return a List of intersection points on the geometry with the given ray, or
	 *         null if there are no intersections
	 */
	public List<Point> findIntersections(Ray ray);
}
