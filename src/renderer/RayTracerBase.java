/**
 * 
 */
package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * 
 * Abstract class RayTracerBase for representing ray tracing implementations.
 * 
 * @author Shilat Sharon and Lea Drach
 */
public abstract class RayTracerBase {
	/** The scene to be rendered */
	protected final Scene scene;

	/**
	 * 
	 * Constructs a RayTracerBase object with the specified scene.
	 * 
	 * @param scene The scene to be rendered
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * 
	 * Traces a ray and returns the color of the intersected object or background.
	 * This method needs to be implemented by concrete subclasses.
	 * 
	 * @param ray The ray to be traced
	 * @return The color of the intersected object or the background color.
	 */
	public abstract Color traceRay(Ray ray);
}
