package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Sphere class for representing a Sphere, extends RadialGeometry
 * 
 * @author Shilat Sharon and Leli Drach
 *
 */
public class Sphere extends RadialGeometry {
	/** center of a Sphere */
	private final Point center;

	/**
	 * Constructor to initialize a Sphere
	 * 
	 * @param p center of a Sphere
	 * @param r radius of a Sphere
	 */
	public Sphere(Point p, double r) {
		super(r);
		center = p;
	}

	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	@Override
	public String toString() {
		return "Sphere [center=" + center + ", radius=" + radius + "]";
	}

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}
}
