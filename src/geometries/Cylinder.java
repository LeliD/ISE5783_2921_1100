package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class for representing a Cylinder, extends Tube
 * 
 * @author Shilat Sharon and Leli Drach
 *
 */
public class Cylinder extends Tube {
	/** height of Cylinder */
	private final double height;

	/**
	 * @param ray    Ray of Cylinder
	 * @param radius radius of Cylinder
	 * @param height height of Cylinder
	 */
	public Cylinder(Ray ray, double radius, double height) {
		super(ray, radius);
		this.height = height;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return "Cylinder [height=" + height + ", axisRay=" + axisRay + ", radius=" + radius + "]";
	}

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}
}
