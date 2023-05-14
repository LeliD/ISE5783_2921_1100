/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import primitives.*;
/**
 * @author lelid
 *
 */
class RayTests {

	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}.
	 */
	@Test
	void testGetPoint() {
        Ray ray = new Ray(new Point(0,0,1), new Vector(1,0,0));
		
		// ============ Equivalence Partitions Tests ==============
        // TC01: When t is positive
        assertEquals(new Point(1,0,1), ray.getPoint(1),"Error: wrong point on the ray");
        // TC02: When t is negative
        assertEquals(new Point(-1,0,1), ray.getPoint(-1),"Error: wrong point on the ray");
        
        // =============== Boundary Values Tests ==================
        // TC11: When t is 0, and the wanted point is the ray's starting-point
        assertEquals(new Point(0,0,1), ray.getPoint(0),"Error: wrong point on the ray");
	}
	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
	 */
	@Test
	public void testFindClosestPoint() {
		Ray ray=new Ray(new Point(0,0,0),new Vector(0,0,1));
		// ============ Equivalence Partitions Tests ==============
		// TC01:A point in the middle of the list is closest point
		List<Point> points=List.of(new Point(0,0,5),new Point(0,0,0.5),new Point(0,0,1));
		assertEquals(new Point(0,0,0.5), ray.findClosestPoint(points),"Error: wrong ClosestPoint (in the middle of the list)");
		
		// =============== Boundary Values Tests ==================
	    // TC11:Null and Empty list (method should return null value)
		List<Point> emptyList=List.of();
		assertNull(ray.findClosestPoint(null),"Error: there is no closest point");
		assertNull(ray.findClosestPoint(emptyList),"Error: there is no closest point");
		// TC12:The first point is closest point
		points=List.of(new Point(0,0,0.5), new Point(0,0,5),new Point(0,0,1));
		assertEquals(new Point(0,0,0.5), ray.findClosestPoint(points),"Error: wrong ClosestPoint (in the beginning of the list)");
		// TC13: The last point is closest point
		points=List.of( new Point(0,0,5),new Point(0,0,1), new Point(0,0,0.5));
		assertEquals(new Point(0,0,0.5), ray.findClosestPoint(points),"Error: wrong ClosestPoint (in the end of the list)");
		
	}
}
