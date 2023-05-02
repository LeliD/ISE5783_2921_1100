package geometries;

/**
 * RadialGeometry class for representing a Radial Geometry which implements
 * Geometry interface
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public abstract class RadialGeometry implements Geometry {
	/** parameter of radius */
	protected final double radius;
	protected final double radius2;

	/**
	 * Constructor to initialize RadialGeometry
	 * 
	 * @param r radius
	 */
	public RadialGeometry(double r) {
		radius = r;
		radius2 = r * r;
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
