package geometries;

import static primitives.Util.isZero;

import java.util.List;

import primitives.*;

/**
 * Cylinder class for representing a Cylinder, extends Tube
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class Cylinder extends Tube {
	/** The height of Cylinder */
	private final double height;

	/**

	Constructs a cylinder with the specified axis ray, radius, and height.
	@param ray the axis ray of the cylinder
	@param radius the radius of the cylinder
	@param height the height of the cylinder
	*/
	public Cylinder(Ray ray, double radius, double height) {
		super(ray, radius);
		this.height = height;
	}

	/**

	Returns the height of this cylinder.
	@return the height of this cylinder
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
			return axisRay.getDir().scale(-1);
		Vector v2 = p.subtract(centerSecondBase);// p-centerSecondBase
		if (v2.length() < radius && isZero(v2.dotProduct(axisRay.getDir())))// if point p is on the cylinder's upper
			return axisRay.getDir();
		// in case point p is on the cylinder's side
		return super.getNormal(p);
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
