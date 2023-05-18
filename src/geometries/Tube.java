package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class for representing a Tube, extends RadialGeometry
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class Tube extends RadialGeometry {
	/**
	 * The axis ray of the tube, which represents the center of the tube and its
	 * direction.
	 */
	protected final Ray axisRay;

	/**
	 * 
	 * Constructs a tube with the specified axis ray and radius.
	 * 
	 * @param ray    the axis ray of the tube
	 * @param radius the radius of the tube
	 */
	public Tube(Ray ray, double radius) {
		super(radius);
		axisRay = ray;
	}

	/**
	 * 
	 * Returns the axis ray of the tube.
	 * 
	 * @return the axis ray of the tube
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

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}

}
