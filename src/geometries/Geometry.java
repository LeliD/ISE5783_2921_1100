package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface of Geometry
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public interface Geometry extends Intersectable {
	/**
	 * Calculate normal vector of point on geometry shape's surface
	 * 
	 * @param p point to get its normal
	 * @return normal Vector in the point
	 */
	public Vector getNormal(Point p);
}
