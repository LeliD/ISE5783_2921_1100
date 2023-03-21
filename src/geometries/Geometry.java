package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Interface of Geometry
 * 
 * @author Shilat Sharon and Leli Drach
 *
 */
public interface Geometry {
	/**
	 * Calculate normal vector of point on geometry shape
	 * 
	 * @param p point to get its normal
	 * @return normal Vector in the point
	 */
	public Vector getNormal(Point p);
}
