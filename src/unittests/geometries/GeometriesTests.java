/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import geometries.Geometries;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Testing Geometries
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
class GeometriesTests {

	/**
	 * Test method for
	 * {@link geometries.Geometries#findIntersections(primitives.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		Plane plane = new Plane(new Point(0, 0, 0), new Vector(1, 0, 0));
		Triangle triangle = new Triangle(new Point(2, 0, 1), new Point(2, 0, -1), new Point(2, 1, 0));
		Sphere sphere = new Sphere(new Point(4, 0, 0), 1d);
		Polygon polygon = new Polygon(new Point(9, 0, 1), new Point(9, 3, 1), new Point(9, 3, -1), new Point(9, 0, -1));
		Geometries _geometries = new Geometries(plane, triangle, sphere, polygon);
		Geometries emptyGeometries = new Geometries();// an empty list of geometries

		// ============ Equivalence Partitions Tests ==============

		// TC01: a few geometries have intersection points
		// the ray cross the plane and polygon
		assertEquals(2, _geometries.findIntersections(new Ray(new Point(-1, 2, 0), new Vector(1, 0, 0))).size(),
				"Some geometries have intersection points");

		// =============== Boundary Values Tests ==================

		// TC11: empty list of geometries
		assertNull(emptyGeometries.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0))),
				"An empty list");
		// TC12: no geometry has intersection points
		assertNull(_geometries.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(0, 1, 0))),
				"Non geometry has intersection points");
		// TC13: one geometry only has intersection points (with the sphere)
		assertEquals(2, _geometries.findIntersections(new Ray(new Point(2.5, 0, 0), new Vector(4, 0, 0))).size(),
				"one geometry has intersection points");
		// TC14: all geometries have intersection points
		assertEquals(5, _geometries.findIntersections(new Ray(new Point(-1, 0.5, 0), new Vector(1, 0, 0))).size(),
				"All geometries has intersection points");
	}

}
