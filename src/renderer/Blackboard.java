/**
 * 
 */
package renderer;

import static primitives.Util.alignZero;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The Blackboard class represents a blackboard in a scene, used for generating
 * jittered grid rays.
 * 
 * @author Shilat Sharon and Lea Drach
 */
public class Blackboard {
	/* The resolution of the Blackboard */
	private final int nXY;
	/* The vTo Vector of the Blackboard */
	private final Vector vTo;
	/* The vUp Vector of the Blackboard */
	private final Vector vUp;
	/* The vRight Vector of the Blackboard */
	private final Vector vRight;
	/* The distance to the Blackboard */
	private final double distance;
	/* The length and height size of the Blackboard */
	private final double gridSize;
	/* The center point of the Blackboard */
	private final Point pc;
	/* The head of the main transparency/reflection ray. (vTo of the Blackboard) */
	private final Point p0;

	/**
	 * Constructs a Blackboard object with the specified parameters.
	 *
	 * @param nXY      The number of pixels in each row/column of the grid.
	 * @param mainRay  The transparency or reflection ray in the scene.
	 * @param distance The distance between the blackboard and the main ray's
	 *                 origin.
	 * @param gd       The size of the grid.
	 */
	public Blackboard(int nXY, Ray mainRay, double distance, double gd) {
		this.nXY = nXY;
		this.vTo = mainRay.getDir();
		this.vUp = vTo.findOrthogonal();
		this.vRight = vUp.crossProduct(vTo).normalize();
		this.distance = distance;
		this.gridSize = gd * 2;
		this.p0 = mainRay.getP0();
		this.pc = p0.add(vTo.scale(this.distance));

	}

	/**
	 * Generates jittered grid rays for a given scene.
	 *
	 * @return A list of Ray objects representing the jittered grid rays.
	 */
	public List<Ray> gridRays(Vector n, int direction) {
		List<Ray> rays = new LinkedList<>();
		double sizeOfCube = gridSize / nXY;// height of each pixel
		// double rX = gridSize / nX;// width of each pixel
		Random random = new Random(); // Create a random number generator
		n = n.dotProduct(vTo) > 0 ? n.scale(-direction) : n.scale(direction);// fix
		// the normal direction
		for (int i = 0; i < nXY; i++)
			for (int j = 0; j < nXY; j++) {

				// Calculate the jittered offsets between -0.5sizeOfCube to 0.5sizeOfCube for x
				// and y coordinates
				double xOffset = random.nextDouble() * sizeOfCube - sizeOfCube / 2;
				double yOffset = random.nextDouble() * sizeOfCube - sizeOfCube / 2;

				// Calculate the middle of the jittered pixel (xj, yi)
				double yi = (i - (nXY - 1) / 2d) * sizeOfCube + xOffset;
				double xj = (j - (nXY - 1) / 2d) * sizeOfCube + yOffset;

				Point pij = pc;

				if (alignZero(xj) != 0)
					pij = pij.add(vRight.scale(xj));

				if (alignZero(yi) != 0)
					pij = pij.add(vUp.scale(-yi));

				Vector tempRayVector = pij.subtract(p0);

				if (n.dotProduct(tempRayVector) < 0 && direction == 1) // transparency
					rays.add(new Ray(p0, tempRayVector, n));
				if (n.dotProduct(tempRayVector) > 0 && direction == -1) // reflection
					rays.add(new Ray(p0, tempRayVector, n));
				rays.add(new Ray(p0, pij.subtract(p0)));
				// return new Ray(p0, pij.subtract(p0));

			}
		return rays;
	}

}
