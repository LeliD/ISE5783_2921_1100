package primitives;

import java.util.Objects;
/**
 * Point class is for represent a point in 3 dimensional space
 * @author Shilat and Leli
 */
public class Point 
{

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

	Point(Double3 obj) {
		xyz = new Double3(obj.d1, obj.d2, obj.d3);
	}

	/**
	 * subtract between two points and return vector
	 * 
	 * @param otherPoint the second point
	 * @return new vector after subtract
	 * @throws IllegalArgumentException
	 */
	public Vector subtract(Point otherPoint) {
		Double3 obj = xyz.subtract(new Double3(otherPoint.xyz.d1, otherPoint.xyz.d2, otherPoint.xyz.d3));
		return new Vector(obj);
	}

	public Point add(Vector otherVector) {
		Double3 obj = xyz.add(new Double3(otherVector.xyz.d1, otherVector.xyz.d2, otherVector.xyz.d3));
		return new Point(obj);
	}

	public double distanceSquared(Point otherPoint) {
		Double3 obj = this.subtract(otherPoint).xyz;
		return obj.d1 * obj.d1 + obj.d2 * obj.d2 + obj.d3 * obj.d3;
	}

	public double distance(Point otherPoint) {
		return Math.sqrt(distanceSquared(otherPoint));
	}

	@Override
	public int hashCode() {
		return Objects.hash(xyz);
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

}
