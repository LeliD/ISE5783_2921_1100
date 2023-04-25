/**
 * 
 */
package geometries;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * Geometries' class. Represents a collection of geometries by composite
 * structural pattern
 * 
 * @author Shilat Sharon and Lea Drach
 */
public class Geometries implements Intersectable {
	private List<Intersectable> geometries;

	/* ********* Constructors ***********/
	/**
	 * default constructor
	 */
	public Geometries() {
		geometries = new LinkedList<Intersectable>();
	}

	/**
	 * Constructor that receives geometries and initializes the LinkedList with them
	 * 
	 * @param geometries - several geometries
	 */
	public Geometries(Intersectable... geometries) {
		this.geometries = new LinkedList<Intersectable>(Arrays.asList(geometries));
	}

	/* ************* Operations ***************/

	/**
	 * get geometries
	 * 
	 * @return geometries list
	 */
	public List<Intersectable> getGeometries() {
		return geometries;
	}

	/**
	 * add geometry
	 * 
	 * @param geometries the geometry
	 */
	public void add(Intersectable... geometries) {
		if (geometries != null) {
			this.geometries.addAll(Arrays.asList(geometries));
		}

	}

	/**
	 * @param ray - the ray that intersect the geometries
	 * @return list of Point that intersect the collection
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersections = null;

		for (Intersectable geo : geometries) {
			List<Point> IntersectionsPerGeometry = geo.findIntersections(ray);// list of single geometry
			if (IntersectionsPerGeometry != null) {
				if (intersections == null)// for the first time
					intersections = new LinkedList<>();
				intersections.addAll(IntersectionsPerGeometry);
			}
		}
		return intersections;

	}

}
