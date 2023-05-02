package geometries;

import static primitives.Util.*;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Sphere class for representing a Sphere, extends RadialGeometry
 * 
 * @author Shilat Sharon and Leli Drach
 *
 */
public class Sphere extends RadialGeometry {
	/** center of a Sphere */
	private final Point center;

	/**
	 * Constructor to initialize a Sphere
	 * 
	 * @param p center of a Sphere
	 * @param r radius of a Sphere
	 */
	public Sphere(Point p, double r) {
		super(r);
		center = p;
	}

	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	@Override
	public String toString() {
		return "Sphere [center=" + center + ", radius=" + radius + "]";
	}

	@Override
	public Vector getNormal(Point p) {
		return (p.subtract(center)).normalize();
	}

	/**
	 * Finding intersection points on the geometry with a given ray
	 * 
	 * @param ray - the ray to find intersection points with
	 * @return List of intersection-points on the geometry with the given ray
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		// get ray point and vector
		Point p0 = ray.getP0();
		Vector v = ray.getDir();

		// vector between p0 start and sphere center-O
		Vector u = null;
		try {
			u = center.subtract(p0);
		} catch (Exception ex)// p0=center
		{
			// return p0 + r*v
			return List.of(ray.getPoint(radius));
		}

		double tm = v.dotProduct(u);

		// get the distance between the ray and the sphere center
		double d2 = alignZero(u.lengthSquared() - tm * tm);

		// the ray tangent the sphere
		double delta2 = alignZero(radius2 - d2);
		if (delta2 <= 0) // if d=radius
			return null;

		// the ray crosses the sphere in two places
		double th = Math.sqrt(delta2);
		// get the distance to the two points
		double t2 = alignZero(tm + th);
		if (t2 <= 0) return null;
		
		double t1 = alignZero(tm - th);
		return t1 <= 0 //
				? List.of(ray.getPoint(t2)) // P2
				: List.of(ray.getPoint(t1), ray.getPoint(t2)); // P1 , P2
	}
}
