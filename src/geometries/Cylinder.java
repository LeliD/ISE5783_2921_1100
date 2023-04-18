package geometries;

import static primitives.Util.isZero;

import java.util.List;

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
		if (p.equals(axisRay.getP0()))// if point p is on the center of the cylinder's first base
			return axisRay.getDir().scale(-1);
		Point centerSecondBase = axisRay.getP0().add(axisRay.getDir().scale(height));
		if (p.equals(centerSecondBase))// if point p is on the center of the cylinder's upper base
			return axisRay.getDir();
		Vector v1 = p.subtract(axisRay.getP0());// p-p0
		if (v1.length() < radius && isZero(v1.dotProduct(axisRay.getDir())))// if point p is on the cylinder's first
																			// base
			return axisRay.getDir().scale(-1);
		Vector v2 = p.subtract(centerSecondBase);// p-centerSecondBase
		if (v2.length() < radius && isZero(v2.dotProduct(axisRay.getDir())))// if point p is on the cylinder's upper
																			// base
			return axisRay.getDir();
		// in case point p is on the cylinder's side
		return super.getNormal(p);
	}
	/**
	 * Finding intersection points on the geometry with a given ray
	 * @param ray - the ray to find intersection points with
	 * @return List of intersection-points on the geometry with the given ray
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
