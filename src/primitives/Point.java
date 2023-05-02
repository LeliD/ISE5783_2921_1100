package primitives;

/**
 * Point class is for representing a point in 3 dimensional space
 * 
 * @author Shilat and Leli
 */
public class Point {
	/** A point */
	protected final Double3 xyz;

	/**
	 * Constructor to initialize Double3 based object with its three number values
	 * 
	 * @param d1 first number value
	 * @param d2 second number value
	 * @param d3 third number value
	 */
	public Point(double d1, double d2, double d3) {
		xyz = new Double3(d1, d2, d3);
	}

	/**
	 * Constructor to initialize xyz by Double3 object
	 * 
	 * @param obj Double3 object
	 */
	Point(Double3 obj) {
		xyz = obj;
	}

	/**
	 * subtract between two points and return vector
	 * 
	 * @param otherPoint the second point
	 * @return new vector after subtract
	 * @throws IllegalArgumentException when subtracting identical points
	 */
	public Vector subtract(Point otherPoint) {
		return new Vector(xyz.subtract(xyz.subtract(otherPoint.xyz)));
	}

	/**
	 * add vector to point and return a new point
	 * 
	 * @param otherVector the vector to add to the point
	 * @return result of add
	 */
	public Point add(Vector otherVector) {
		Double3 obj = xyz.add(new Double3(otherVector.xyz.d1, otherVector.xyz.d2, otherVector.xyz.d3));
		return new Point(obj);
	}

	/**
	 * Calculate the Squared distance between two points
	 * 
	 * @param otherPoint other Point
	 * @return the Squared distance
	 */
	public double distanceSquared(Point otherPoint) {
		Double3 obj = this.subtract(otherPoint).xyz;
		return obj.d1 * obj.d1 + obj.d2 * obj.d2 + obj.d3 * obj.d3;
	}

	/**
	 * Calculate the distance between two points
	 * 
	 * @param otherPoint other Point
	 * @return the distance
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
		if (obj == null)
			return false;
		if (obj instanceof Point other)
			return this.xyz.equals(other.xyz);
		return false;
	}

	@Override
	public String toString() {
		return "Point [xyz=" + xyz + "]";
	}

	/**
	 * getX
	 * 
	 * @return x coordinate
	 */
	public double getX() {
		return xyz.d1;
	}

	/**
	 * getY
	 * 
	 * @return y coordinate
	 */
	public double getY() {
		return xyz.d2;
	}

}
