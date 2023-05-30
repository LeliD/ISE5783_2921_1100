/**
 * 
 */
package primitives;

/**
 * 
 * The Material class represents the material properties of an object.
 * 
 * @author Shilat Sharon and Lea Drach
 */
public class Material {

	/** The diffuse reflection coefficient. */
	public Double3 kD = Double3.ZERO;
	/** The specular reflection coefficient. */
	public Double3 kS = Double3.ZERO;
	/** The shininess value for controlling specular highlights. */
	public int nShininess = 0;
	/** The transparency coefficient. */
	public Double3 kT = Double3.ZERO;
	/** The reflection coefficient. */
	public Double3 kR = Double3.ZERO;

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
	 * @return The Material object itself (for method chaining)
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

	/**
	 * 
	 * Sets the transparency coefficient of the material to the specified Double3
	 * object.
	 * 
	 * @param kT the Double3 object representing the new transparency coefficient
	 * @return The Material object itself (for method chaining)
	 */
	public Material setKt(Double3 kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * 
	 * Sets the reflection coefficient of the material to the specified Double3
	 * object.
	 * 
	 * @param kR the Double3 object representing the new reflection coefficient
	 * @return The Material object itself (for method chaining)
	 */
	public Material setKr(Double3 kR) {
		this.kR = kR;
		return this;
	}

	/**
	 * 
	 * Sets the transparency coefficient of the material to the specified double
	 * value. The double value is converted to a Double3 object using the
	 * constructor.
	 * 
	 * @param kT the double value representing the new transparency coefficient
	 * @return The Material object itself (for method chaining)
	 */
	public Material setKt(double kT) {
		this.kT = new Double3(kT);
		return this;
	}

	/**
	 * 
	 * Sets the reflection coefficient of the material to the specified double
	 * value. The double value is converted to a Double3 object using the
	 * constructor.
	 * 
	 * @param kR the double value representing the new reflection coefficient
	 * @return The Material object itself (for method chaining)
	 */
	public Material setKr(double kR) {
		this.kR = new Double3(kR);
		return this;
	}
}
