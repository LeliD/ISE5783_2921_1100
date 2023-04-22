/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static primitives.Util.isZero;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for geometries.Triangle class
 * 
 * @author Lea and Shilat
 */
class TriangleTests {

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0) };
		Triangle tri = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
		// ensure there are no exceptions
		assertDoesNotThrow(() -> tri.getNormal(new Point(0, 0, 1)), "");
		// generate the test result
		Vector result = tri.getNormal(new Point(0, 0, 1));
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
		// ensure the result is orthogonal to all the edges
		for (int i = 0; i < 2; ++i)
			assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
					"Triangle's normal is not orthogonal to one of the edges");

	}

	/**
	 * checks findIntersections Test method for triangle
	 * {@link geometries.Polygon#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Triangle tr = new Triangle(new Point(0, 3, -3), new Point(3, 0, -3), new Point(-3, 0, -3));
		Ray r;
		// ============ Equivalence Partitions Tests ==============
		// TC01: the ray goes through the triangle
		r = new Ray(new Point(1, 1, -2), new Vector(-2, 0.5, -1));
		List<Point> intersections = tr.findIntersections(r);
		assertEquals(List.of(new Point(-1, 1.5, -3)), tr.findIntersections(r), "The ray goes through the triangle");
		// TC02: the ray is outside the triangle in front of one edge
		r = new Ray(new Point(4, 4, -2), new Vector(1, 1, -4));
		assertNull(tr.findIntersections(r), "The ray is outside the triangle in front of one edge");
		// TC03: the ray is outside the triangle in front of one vertex
		r = new Ray(new Point(-4, -1, -2), new Vector(-1, -1, -1));
		assertNull(tr.findIntersections(r), "The ray is outside the triangle in front of one vertex");

		// =============== Boundary Values Tests ==================
		// TC11: ray through edge
		r = new Ray(new Point(-2, 1, -1), new Vector(0, 0, -1));
		assertNull(tr.findIntersections(r), "ray through edge");
		// TC12: ray through vertex
		r = new Ray(new Point(3, 0, -2), new Vector(0, 0, -1));
		assertNull(tr.findIntersections(r), "ray through vertex");
		// TC13: ray goes through the continuation of side 1
		r = new Ray(new Point(-1, 4, -2), new Vector(0, 0, -1));
		assertNull(tr.findIntersections(r), "ray goes through the continuation of side 1");
	}

}
