package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class for representing a Tube, extends RadialGeometry
 * 
 * @author Shilat Sharon and Leli Drach
 *
 */
public class Tube extends RadialGeometry {
	/** Ray of Tube */
	protected final Ray axisRay;

	/**
	 * Constructor to initialize a Tube
	 * 
	 * @param ray    Ray of Tube
	 * @param radius radius of Tube
	 */
	public Tube(Ray ray, double radius) {
		super(radius);
		axisRay = ray;
	}

	/**
	 * @return the axisRay
	 */
	public Ray getAxisRay() {
		return axisRay;
	}

	@Override
	public Vector getNormal(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
	}

}
