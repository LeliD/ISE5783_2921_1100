package primitives;

import static primitives.Util.isZero;

import java.util.List;
import java.util.Objects;

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
	public int hashCode() {
		return Objects.hash(dir, p0);
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
	 * points.
	 * 
	 * @param points The list of points to search for the closest point
	 * 
	 * @return The closest point to the starting point of the ray, or null if the
	 *         list is null
	 */
	public Point findClosestPoint(List<Point> points) {
		if (points == null || points.isEmpty())
			return null;
		// initialize variables
		double minDis = p0.distance(points.get(0));// the smallest distance
		Point closestPoint = points.get(0);// the closest point
		double currentDis;// save the current distance-for the loop
		// iterate through the list of points
		for (int i = 1; i < points.size(); i++) {
			currentDis = p0.distance(points.get(i));
			// update the closest point if a closer point is found
			if (currentDis < minDis) {
				minDis = currentDis;
				closestPoint = points.get(i);
			}
		}
		return closestPoint;
	}

}
