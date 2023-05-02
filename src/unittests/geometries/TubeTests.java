/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import geometries.Tube;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for geometries.Tube class
 * 
 * @author Shilat Sharon and Lea Drach
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Tube tube = new Tube(new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)), 1);

		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here

		// ensure the normal is correct
		assertEquals(new Vector(1, 0, 0), tube.getNormal(new Point(1, 0, 2)), "Tube's normal isn't correct");

		// =============== Boundary Values Tests ==================
		// Check when the point is in front of the head Ray
		assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(0, 1, 1)), "Tube's normal isn't correct");
	}

}
