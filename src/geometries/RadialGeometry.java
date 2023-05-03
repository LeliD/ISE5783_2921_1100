package geometries;

/**
 * 
 * Abstract class RadialGeometry for representing radial geometries, which are
 * geometries with a certain radius.
 * 
 * Implements the Geometry interface.
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public abstract class RadialGeometry implements Geometry {
	/** The radius of the radial geometry. */
	protected final double radius;
	/** The square of the radius of the radial geometry. */
	protected final double radius2;

	/**

	Constructs a new RadialGeometry object with the given radius.
	@param r the radius of the radial geometry.
	*/
	public RadialGeometry(double r) {
		radius = r;
		radius2 = r * r;
	}

	/**

	Returns the radius of the radial geometry.
	@return the radius of the radial geometry.
	*/
	public double getRadius() {
		return radius;
	}

	@Override
	public String toString() {
		return "RadialGeometry [radius=" + radius + "]";
	}
}
