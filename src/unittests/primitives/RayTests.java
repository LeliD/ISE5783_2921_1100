/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

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

}
