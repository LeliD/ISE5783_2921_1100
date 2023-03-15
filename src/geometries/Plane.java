package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry
{
	
	private final Point p0;
	private final Vector normal;
	public Plane(Point p1,Point p2,Point p3) 
	{
		p0=p1;
		normal=null;
	}
	public Plane(Point p,Vector v) 
	{
		p0=p;
		normal=v.normalize();
	}
	/**
	 * @return the p0
	 */
	public Point getP0() {
		return p0;
	}
	
	/**
	 * @return the normal
	 */
	public Vector getNormal(){ return normal; }
	public Vector getNormal(Point point) { return normal; }

	@Override
	public String toString() {
		return "Plane [" + (p0!=null ? "p0=" + p0 + ", " : "")+(normal!=null ? "normal= " + normal : "") + "]";
	}
}
