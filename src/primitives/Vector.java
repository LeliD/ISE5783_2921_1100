/**
 * 
 */
package primitives;

import static primitives.Double3.ZERO;

/**
 * Vector class for representing a vector in 3D space ,extends Point
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class Vector extends Point {

	/**
	 * 
	 * Constructs a vector with the given x, y, and z values.
	 * 
	 * @param d1 the x-coordinate of the vector
	 * @param d2 the y-coordinate of the vector
	 * @param d3 the z-coordinate of the vector
	 * @throws IllegalArgumentException if it is a zero vector
	 */
	public Vector(double d1, double d2, double d3) {
		super(d1, d2, d3);
		if (xyz.equals(ZERO))
			throw new IllegalArgumentException("ERROR. Cannot create zero vector");
	}

	/**
	 * 
	 * Constructs a vector with the x, y, and z values from the given Double3
	 * object.
	 * 
	 * @param obj the Double3 object containing the x, y, and z values for the
	 *            vector
	 * @throws IllegalArgumentException if it is a zero vector
	 */
	Vector(Double3 obj) {
		super(obj);
		if (xyz.equals(ZERO))
			throw new IllegalArgumentException("ERROR. Cannot create zero vector");
	}

	/**
	 * 
	 * Adds the given vector to this vector and returns a new vector representing
	 * the result.
	 * 
	 * @param otherVector the vector to be added to this vector
	 * @return a new vector representing the sum of the two vectors
	 * @throws IllegalArgumentException in case the result vector is zero one
	 */
	public Vector add(Vector otherVector) {
		return new Vector(otherVector.xyz.add(this.xyz));
	}

	/**
	 * 
	 * Multiplies this vector by the given scalar and returns a new vector
	 * representing the result.
	 * 
	 * @param rhs the scalar to multiply the vector by
	 * 
	 * @return a new vector representing the result of the scalar multiplication
	 * @throws IllegalArgumentException in case the given scalar is 0
	 */
	public Vector scale(double rhs) {
		return new Vector(xyz.scale(rhs));
	}

	/**
	 * 
	 * Computes the dot product of this vector and the given vector.
	 * 
	 * @param otherVector the vector to compute the dot product with
	 * @return the dot product of the two vectors
	 */
	public double dotProduct(Vector otherVector) {
		return xyz.d1 * otherVector.xyz.d1 //
				+ xyz.d2 * otherVector.xyz.d2 //
				+ xyz.d3 * otherVector.xyz.d3;
	}

	/**
	 * 
	 * Computes the cross product of this vector and the given vector.
	 * 
	 * @param otherVector the vector to compute the cross product with
	 * @return a new vector representing the cross product of the two vectors
	 */
	public Vector crossProduct(Vector otherVector) {
		return new Vector(xyz.d2 * otherVector.xyz.d3 - xyz.d3 * otherVector.xyz.d2,
				xyz.d3 * otherVector.xyz.d1 - xyz.d1 * otherVector.xyz.d3,
				xyz.d1 * otherVector.xyz.d2 - xyz.d2 * otherVector.xyz.d1);
	}

	/**
	 * 
	 * Computes the square of the length of this vector.
	 * 
	 * @return the square of the length of this vector
	 */
	public double lengthSquared() {
		return dotProduct(this);
	}

	/**
	 * 
	 * Computes the length of this vector.
	 * 
	 * @return the length of this vector
	 */
	public double length() {
		return Math.sqrt(lengthSquared());
	}

	/**
	 * 
	 * Returns a new vector that is the normalization of this vector. The
	 * normalization of a vector is a vector in the same direction with a length of
	 * 1.
	 * 
	 * @return a new vector representing the normalization of this vector
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

	/**
	 * 
	 * Finds orthogonal vector to the current vector.
	 * 
	 * @return A new Vector object that is orthogonal to the current vector.
	 */
	public Vector findOrthogonal() {
		double x = this.xyz.d1, //
				y = this.xyz.d2, //
				z = this.xyz.d3, //
				absX = Math.abs(x), //
				absY = Math.abs(y), //
				absZ = Math.abs(z);
		if (absX < absY)
			return absX < absZ ? new Vector(0, -z, y).normalize() : new Vector(-y, x, 0).normalize();
		else
			return absY < absZ ? new Vector(z, 0, -x).normalize() : new Vector(-y, x, 0).normalize();
	}
}
