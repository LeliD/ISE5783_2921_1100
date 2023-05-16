package geometries;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Plane class for representing a plane.implements Geometry interface
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class Plane extends Geometry {
	/** a point in the plane */
	private final Point p0;
	/** normal of the plane */
	private final Vector normal;

	/**
	 * 
	 * Constructs a plane from three points.
	 * 
	 * @param p1 The first point on the plane
	 * @param p2 The second point on the plane
	 * @param p3 The third point on the plane
	 */

	public Plane(Point p1, Point p2, Point p3) {
		this.p0 = p1;
		Vector v1 = p2.subtract(p1);
		Vector v2 = p3.subtract(p1);
		normal = v1.crossProduct(v2).normalize();// if v1 and v2 are on the same direction of vector- the cross product
													// and the normal will be zero vector.

	}

	/**
	 * 
	 * Constructs a plane from a point and a normal vector.
	 * 
	 * @param p The point on the plane
	 * @param v The normal vector to the plane
	 */
	public Plane(Point p, Vector v) {
		p0 = p;
		normal = v.normalize();
	}

	/**
	 * 
	 * Returns the point on the plane.
	 * 
	 * @return The point on the plane
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * 
	 * Returns the normal vector to the plane.
	 * 
	 * @return The normal vector to the plane
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

	@Override
	public List<Point> findIntersections(Ray ray) {
		// get ray point and vector
		Point p0ray = ray.getP0();
		Vector v = ray.getDir();

		// check if the ray is parallel to the plane
		if (isZero(normal.dotProduct(v))) // if dotProduct = 0
			return null;

		try {
			double t = alignZero((normal.dotProduct(p0.subtract(p0ray))) / (normal.dotProduct(v)));
			// if the the ray starts on the plane or doesn't cross the plane - return null
			return t <= 0 ? null : List.of((ray.getPoint(t)));

		} catch (IllegalArgumentException ex) {
			// if p0.subtract(p0ray) is vector zero, if p0ray=p0
			return null;
		}
	}
}
