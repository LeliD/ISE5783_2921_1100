package primitives;

import static primitives.Util.isZero;

import java.util.List;
import java.util.Objects;

import geometries.Intersectable.GeoPoint;

/**
 * Ray class for representing a ray in 3D space
 * 
 * @author Shilat Sharon and Lea Drach
 */
public class Ray {
	/** The starting point of the ray */
	private final Point p0;
	/** The direction vector of the ray */
	private final Vector dir;

	/**
	 * 
	 * Constructs a Ray object with the given starting point and direction vector.
	 * The direction vector is automatically normalized to ensure that it has length
	 * 1.
	 * 
	 * @param p the starting point of the ray
	 * @param v the direction vector of the ray
	 */

	public Ray(Point p, Vector v) {
		p0 = p;
		dir = v.normalize();
	}

	/**
	 * 
	 * Returns the starting point of the ray.
	 * 
	 * @return the starting point of the ray
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * 
	 * Returns the direction vector of the ray.
	 * 
	 * @return the direction vector of the ray
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * 
	 * Returns a point along the ray at a given distance from the ray head The point
	 * is calculated as p0 + t*dir, where t is the given distance.
	 * 
	 * @param t the distance
	 * @return a point along the ray at the given distance
	 */
	public Point getPoint(double t) {
		return isZero(t) ? p0 : p0.add(dir.scale(t));
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return obj instanceof Ray other //
				&& this.p0.equals(other.p0) && this.dir.equals(other.dir);
	}

	@Override
	public String toString() {
		return "Ray [p0=" + p0 + ", dir=" + dir + "]";
	}

	/**
	 * 
	 * Finds the closest point to the starting point of the ray from a list of
	 * points by calling the function findClosestGeoPoint
	 * 
	 * @param points The list of points to search for the closest point
	 * 
	 * @return The closest point to the starting point of the ray, or null if the
	 *         list is either null or empty
	 */
	public Point findClosestPoint(List<Point> points) {
		return points == null || points.isEmpty() ? null
				: findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}

	/**
	 * 
	 * Finds the closest GeoPoint to the starting point of the ray from a list of
	 * points.
	 * 
	 * @param intersections The list of points to search for the closest GeoPoint
	 * 
	 * @return The closest GeoPoint to the starting point of the ray, or null if the
	 *         list is either null or empty
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
		if (intersections == null || intersections.isEmpty()) // points is empty or uninitialized
			return null;
		// initialize variables
		double minDis = Double.POSITIVE_INFINITY;// the smallest distance
		GeoPoint closestGeoPoint = null;// the closest GeoPoint
		double currentDis;// saves the current distance (for the loop)
		// iterate through the list of points
		for (var p : intersections) {
			currentDis = p0.distance(p.point);
			// update the closest point and distance if a closer point is found
			if (currentDis < minDis) {
				minDis = currentDis;
				closestGeoPoint = p;
			}
		}
		return closestGeoPoint;
	}

}
