/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class for representing an ambient light source in a 3D
 * scene.
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class AmbientLight {
	/** The intensity of the ambient light */
	private final Color intensity;
	/** Default AmbientLight with black color and zero attenuation coefficient. */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * 
	 * Constructs an AmbientLight object with the specified intensity and
	 * attenuation coefficient.
	 * 
	 * @param iA The color of the ambient light
	 * @param kA The attenuation coefficient of the ambient light
	 */
	public AmbientLight(Color iA, Double3 kA) {
		intensity = iA.scale(kA);

	}

	/**
	 * 
	 * Constructs an AmbientLight object with the specified intensity and
	 * coefficient. This constructor takes a double value for the coefficient.
	 * 
	 * @param iA The color of the ambient light
	 * @param kA The coefficient of the ambient light
	 */
	public AmbientLight(Color iA, double kA) {
		intensity = iA.scale(kA);

	}

	/**
	 * 
	 * Returns the intensity of the ambient light.
	 * 
	 * @return The color representing the intensity of the ambient light
	 */
	public Color getIntensity() {
		return intensity;
	}
}
