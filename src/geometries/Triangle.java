package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * Triangle class for representing a Triangle, extends Polygon
 * 
 * @author Shilat Sharon and Leli Drach
 *
 */
public class Triangle extends Polygon {
	/**
	 * Constructor to initialize a Triangle
	 * 
	 * @param p1 first point in the Triangle
	 * @param p2 second point in the Triangle
	 * @param p3 third point in the Triangle
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}
	/**
	 * Finding intersection points on the geometry with a given ray
	 * @param ray - the ray to find intersection points with
	 * @return List of intersection-points on the geometry with the given ray
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
