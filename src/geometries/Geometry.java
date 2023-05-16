package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometric shape that can be intersected
 * by a ray and has a normal vector
 * 
 * in every point on its surface.
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public abstract class Geometry extends Intersectable {
	protected Color emission = Color.BLACK;

	/**
	 * 
	 * Calculates the normal vector of a point on the surface of the geometry shape.
	 * 
	 * @param p the point on the surface of the shape
	 * @return the normal vector to the shape at the point p
	 */
	public abstract Vector getNormal(Point p);

	/**
	 * 
	 * Returns the emission color of the geometry.
	 * 
	 * @return the emission color of the geometry.
	 */
	public Color getEmission() {
		return emission;

	}

	/**
	 * 
	 * Sets the emission color of the geometry.
	 * 
	 * @param emission the emission color to set
	 * @return The geometry object itself (for method chaining)
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}
}
