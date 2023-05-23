/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The LightSource interface for representing a light source.
 * 
 * @author Shilat Sharon and Lea Drach
 * 
 */
public interface LightSource {

	/**
	 * 
	 * Returns the intensity of the light at the specified point.
	 * 
	 * @param p the point at which the intensity is calculated
	 * @return the intensity of the light at the specified point
	 */
	public Color getIntensity(Point p);

	/**
	 * 
	 * Returns the direction vector from the light source towards the specified
	 * point.
	 * 
	 * @param p the point the direction is calculated to
	 * @return the direction vector from the light source towards the specified
	 *         point.
	 */
	public Vector getL(Point p);

	/**
	 * 
	 * Calculates the distance between the light source and a given point.
	 * 
	 * @param point The point for which the distance is calculated
	 * @return The distance between the light source and the point
	 */
	double getDistance(Point point);
}
