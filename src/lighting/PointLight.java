/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * 
 * The PointLight class for representing a point light source.
 * 
 * It extends the Light class and implements the LightSource interface.
 * 
 * @author Shilat Sharon and Lea Drach
 */
public class PointLight extends Light implements LightSource {

	/** The position of the light source. */
	private Point position;
	/** The quadratic attenuation factor. */
	private double kQ = 0;
	/** The linear attenuation factor. */
	private double kL = 0;
	/** The constant attenuation factor. */
	private double kC = 1;

	/**
	 * 
	 * Constructs a PointLight object with the specified intensity and position.
	 * 
	 * @param intensity the intensity of the light
	 * @param position  the position of the light source
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	/**
	 * 
	 * Sets the quadratic attenuation factor of the light.
	 * 
	 * @param kQ the quadratic attenuation factor
	 * @return the PointLight object itself (for method chaining)
	 */
	public PointLight setKq(double kQ) {
		this.kQ = kQ;
		return this;
	}

	/**
	 * 
	 * Sets the linear attenuation factor of the light.
	 * 
	 * @param kL the linear attenuation factor
	 * @return the PointLight object itself (for method chaining)
	 */
	public PointLight setKl(double kL) {
		this.kL = kL;
		return this;
	}

	/**
	 * 
	 * Sets the constant attenuation factor of the light.
	 * 
	 * @param kC the constant attenuation factor
	 * @return the PointLight object itself (for method chaining)
	 */
	public PointLight setKc(double kC) {
		this.kC = kC;
		return this;
	}

	@Override
	public Color getIntensity(Point p) {
		double distance2 = p.distanceSquared(position);
		double distance = p.distance(position);
		return getIntensity().scale(1 / (kC + kL * distance + kQ * distance2));
	}

	@Override
	public Vector getL(Point p) {
		return p.equals(position) ? null : p.subtract(position).normalize();
	}
	@Override
	public double getDistance(Point point) {
		return position.distance(point);
	}
}
