package primitives;

/**
 * Point class for representing a point in 3D space
 * 
 * @author Shilat Sharon and Lea Drach
 */
public class Point {

	/** The coordinates of the point */
	protected final Double3 xyz;
    /** Zero point (0,0,0)*/
	//public static final Point ZERO = new Point(Double3.ZERO);
	/**
	 * Constructs a Point object with the given coordinates.
	 * 
	 * @param d1 the x coordinate
	 * @param d2 the y coordinate
	 * @param d3 the z coordinate
	 */
	public Point(double d1, double d2, double d3) {
		xyz = new Double3(d1, d2, d3);
	}

	/**
	 * Constructs a Point object using a Double3 object.
	 * 
	 * @param obj the Double3 object to use
	 */
	Point(Double3 obj) {
		xyz = obj;
	}

	/**
	 * Subtracts another point from this point and returns the resulting vector.
	 * 
	 * @param otherPoint the point to subtract from this point
	 * @return the resulting vector
	 */
	public Vector subtract(Point otherPoint) {
		return new Vector(xyz.subtract(otherPoint.xyz));
	}

	/**
	 * Adds a vector to this point and returns the resulting point.
	 * 
	 * @param otherVector the vector to add to this point
	 * @return the resulting point
	 */
	public Point add(Vector otherVector) {
		return new Point(xyz.add(otherVector.xyz));
	}

	/**
	 * 
	 * Calculates the squared distance between this point and another point.
	 * 
	 * @param otherPoint the other point to calculate the distance to
	 * @return the squared distance between this point and the other point
	 */
	public double distanceSquared(Point otherPoint) {
		Double3 obj = xyz.subtract(otherPoint.xyz);
		return obj.d1 * obj.d1 + obj.d2 * obj.d2 + obj.d3 * obj.d3;
	}

	/**
	 * 
	 * Calculates the distance between this point and another point.
	 * 
	 * @param otherPoint the other point to calculate the distance to
	 * @return the distance between this point and the other point
	 */
	public double distance(Point otherPoint) {
		return Math.sqrt(distanceSquared(otherPoint));
	}

	@Override
	public int hashCode() {
		return xyz.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return obj instanceof Point other //
				&& this.xyz.equals(other.xyz);
	}

	@Override
	public String toString() {
		return "Point [xyz=" + xyz + "]";
	}

	/**
	 * 
	 * Returns the x coordinate of this point.
	 * 
	 * @return the x coordinate
	 */
	public double getX() {
		return xyz.d1;
	}

	/**
	 * 
	 * Returns the y coordinate of this point.
	 * 
	 * @return the y coordinate
	 */
	public double getY() {
		return xyz.d2;
	}

}
