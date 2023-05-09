/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Shilat Sharon and Lea Drach
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple single test
		Point p1 = new Point(1, 2, 3);
		assertEquals(new Vector(2, 2, 2), (new Point(3, 4, 5).subtract(p1)),
				"ERROR: Point - Point does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple single test
		Point p1 = new Point(1, 2, 3);
		assertEquals(new Point(3, 4, 5), (p1.add(new Vector(2, 2, 2))),
				"ERROR: Point + Vector does not work correctly");

		// =============== Boundary Values Tests ==================
		// TC11: test (0, 0, 0) point from add
		assertEquals(new Point(0, 0, 0), p1.add(new Vector(-1, -2, -3)),
				"ERROR: add() does not work correctly in case of Point(0,0,0)");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: A simple single test
		Point p1 = new Point(1, 2, 3);
		assertEquals(27, p1.distanceSquared(new Point(4, 5, 6)), 0.00000001,
				"ERROR: distanceSquared() does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		Point p1 = new Point(1, 2, 3);
		// ============ Equivalence Partitions Tests ==============
		// TC01:A simple single test
		assertEquals(5, p1.distance(new Point(4, 6, 3)), 0.00000001, "ERROR: distance() does not work correctly");

		// =============== Boundary Values Tests ==================
		// TC11: A test of zero-distance
		assertEquals(0, p1.distance(p1), 0.00000001, "ERROR: distance() does not work correctly in case of distance 0");
	}

}
