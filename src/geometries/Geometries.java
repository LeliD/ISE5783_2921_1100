/**
 * 
 */
package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import primitives.Ray;

/**
 * Geometries class for representing a collection of Intersectable geometries by
 * composite structural pattern
 * 
 * @author Shilat Sharon and Lea Drach
 */
public class Geometries extends Intersectable {
	/** List of intersectable geometries */
	private List<Intersectable> geometries;

	/**
	 * Default constructor, creates an empty list of geometries.
	 */
	public Geometries() {
		geometries = new LinkedList<Intersectable>();
	}

	/**
	 * 
	 * Constructs a geometries list with the provided Intersectable objects.
	 * 
	 * @param geometries An array of Intersectable objects to be added to the list
	 *                   of geometries.
	 */
	public Geometries(Intersectable... geometries) {
		this.geometries = new LinkedList<Intersectable>(Arrays.asList(geometries));
	}

	/**
	 * 
	 * Returns the list of intersectable geometries.
	 * 
	 * @return The list of intersectable geometries.
	 */
	public List<Intersectable> getGeometries() {
		return geometries;
	}

	/**
	 * 
	 * Adds the provided Intersectable objects to the list of geometries.
	 * 
	 * @param geometries An array of Intersectable objects to be added to the list
	 *                   of geometries.
	 */
	public void add(Intersectable... geometries) {
		if (geometries != null) {
			this.geometries.addAll(Arrays.asList(geometries));
		}

	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> intersections = null;

		for (Intersectable geo : geometries) {
			List<GeoPoint> IntersectionsPerGeometry = geo.findGeoIntersections(ray);// list of single geometry
			if (IntersectionsPerGeometry != null) {
				if (intersections == null)// for the first time
					intersections = new LinkedList<>();
				intersections.addAll(IntersectionsPerGeometry);
			}
		}
		return intersections;

	}

}
