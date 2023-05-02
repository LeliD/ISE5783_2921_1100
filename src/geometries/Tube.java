package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.isZero;

import java.util.List;

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
		Vector direction = axisRay.getDir();
		Point p0 = axisRay.getP0();
		double t = direction.dotProduct(p.subtract(p0));// t=direction* (p-p0)
		return p.subtract(axisRay.getPoint(t)).normalize();

	}

	@Override
	public String toString() {
		return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
	}

	/**
	 * Finding intersection points on the geometry with a given ray
	 * 
	 * @param ray - the ray to find intersection points with
	 * @return List of intersection-points on the geometry with the given ray
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

}
