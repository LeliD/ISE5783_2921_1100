/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class for representing a directional light source. It
 * extends the Light class and implements the LightSource interface.
 * 
 * @author Shilat Sharon and Lea Drach
 * 
 */
public class DirectionalLight extends Light implements LightSource {
	/** The direction vector of the light. */
	private final Vector direction;

	/**
	 * 
	 * Constructs a DirectionalLight object with the specified intensity and
	 * direction. The direction vector is normalized before being stored.
	 * 
	 * @param intensity the intensity of the light
	 * @param direction the direction vector of the light
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return intensity;
	}

	@Override
	public Vector getL(Point p) {
		return direction;
	}
	@Override
	public double getDistance(Point point) {
		return  Double.POSITIVE_INFINITY;
	}
}
