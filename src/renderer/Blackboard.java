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
 * @author Shilat Sharon
 *
 */
public class Blackboard {
	private final int nX;
	private final int nY;
	private final Vector vTo;
	private final Vector vUp;
	private final Vector vRight;
	private double distance = 1;
	private final double gridSize;
	private final Point pc;
	private final Point p0;

	public Blackboard(int nXY, Ray mainRay, double distance, double gd) {
		this.nX = nXY;
		this.nY = nXY;
		this.vTo = mainRay.getDir();
		this.vUp = vTo.findOrthogonal();
		this.vRight = vUp.crossProduct(vTo).normalize();
		this.distance = distance;
		this.gridSize = gd * 2;
		this.p0 = mainRay.getP0();
		this.pc = p0.add(vTo.scale(distance));

	}

	/**
	 * Generates jittered grid rays for a given scene.
	 *
	 * @return A list of Ray objects representing the jittered grid rays.
	 */
	public List<Ray> gridRays() {
		List<Ray> rays = new LinkedList<>();
		double sizeOfCube = gridSize / nX;// height of each pixel
		// double rX = gridSize / nX;// width of each pixel
		Random random = new Random(); // Create a random number generator

		for (int i = 0; i < nY; i++)
			for (int j = 0; j < nX; j++) {

				// Calculate the jittered offsets between -0.5sizeOfCube to 0.5sizeOfCube for x
				// and y coordinates
				double xOffset = random.nextDouble() * sizeOfCube - sizeOfCube / 2;
				double yOffset = random.nextDouble() * sizeOfCube - sizeOfCube / 2;

				// Calculate the middle of the jittered pixel (xj, yi)
				double yi = (i - (nY - 1) / 2d) * sizeOfCube + xOffset;
				double xj = (j - (nX - 1) / 2d) * sizeOfCube + yOffset;

				Point pij = pc;

				if (alignZero(xj) != 0)
					pij = pij.add(vRight.scale(xj));

				if (alignZero(yi) != 0)
					pij = pij.add(vUp.scale(-yi));
				rays.add(new Ray(p0, pij.subtract(p0)));
				// return new Ray(p0, pij.subtract(p0));

			}
		return rays;
	}

}
