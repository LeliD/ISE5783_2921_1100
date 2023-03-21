/**
 * 
 */
package primitives;

/**
 * Vector class for representing a point with direction
 * @author Leli and Shilat
 *
 */
import static primitives.Double3.ZERO;

public class Vector extends Point {
	/**
	 * Constructor to initialize Vector
	 * 
	 * @param d1 first number value
	 * @param d2 second number value
	 * @param d3 third number value
	 * @throws IllegalArgumentException
	 */
	public Vector(double d1, double d2, double d3) throws IllegalArgumentException {
		super(d1, d2, d3);
		if (xyz.equals(ZERO))
			throw new IllegalArgumentException("ERROR. Cannot create zero vector");
	}

	/**
	 * Constructor to initialize xyz by Double3 object
	 * 
	 * @param obj Double3 object
	 */
	Vector(Double3 obj) {
		super(obj);
		if (xyz.equals(ZERO))
			throw new IllegalArgumentException("ERROR. Cannot create zero vector");
	}

	/**
	 * add vector to vector and return a new vector
	 * 
	 * @param otherVector the vector to add to the vector
	 * @return result of add
	 */
	public Vector add(Vector otherVector) {
		return new Vector(super.add(otherVector).xyz);
	}

	/**
	 * scale product
	 * 
	 * @param rhs a scale number
	 * @return new vector after scale product
	 */
	public Vector scale(double rhs) {
		return new Vector(xyz.scale(rhs));
	}

	/**
	 * dot Product
	 * 
	 * @param otherVector other Vector
	 * @return new vector after dot product
	 */
	public double dotProduct(Vector otherVector) {
		return this.xyz.d1 * otherVector.xyz.d1 + this.xyz.d2 * otherVector.xyz.d2 + this.xyz.d3 * otherVector.xyz.d3;
	}

	/**
	 * cross Product
	 * 
	 * @param otherVector other Vector
	 * @return new vector after cross product
	 */
	public Vector crossProduct(Vector otherVector) {
		return new Vector(this.xyz.d2 * otherVector.xyz.d3 - this.xyz.d3 * otherVector.xyz.d2,
				this.xyz.d3 * otherVector.xyz.d1 - this.xyz.d1 * otherVector.xyz.d3,
				this.xyz.d1 * otherVector.xyz.d2 - this.xyz.d2 * otherVector.xyz.d1);
	}

	/**
	 * calculate Squared length
	 * 
	 * @return Squared length
	 */
	public double lengthSquared() {
		return dotProduct(this);
	}

	/**
	 * calculate the length of the vector
	 * 
	 * @return length Squared
	 */
	public double length() {
		return Math.sqrt(lengthSquared());
	}

	/**
	 * normalize the vector
	 * 
	 * @return this vector but normalized
	 */
	public Vector normalize() {
		return scale(1.0 / length());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Vector [xyz=" + xyz + "]";
	}
}
