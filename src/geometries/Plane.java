package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Plane class for representing a plane, implements Geometry interface
 * 
 * @author Shilat Sharon and Leli Drach
 *
 */
public class Plane implements Geometry {
	/** a point in the plane */
	private final Point p0;
	/** normal of the plane */
	private final Vector normal;

	/**
	 * Constructor to initialize a plane
	 * 
	 * @param p1 first point in the plane
	 * @param p2 second point in the plane
	 * @param p3 third point in the plane
	 */

	public Plane(Point p1, Point p2, Point p3) 
	{
	    this.p0 = p1;
		Vector v1=p2.subtract(p1);
		Vector v2=p3.subtract(p1);
	    normal = v1.crossProduct(v2).normalize();//if v1 and v2 are on the same direction of vector- the cross product and the normal will be zero vector.	
	
	}
	/**
	 * Constructor to initialize a plane
	 * 
	 * @param p point in the plane
	 * @param v normal of the plane (not necessarily normalized)
	 */
	public Plane(Point p, Vector v) {
		p0 = p;
		normal = v.normalize();
	}

	/**
	 * @return the p0
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * @return the normal
	 */
	public Vector getNormal() {
		return normal;
	}

	@Override
	public Vector getNormal(Point point) {
		return normal;
	}

	@Override
	public String toString() {
		return "Plane [" + (p0 != null ? "p0=" + p0 + ", " : "") + (normal != null ? "normal= " + normal : "") + "]";
	}
}
