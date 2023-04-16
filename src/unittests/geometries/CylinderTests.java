/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import geometries.Cylinder;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for geometries.Cylinder class
 * 
 * @author Lea and Shilat
 */
class CylinderTests {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Cylinder cyl = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 4, 3); // cylinder: x^2 + y^2 = 25
																								// and 0 <= z <= 3
		// ============ Equivalence Partitions Tests ==============
		// TC01: Test for points on the cylinder's side
		assertEquals(new Vector(1, 0, 0), cyl.getNormal(new Point(4, 0, 2)),
				"Cylinder's normal isn't correct for points on the cylinder's side");
		// TC02: Test for points on the cylinder's first base (where the axis starts)
		assertEquals(new Vector(0, 0, -1), cyl.getNormal(new Point(1, 1, 0)),
				"Cylinder's normal isn't correct for points on the cylinder's first base");
		// TC03: Test for points on the cylinder's second base (where the axis "ends",
		// by the height)
		assertEquals(new Vector(0, 0, 1), cyl.getNormal(new Point(1, 2, 3)),
				"Cylinder's normal isn't correct for points on the cylinder's second base");

		// =============== Boundary Values Tests ==================
		// TC10: Test for the center point on the cylinder's first base (where the axis
		// starts) 
		assertEquals(new Vector(0, 0, -1), cyl.getNormal(new Point(0, 0, 0)),
				"Cylinder's normal isn't correct for points on the cylinder's first base (on the axis' starting point)");
		// TC11: Test for the center point on the cylinder's second base (where the axis
		// "ends", by the height)
		assertEquals(new Vector(0, 0, 1), cyl.getNormal(new Point(0, 0, 3)),
				"Cylinder's normal isn't correct for points on the cylinder's second base (on the axis' 'end' point)");
	}

}
