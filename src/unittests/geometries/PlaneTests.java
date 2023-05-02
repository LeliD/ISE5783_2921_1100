/**
 * 
 */
package unittests.geometries;

import primitives.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static primitives.Util.isZero;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import geometries.Plane;
import primitives.Point;
import primitives.Vector;
import java.util.List;

/**
 * @author Shilat Sharon and Lea Drach
 *
 */
class PlaneTests {

	/**
	 * Test method for
	 * {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
	 */
	@Test
	void testConstructor() {
		// =============== Boundary Values Tests ==================
		// test two identical points
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 1, 1), new Point(1, 1, 1), new Point(0, 1, 0)));
		// test three points in the same line
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(1, 0, 0), new Point(2, 0, 0), new Point(3, 0, 0)));
	}

	/**
	 * Test method for {@link geometries.Plane#getNormal()}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0) };
		Plane ple = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
		// ensure there are no exceptions
		assertDoesNotThrow(() -> ple.getNormal(new Point(0, 0, 1)), "");
		// generate the test result
		Vector result = ple.getNormal(new Point(0, 0, 1));
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
		// ensure the result is orthogonal to all the edges
		for (int i = 0; i < 2; ++i)
			assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
					"Triangle's normal is not orthogonal to one of the edges");

	}

	/**
	 * checks findIntersections Test method for plane
	 * {@link geometries.Plane#findIntersections(primitives.Ray)}.
	 */
	public void testFindIntersections() {
		Plane pl = new Plane(new Point(0, 0, -3), new Vector(0, 0, -1));
		// ============ Equivalence Partitions Tests ==============
		// EP: The Ray must be neither orthogonal nor parallel to the plane
		// TC01: Ray intersects the plane
		List<Point> result = pl.findIntersections(new Ray(new Point(1, 1, 0), new Vector(2, 1, -1)));
		assertEquals(List.of(new Point(7, 4, -3)), result, "the ray intersects the plane");
		// TC02:Ray does not intersect the plane
		result = pl.findIntersections(new Ray(new Point(1, 1, 0), new Vector(2, 1, 1)));
		assertNull(result, "the ray does not intersect the plane");
		// =============== Boundary Values Tests ==================
		// Group:Ray is parallel to the plane
		// TC11:Ray is parallel to the plane,the ray included in the plan
		assertNull(pl.findIntersections(new Ray(new Point(1, 2, -3), new Vector(2, 1, 0))),
				"The ray is parallel and included in the plane");
		// TC12: Ray is parallel to the plane and ray is not included in the plane
		assertNull(pl.findIntersections(new Ray(new Point(1, 2, -2), new Vector(2, 1, 0))),
				"The ray is parallel and not included in the plane");
		// Group: Ray is orthogonal to the plane
		// TC13:Ray starts before the plane
		assertEquals(List.of(new Point(1, 1, -3)),
				pl.findIntersections(new Ray(new Point(1, 1, 0), new Vector(0, 0, -1))),
				"the ray is orthogonal and starts before the plane");
		// TC14:Ray starts in the plane
		assertNull(pl.findIntersections(new Ray(new Point(1, 1, -3), new Vector(0, 0, -1))),
				"The ray is orthogonal and starts in the plane");
		// TC15:Ray starts after the plane
		assertNull(pl.findIntersections(new Ray(new Point(1, 1, -4), new Vector(0, 0, -1))),
				"The ray is orthogonal and starts after the plane");

		// TC16: Starting point on the plane, but the rest of the ray is not
		assertNull(pl.findIntersections(new Ray(new Point(1, 1, -3), new Vector(2, 1, -1))),
				"Starting point on the plane, but the rest of the ray is not");

		// TC17: Starting point is equal to the plane point
		assertNull(pl.findIntersections(new Ray(new Point(0, 0, -3), new Vector(2, 1, -1))),
				"Starting point is equal to the plane point");

	}

}
