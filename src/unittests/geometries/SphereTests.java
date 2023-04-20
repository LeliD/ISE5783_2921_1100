/**
 * 
 */
package unittests.geometries;
import primitives.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.List;
import geometries.Sphere;
import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for geometries.Sphere class
 * 
 * @author Lea and Shilat
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Sphere sp = new Sphere(new Point(0, 0, 0), 2);
		assertEquals(new Vector(0, 1, 0), sp.getNormal(new Point(0, 2, 0)), "");
	}
	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
	Sphere sphere = new Sphere(new Point (1, 0, 0), 1d);
	// ============ Equivalence Partitions Tests ==============
	// TC01: Ray's line is outside the sphere (0 points)
	assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),"Ray's line out of sphere");
	// TC02: Ray starts before and crosses the sphere (2 points)
    Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
	Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
	List<Point> result = sphere.findIntersections(new Ray(new Point(-1, 0, 0),new Vector(3, 1, 0)));
	assertEquals(2, result.size(), "Wrong number of points");
	//if (result.get(0).xyz.getX() > result.get(1).getX())
	  //result = List.of(result.get(1), result.get(0));
	assertEquals(List.of(p1, p2), result, "Ray crosses sphere");
	// TC03: Ray starts inside the sphere (1 point)
	Sphere sphere2 = new Sphere(new Point (0, -1, 0), 2d);
	result = sphere2.findIntersections(new Ray(new Point(-1, 0, 0),new Vector(3, -1, 0)));
	assertEquals(1, result.size(),"Wrong number of points");
	assertEquals(new Point(2, -1, 0), result.get(0),"Ray starts inside the sphere");
    // TC04: Ray starts after the sphere (0 points)
	assertNull(sphere2.findIntersections(new Ray(new Point(-3, 0, 0),new Vector(-1, 0, 0))),"Ray starts after the sphere and cross sphere ");
	// =============== Boundary Values Tests ==================
    // **** Group: Ray's line crosses the sphere (but not the center)
	// TC11: Ray starts at sphere and goes inside (1 points)
	result = sphere2.findIntersections(new Ray(new Point(0, -3, 0), new Vector(0, 2, 2)));
    assertEquals(new Point(0, -1, 2), result.get(0));
    // TC12: Ray starts at sphere and goes outside (0 points)
	result = sphere2.findIntersections(new Ray(new Point(0, -3, 0), new Vector(0, -2, -2)));
	assertNull(result);
    // **** Group: Ray's line goes through the center
	// TC13: Ray starts before the sphere (2 points)
	p1 = new Point(0, -3, 0);
	p2=  new Point(0, 1, 0);
	result = sphere2.findIntersections(new Ray(new Point(0, -4, 0), new Vector(0, 7, 0)));
	assertEquals(List.of(p1, p2), result);
	// TC14: Ray starts at sphere and goes inside (1 points)
	result = sphere2.findIntersections(new Ray(new Point(0, 1, 0), new Vector(0, -4, 0)));
	assertEquals(new Point(0, -3, 0), result);
	// TC15: Ray starts inside (1 points)
	result = sphere2.findIntersections(new Ray(new Point(0, -2, 0), new Vector(0, 5, 0)));
	assertEquals(new Point(0, 1, 0), result);
    // TC16: Ray starts at the center (1 points)
	result = sphere.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)));
	assertEquals(new Point(1, 1, 0), result);
    // TC17: Ray starts at sphere and goes outside (0 points)
	result = sphere2.findIntersections(new Ray(new Point(0, 1, 0), new Vector(0, 11, 0)));
	assertNull(result);
    // TC18: Ray starts after sphere (0 points)
	result = sphere2.findIntersections(new Ray(new Point(0, 4, 0), new Vector(0, 11, 0)));
	assertNull(result);
    // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
	// TC19: Ray starts before the tangent point
	result = sphere2.findIntersections(new Ray(new Point(-1, 1, 0), new Vector(2, 0, 0)));
	assertNull(result);
    // TC20: Ray starts at the tangent point
	result = sphere2.findIntersections(new Ray(new Point(0, 1, 0), new Vector(2, 0, 0)));
	assertNull(result);
    // TC21: Ray starts after the tangent point
	result = sphere2.findIntersections( new Ray(new Point(1, 1, 0), new Vector(2, 0, 0)));
	assertNull(result);
    // **** Group: Special cases
	// TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
	result = sphere2.findIntersections(new Ray(new Point(0, 6, 0), new Vector(2, 0, 0)));
	assertNull(result);
   }
}
