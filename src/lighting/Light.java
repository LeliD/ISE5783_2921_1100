/**
 * 
 */
package lighting;

import primitives.Color;

/**
 * The abstract class Light for representing a light source.
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
abstract class Light {
	/** The intensity of the light. */
	private final Color intensity;

	/**
	 * 
	 * Constructs a Light object with the specified intensity.
	 * 
	 * @param intensity the intensity of the light
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * 
	 * Returns the intensity of the light.
	 * 
	 * @return the intensity of the light
	 */
	public Color getIntensity() {
		return intensity;
	}
}
