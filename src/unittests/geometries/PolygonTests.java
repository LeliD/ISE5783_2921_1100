package unittests.geometries;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Polygons
 * 
 * @author Dan
 */
public class PolygonTests {

	/** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
				"Constructed a polygon with wrong order of vertices");

		// TC03: Not in the same plane
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
				"Constructed a polygon with vertices that are not in the same plane");

		// TC04: Concave quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0.5, 0.25, 0.5)), //
				"Constructed a concave polygon");

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
				"Constructed a polygon with vertix on a side");

		// TC11: Last point = first point
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
				"Constructed a polygon with vertice on a side");

		// TC12: Co-located points
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
				"Constructed a polygon with vertice on a side");

	}

	/** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here - using a quad
		Point[] pts = { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1) };
		Polygon pol = new Polygon(pts);
		// ensure there are no exceptions
		assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
		// generate the test result
		Vector result = pol.getNormal(new Point(0, 0, 1));
		// ensure |result| = 1
		assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
		// ensure the result is orthogonal to all the edges
		for (int i = 0; i < 3; ++i)
			assertEquals(0, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), 0.00000001,
					"Polygon's normal is not orthogonal to one of the edges");
	}

	/**
	 * checks findIntersections Test method for polygon
	 * {@link geometries.Polygon#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Polygon poly = new Polygon(new Point(0, 3, -3), new Point(3, 0, -3), new Point(-3, 0, -3));
		Ray r;
		// ============ Equivalence Partitions Tests ==============
		// TC01: the ray goes through the triangle
		r = new Ray(new Point(1, 1, -2), new Vector(-2, 0.5, -1));
		assertEquals(List.of(new Point(-1, 1.5, -3)), poly.findIntersections(r), "The ray goes through the triangle");
		// TC02: the ray is outside the triangle in front of one edge
		r = new Ray(new Point(4, 4, -2), new Vector(1, 1, -4));
		assertNull(poly.findIntersections(r), "The ray is outside the triangle in front of one edge");
		// TC03: the ray is outside the triangle in front of one vertex
		r = new Ray(new Point(-4, -1, -2), new Vector(-1, -1, -1));
		assertNull(poly.findIntersections(r), "The ray is outside the triangle in front of one vertex");

		// =============== Boundary Values Tests ==================
		// TC11: ray through edge
		r = new Ray(new Point(-2, 1, -1), new Vector(0, 0, -1));
		assertNull(poly.findIntersections(r), "ray through edge");
		// TC12: ray through vertex
		r = new Ray(new Point(3, 0, -2), new Vector(0, 0, -1));
		assertNull(poly.findIntersections(r), "ray through vertex");
		// TC13: ray goes through the continuation of side 1
		r = new Ray(new Point(-1, 4, -2), new Vector(0, 0, -1));
		assertNull(poly.findIntersections(r), "ray goes through the continuation of side 1");

	}
}