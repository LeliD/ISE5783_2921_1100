/**
 * 
 */
package primitives;

/**
 * @author Leli and Shilat
 *
 */
import static primitives.Double3.ZERO;

public class Vector extends Point {

	public Vector(double d1, double d2, double d3) throws IllegalArgumentException {
		super(d1, d2, d3);
		if (xyz.equals(ZERO))
			throw new IllegalArgumentException("ERROR. Cannot create zero vector");
	}

	Vector(Double3 obj) {
		super(obj);
		if (xyz.equals(ZERO))
			throw new IllegalArgumentException("ERROR. Cannot create zero vector");
	}

	public Vector add(Vector otherVector) {
		return new Vector(super.add(otherVector).xyz);
	}

	public Vector scale(double rhs) {
		return new Vector(xyz.scale(rhs));
	}

	public double dotProduct(Vector otherVector) {
		return this.xyz.d1 * otherVector.xyz.d1 + this.xyz.d2 * otherVector.xyz.d2 + this.xyz.d3 * otherVector.xyz.d3;
	}

	public Vector crossProduct(Vector otherVector) {
		return new Vector(this.xyz.d2 * otherVector.xyz.d3 - this.xyz.d3 * otherVector.xyz.d2,
				this.xyz.d3 * otherVector.xyz.d1 - this.xyz.d1 * otherVector.xyz.d3,
				this.xyz.d1 * otherVector.xyz.d2 - this.xyz.d2 * otherVector.xyz.d1);
	}

	public double lengthSquared() {
		return dotProduct(this);
	}

	public double length() {
		return Math.sqrt(lengthSquared());
	}

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
