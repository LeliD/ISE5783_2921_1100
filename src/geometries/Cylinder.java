package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;


public class Cylinder extends Tube
{
	private final double height;
	/**
	 * @param ray
	 * @param radius
	 * @param height
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
