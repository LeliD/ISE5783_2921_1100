package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The SpotLight class for representing a spotlight source. It extends the
 * PointLight class.
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class SpotLight extends PointLight {
	/** The direction vector of the spotlight. */
	private Vector direction;

	/**
	 * 
	 * Constructs a SpotLight object with the specified intensity, position, and
	 * direction. The direction vector is normalized before being stored.
	 * 
	 * @param intensity the intensity of the light
	 * @param position  the position of the light source
	 * @param direction the direction vector of the spotlight
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		double pl = direction.dotProduct(getL(p));
		return pl <= 0 ? Color.BLACK : super.getIntensity(p).scale(pl);
	}
}
