/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Vector;

/**
 * Unit tests for primitives.Vector class
 * 
 * @author Lea and Shilat
 */
class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#Vector(double, double, double)}.
	 */
	@Test
	void testVectorDoubleDoubleDouble() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#Vector(primitives.Double3)}.
	 */
	@Test
	void testVectorDouble3() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(1, -3, -4);
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple single test
		assertEquals(new Vector(2, -1, -1), v1.add(v2), "ERROR: add() does not work correctly");// check if the add
																								// works well (not the
																								// zero vector)
		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from add of parallel vectors in opposite directions
		Vector v3 = new Vector(-1, -2, -3);
		assertThrows(IllegalArgumentException.class, () -> v1.add(v3),
				"ERROR: Vector + -itself does not throw an exception");

	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		Vector v = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple single test
		assertEquals(new Vector(2, 4, 6), v.scale(2), "Bad vector scale: wrong value");

		// =============== Boundary Values Tests ==================
		// TC10: Test for trying to get vector-zero, which is illegal
		assertThrows(IllegalArgumentException.class, () -> v.scale(0), "Bad vector scale: vector-zero is not allowed");

	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		Vector v1 = new Vector(1, 2, 3);

		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple single test
		Vector v2 = new Vector(2, 3, 4);
		assertEquals(20, v1.dotProduct(v2), 0.00000001, "ERROR: dotProduct() wrong value");

		// =============== Boundary Values Tests ==================
		// TC10: Test for orthogonal vectors - expected value is exactly 0
		Vector v3 = new Vector(0, -3, 2);
		assertEquals(0, v1.dotProduct(v3), 0.00000001, "ERROR: dotProduct() for orthogonal vectors is not zero");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		Vector v1 = new Vector(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		Vector v2 = new Vector(0, 3, -2);
		Vector vr = v1.crossProduct(v2);
		// TC01: Test that length of cross-product is proper (orthogonal vectors taken
		// for simplicity)
		assertEquals(v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");
		// TC02: Test cross-product result orthogonality to its operands
		assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
		assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");
		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from cross-productof co-lined vectors
		Vector v3 = new Vector(-2, -4, -6);
		assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
				"crossProduct() for parallel vectors does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple single test
		Vector v = new Vector(1, 2, 3);
		assertEquals(14, v.lengthSquared(), 0.00000001, "Bad vectors length-squared: wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple single test
		Vector v = new Vector(5, 0, 0);
		assertEquals(5, v.length(), 0.00000001, "Bad vectors length: wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple single test of changing the vector itself
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = v1.normalize();
		assertEquals(1, v1.normalize().length(), 0.00000001, "Bad vectors normalize: wrong value");
		assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
				"ERROR: the normalized vector is not parallel to the original one");
		assertFalse(v1.dotProduct(v2) < 0, "ERROR: the normalized vector is opposite to the original one");
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		Vector v1 = new Vector(1, 2, 3);
		Vector v2 = new Vector(1, -3, -4);
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		assertEquals(new Vector(0, 5, 7), v1.subtract(v2), "ERROR: subtract() does not work correctly");
		// =============== Boundary Values Tests ==================
		// TC11: test zero vector from subtract of parallel vectors in the same
		// directions
		Vector v3 = new Vector(1, 2, 3);

		assertThrows(IllegalArgumentException.class, () -> v1.subtract(v3),
				"ERROR: Vector - itself does not throw an exception");

	}

}
