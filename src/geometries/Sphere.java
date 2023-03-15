package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry  
{
	private final Point center;
	
	public Sphere(Point p,double r) 
	{
		super(r);
		center=p;
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
