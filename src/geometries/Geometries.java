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
	private List<Intersectable> geometries=new LinkedList<>();
	/** List of infinities geometries */
	private final List<Intersectable> infinitiesGeometries = new LinkedList<>();
	/**
	 * Default constructor.
	 */
	public Geometries() {
	}

	/**
	 * 
	 * Constructs a geometries list with the provided Intersectable objects.
	 * 
	 * @param geometries An array of Intersectable objects to be added to the list
	 *                   of geometries.
	 */
	public Geometries(Intersectable... geometries) {
		//this.geometries = new LinkedList<Intersectable>(Arrays.asList(geometries));
		//createBox();
		add(geometries);
	}
	/**
	 * constructor that gets several intersectables and add them to the geometries
	 * list
	 * 
	 * @param geometries geometries to add to list
	 */
	public Geometries(List<Intersectable> geometries) {
		add(geometries);
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
		add(List.of(geometries));
		//if (geometries != null) {
		//	this.geometries.addAll(Arrays.asList(geometries));
		//	createBox();
		//}

	}

	/**
	 * Adds geometries to the list
	 * 
	 * @param geometries the geomtries to add
	 */
	public void add(List<Intersectable> geometries) {
		if (!cbr) {
			this.geometries.addAll(geometries);
			return;
		}

		for (var geometry : geometries) {
			if (geometry.box == null)
				infinitiesGeometries.add(geometry);
			else {
				this.geometries.add(geometry);
				if (infinitiesGeometries.isEmpty()) {
					if (box == null)
						box = new Box();
					if (geometry.box.x0 < box.x0)
						box.x0 = geometry.box.x0;
					if (geometry.box.y0 < box.y0)
						box.y0 = geometry.box.y0;
					if (geometry.box.z0 < box.z0)
						box.z0 = geometry.box.z0;
					if (geometry.box.x1 > box.x1)
						box.x1 = geometry.box.x1;
					if (geometry.box.y1 > box.y1)
						box.y1 = geometry.box.y1;
					if (geometry.box.z1 > box.z1)
						box.z1 = geometry.box.z1;
				}
			}
		}
		// if there are inifinities objects
		if (!infinitiesGeometries.isEmpty())
			box = null;
	}


	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> intersections = null;

		//if (box != null && box.IntersectionBox(ray) == false)
		//	return null;

		for (Intersectable geo : geometries) {
			List<GeoPoint> IntersectionsPerGeometry = geo.findGeoIntersections(ray);// list of single geometry
			if (IntersectionsPerGeometry != null) {
				if (intersections == null)// for the first time
					intersections = new LinkedList<>();
				intersections.addAll(IntersectionsPerGeometry);
			}
		}
		for (Intersectable geo : infinitiesGeometries) {
			List<GeoPoint> IntersectionsPerGeometry = geo.findGeoIntersections(ray);// list of single geometry
			if (IntersectionsPerGeometry != null) {
				if (intersections == null)// for the first time
					intersections = new LinkedList<>();
				intersections.addAll(IntersectionsPerGeometry);
			}
		}
		return intersections;

	}

	

	/**
	 * puts the geometries in the right boxes and creates a fitting tree
	 */
	public void createBVH() {
		if (!cbr)
			return;
		// min amount of geometries in a box is 2
		if (geometries.size() <= 4)
			return;

		if (box == null) {
			var finites = new Geometries(geometries);
			geometries.clear();
			geometries.add(finites);
			return;
		}

		double x = box.x1 - box.x0;
		double y = box.y1 - box.y0;
		double z = box.z1 - box.z0;
		// which axis we are reffering to
		final char axis = y > x && y > z ? 'y' : z > x && z > y ? 'z' : 'x';
		
		var left = new Geometries();
		var middle = new Geometries();
		var right = new Geometries();
		double midX = (box.x1 + box.x0) / 2;
		double midY = (box.y1 + box.y0) / 2;
		double midZ = (box.z1 + box.z0) / 2;
		switch (axis) {
		case 'x':
			for (var g : geometries) {
				if (g.box.x0 > midX)
					right.add(g);
				else if (g.box.x1 < midX)
					left.add(g);
				else
					middle.add(g);
			}
			break;
		case 'y':
			for (var g : geometries) {
				if (g.box.y0 > midY)
					right.add(g);
				else if (g.box.y1 < midY)
					left.add(g);
				else
					middle.add(g);
			}
			break;
		case 'z':
			for (var g : geometries) {
				if (g.box.z0 > midZ)
					right.add(g);
				else if (g.box.z1 < midZ)
					left.add(g);
				else
					middle.add(g);
			}
			break;
		}


		geometries.clear();
		if (left.geometries.size() <= 2)
			geometries.addAll(left.geometries);
		else {
			left.createBVH();
			geometries.add(left);
		}

		if (middle.geometries.size() <= 2)
			geometries.addAll(middle.geometries);
		else
			geometries.add(middle);
		
		if (right.geometries.size() <= 2)
			geometries.addAll(right.geometries);
		else {
			right.createBVH();
			geometries.add(right);
		}
	}


}
