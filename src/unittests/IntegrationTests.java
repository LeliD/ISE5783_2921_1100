/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;

/**
 * 
 * This class contains integration tests for intersectable geometries and camera
 * rays.
 * 
 * @author Shilat Sharon and Lea Drach
 */
public class IntegrationTests {

	/** Camera object for testing. */
	private Camera cam1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1)
			.setVPSize(3, 3);
	private Camera cam2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPDistance(1)
			.setVPSize(3, 3);
	/** pixels in row. */
	private int nX = 3;
	/** pixels in column. */
	private int nY = 3;

	/**
	 * 
	 * Counts the number of intersection points between an intersectable geometry
	 * and camera rays.
	 * 
	 * @param inter The intersectable geometry to test.
	 * @param cam   The camera to test.
	 * @return The number of intersection points.
	 */
	private int countIntersection(Intersectable inter, Camera cam) {
		List<Point> results; // List of intersection points of one intersectable
		int count = 0; // Counter of intersection points
		for (int i = 0; i < nX; ++i) {
			for (int j = 0; j < nY; ++j) {
				Ray ray = cam.constructRay(nX, nY, j, i);
				results = inter.findIntersections(ray);
				if (results != null)
					count += results.size();
			}
		}
		return count;
	}

	/**
	 * Tests the {@link Camera#constructRay(int, int, int, int)} method with a
	 * sphere.
	 */
	@Test
	public void constructRayThroughPixelWithSphere() {

		// ***** 2 intersection points*****
		Sphere sph = new Sphere(new Point(0, 0, -3), 1);
		assertEquals(2, countIntersection(sph, cam1), "ERROR: 2 points expected");

		// ***** 18 intersection points*****
		sph = new Sphere(new Point(0, 0, -2.5), 2.5);
		assertEquals(18, countIntersection(sph, cam2), "ERROR: 8 points expected");

		// ***** 10 intersection points*****
		sph = new Sphere(new Point(0, 0, -2), 2);
		assertEquals(10, countIntersection(sph, cam2), "ERROR: 10 points expected");

		// ***** 9 intersection points*****
		sph = new Sphere(new Point(0, 0, 1), 4);
		assertEquals(9, countIntersection(sph, cam2), "ERROR: 9 points expected");

		// ***** 0 intersection points*****
		sph = new Sphere(new Point(0, 0, 1), 0.5);
		assertEquals(0, countIntersection(sph, cam2), "ERROR: no points expected");
	}

	/**
	 * Test for Plane
	 */
	@Test
	public void constructRayThroughPixelWithPlane() {

		// ***** 9 intersection points*****
		Plane pl = new Plane(new Point(0, 0, -7), new Vector(0, 0, 1));
		assertEquals(9, countIntersection(pl, cam2), "ERROR: 9 points expected");

		// ***** 9 intersection points*****
		pl = new Plane(new Point(0, 0, -2), new Vector(0, -1, 3));
		assertEquals(9, countIntersection(pl, cam2), "ERROR: 9 points expected");

		// ***** 6 intersection points*****
		pl = new Plane(new Point(0, 0, -2), new Vector(0, -3, 1));
		assertEquals(6, countIntersection(pl, cam2), "ERROR: 6 points expected");
	}

	/**
	 * Test for triangle
	 */
	@Test
	public void constructRayThroughPixelWithTriangle() {

		// ***** One intersection point*****
		Triangle tr = new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 1, -2));
		assertEquals(1, countIntersection(tr, cam2), "ERROR: 1 points expected");

		// ***** Two intersection point*****
		tr = new Triangle(new Point(1, -1, -2), new Point(-1, -1, -2), new Point(0, 20, -2));
		assertEquals(2, countIntersection(tr, cam2), "ERROR: 2 points expected");
	}

}
