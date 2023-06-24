/**
 * 
 */
package scene;

import geometries.Geometries;
import geometries.Intersectable;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

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
	/** A collection of light sources in the scene. */
	public List<LightSource> lights = new LinkedList<>();

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

	/**
	 * 
	 * Sets the list of light sources for the scene.
	 * 
	 * @param lights the list of light sources to set
	 * @return the Scene object itself (for method chaining)
	 */
	public Scene setLights(List<LightSource> lights) {
		this.lights = lights;
		return this;
	}
	/**
	 * Sets Conservative Bounding Region for creating the scene (for its 3D model).<br>
	 * It must be called <b><u>before</u></b> creating the 3D model (adding bodyes to the scene).
	 * @return scene object itself
	 */
	public Scene setCBR() {
		Intersectable.createCBR();
		return this;
	}

	/**
	 * Creates Bounding Volume Hierarchy in the scene's 3D model<br>
	 * It must be called <b><u>after</u></b> creating the 3D model (adding bodyes to the scene).
	 * @return scene object itself
	 */
	public Scene setBVH() {
		geometries.createBVH();	
		return this;
	}
}
