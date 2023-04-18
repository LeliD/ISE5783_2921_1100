/**
 * 
 */
package geometries;
import primitives.*;
import java.util.List;
/**
 * Intersectable Interface.
 * Represents the general behavior of an intersectable object.
 * @author Lea and Shilat
 *
 */
public interface Intersectable 
{
	/**
	 * Finding intersection points on the geometry with a given ray
	 * @param ray - the ray to find intersection points with
	 * @return List of intersection-points on the geometry with the given ray
	 */
	public List<Point> findIntersections(Ray ray);
}
