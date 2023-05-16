/**
 * 
 */
package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 * Scene class for representing a 3D scene that contains objects, lights, and a
 * background color.
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public class Scene {
	/** The name of the scene */
	public final String name;
	/** The background color of the scene */
	public Color background = Color.BLACK;
	/** The ambient light in the scene */
	public AmbientLight ambientLight = AmbientLight.NONE;
	/** The geometries objects in the scene */
	public Geometries geometries = new Geometries();

	/**
	 * 
	 * Constructs a Scene object with the specified name.
	 * 
	 * @param name The name of the scene
	 */
	public Scene(String name) {
		this.name = name;
	}

	/**
	 * 
	 * Sets the background color of the scene.
	 * 
	 * @param background The background color to set
	 * @return The Scene object itself (for method chaining)
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * 
	 * Sets the ambient light in the scene.
	 * 
	 * @param light The ambient light to set
	 * @return The Scene object itself (for method chaining)
	 */
	public Scene setAmbientLight(AmbientLight light) {
		this.ambientLight = light;
		return this;

	}

	/**
	 * 
	 * Sets the geometries objects in the scene.
	 * 
	 * @param geometries The geometries to set
	 * @return The Scene object itself (for method chaining)
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;

	}
}
