package geometries;

import static primitives.Util.alignZero;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Triangle class for representing a Triangle, extends Polygon
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class Triangle extends Polygon {
	/**
	 * Constructs a Triangle object.
	 * 
	 * @param p1 First point of the Triangle.
	 * 
	 * @param p2 Second point of the Triangle.
	 * 
	 * @param p3 Third point of the Triangle.
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersections = plane.findIntersections(ray);
		if (intersections == null)// if the ray doesn't cross the plane of the triangle
			return null;
		Point p0 = ray.getP0();
		Vector v = ray.getDir();
		Vector v1 = vertices.get(0).subtract(p0);// p1-p0
		Vector v2 = vertices.get(1).subtract(p0);// p2-p0
		Vector v3 = vertices.get(2).subtract(p0);// p3-p0

		Vector n1 = v1.crossProduct(v2);// normal to the plane of v1,v2
		double s1 = alignZero(v.dotProduct(n1));
		if (s1 == 0) // if v contain in plane of v1, v2 ,there aren't Intsersections points
			return null;

		Vector n2 = v2.crossProduct(v3);// normal to the plane of v2,v3
		double s2 = alignZero(v.dotProduct(n2));
		if (s1 * s2 <= 0)// if v contain in plane of v2, v3 ,there aren't Intsersections points
			return null;

		Vector n3 = v3.crossProduct(v1);// normal to the plane of v3,v1
		double s3 = alignZero(v.dotProduct(n3));
		if (s1 * s3 <= 0) // if v contain in plane of v3, v1 ,there aren't Intsersections points
			return null;

		// The point is inside if all v*ni have the same sign (+/-)
		return intersections;
	}
}
