package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import static primitives.Util.*;

/**
 * The SpotLight class for representing a spotlight source. It extends the
 * PointLight class.
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class SpotLight extends PointLight {
	/** The direction vector of the spotlight. */
	private final Vector direction;
	/**
	 * The narrowBeam variable represents the narrow beam angle of a SpotLight
	 * object. The default value is 1.
	 */
	private double narrowBeam = 1;

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
		double dirDotL = Math.pow(alignZero(direction.dotProduct(getL(p))), narrowBeam);
		return dirDotL <= 0 ? Color.BLACK : super.getIntensity(p).scale(dirDotL);
	}

	/**
	 * 
	 * Sets the narrow beam angle for the spotlight.
	 * 
	 * @param narrowBeam the narrow beam angle
	 * @return the SpotLight object itself (for method chaining)
	 */
	public SpotLight setNarrowBeam(double narrowBeam) {
		this.narrowBeam = narrowBeam;
		return this;
	}
}
