package geometries;

public abstract class RadialGeometry  implements Geometry
{
	protected final double radius;
	
	public RadialGeometry(double r) 
	{
		radius=r;
	}
	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "RadialGeometry [radius=" + radius + "]";
	}
}
