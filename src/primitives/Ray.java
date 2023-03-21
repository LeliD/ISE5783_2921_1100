package primitives;

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

	@Override
	public int hashCode() {
		return Objects.hash(dir, p0);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Ray other)
			return this.p0.equals(other.p0) && this.dir.equals(other.dir);
		return false;
	}

	@Override
	public String toString() {
		return "Ray [p0=" + p0 + ", dir=" + dir + "]";
	}

}
