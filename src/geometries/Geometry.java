package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometric shape that can be intersected
 * by a ray and has a normal vector
 * 
 * in every point on its surface.
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public interface Geometry extends Intersectable {

	/**
	 * 
	 * Calculates the normal vector of a point on the surface of the geometry shape.
	 * 
	 * @param p the point on the surface of the shape
	 * @return the normal vector to the shape at the point p
	 */
	public Vector getNormal(Point p);
}
