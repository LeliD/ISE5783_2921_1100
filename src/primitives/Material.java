/**
 * 
 */
package primitives;

/**
 * 
 * The Material class represents the material properties of an object.
 * @author Shilat Sharon and Lea Drach
 */
public class Material {

	/** The diffuse reflection coefficient.  */
	public Double3 kD = Double3.ZERO;
	/** The specular reflection coefficient. */
	public Double3 kS = Double3.ZERO;
	/** The shininess value for controlling specular highlights. */
	public int nShininess = 0;

	/**
	 * 
	 * Sets the diffuse reflection coefficient of the material.
	 * 
	 * @param kD the diffuse reflection coefficient
	 * @return The Material object itself (for method chaining)
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * 
	 * Sets the specular reflection coefficient of the material.
	 * 
	 * @param kS the specular reflection coefficient
	 * @return The Material object itself (for method chaining)
	 */
	public Material setKs(Double3 kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * 
	 * Sets the diffuse reflection coefficient of the material using a single value.
	 * The same value is used for all color components (RGB).
	 * 
	 * @param kD the diffuse reflection coefficient
	 * @return The Material object itself (for method chaining)
	 */
	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;
	}

	/**
	 * 
	 * Sets the specular reflection coefficient of the material using a single
	 * value. The same value is used for all color components (RGB).
	 * 
	 * @param kS the specular reflection coefficient
	 * @return The Material object itself (for method chaining)
	 */
	public Material setKs(double kS) {
		this.kS = new Double3(kS);
		return this;
	}

	/**
	 * 
	 * Sets the shininess value of the material for controlling specular highlights.
	 * 
	 * @param nShininess the shininess value
	 * @return the updated Material object
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
}
