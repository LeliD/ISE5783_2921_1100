package primitives;

import static primitives.Util.isZero;

import java.util.Objects;

/**
 * Ray class is for representing a ray in 3 dimensional space
 * 
 * @author Shilat and Leli
 */
public class Ray {
	/** start point */
	private final Point p0;
	/** direction vector */
	private final Vector dir;

	/**
	 * Constructor to initialize Ray by a start point and direction vector
	 * 
	 * @param p start point
	 * @param v direction vector
	 */
	public Ray(Point p, Vector v) {
		p0 = p;
		dir = v.normalize();
	}

	/**
	 * @return the p0
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * @return the dir
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * Calculates a point on the ray at a given distance from the ray head
	 * 
	 * @param t the distance
	 * @return the calculated point
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

}
