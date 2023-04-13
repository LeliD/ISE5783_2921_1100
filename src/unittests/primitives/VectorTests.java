/**
 * 
 */
package unittests.primitives;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
* Unit tests for primitives.Vector class
* @author Lea and Shilat
*/
class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
	 */
	@Test
	void testSubtract() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	public void testCrossProduct() {
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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		fail("Not yet implemented");
	}

}
