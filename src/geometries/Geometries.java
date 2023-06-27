/**
 * 
 */
package geometries;

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
	private List<Intersectable> geometries = new LinkedList<>();
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
		// this.geometries = new LinkedList<Intersectable>(Arrays.asList(geometries));
		// createBox();
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

	}

	/**
	 * Adds geometries to the list
	 * 
	 * @param geometries the geomtries to add
	 */
	public void add(List<Intersectable> geometries) {
		// if CBR improvement is off
		if (!cbr) {
			this.geometries.addAll(geometries);
			return;
		}
		// CBR improvement is on
		for (var geometry : geometries) {
			// box is null => infinity geometry
			if (geometry.box == null)
				infinitiesGeometries.add(geometry);
			// geometry with a box
			else {
				this.geometries.add(geometry);
				// if there is an infinity geometry in the geometries, there is no box
				if (infinitiesGeometries.isEmpty()) {
					if (box == null)
						box = new Box();
					if (geometry.box.minX < box.minX)
						box.minX = geometry.box.minX;
					if (geometry.box.minY < box.minY)
						box.minY = geometry.box.minY;
					if (geometry.box.minZ < box.minZ)
						box.minZ = geometry.box.minZ;
					if (geometry.box.maxX > box.maxX)
						box.maxX = geometry.box.maxX;
					if (geometry.box.maxY > box.maxY)
						box.maxY = geometry.box.maxY;
					if (geometry.box.maxZ > box.maxZ)
						box.maxZ = geometry.box.maxZ;
				}
			}
		}
		// if there are infinities objects, there is no box
		if (!infinitiesGeometries.isEmpty())
			box = null;
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> intersections = null;

		for (Intersectable geo : geometries) {
			// list of single geometry
			List<GeoPoint> IntersectionsPerGeometry = geo.findGeoIntersections(ray);
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

		if (geometries.size() <= 4)
			return;

		if (box == null) {
			var finites = new Geometries(geometries);
			geometries.clear();
			geometries.add(finites);
			return;
		}

		double x = box.maxX - box.minX;
		double y = box.maxY - box.minY;
		double z = box.maxZ - box.minZ;
		// the axis we are referring to is the longest
		final char axis = y > x && y > z ? 'y' : z > x && z > y ? 'z' : 'x';

		var left = new Geometries();
		var middle = new Geometries();
		var right = new Geometries();
		double midX = (box.maxX + box.minX) / 2;
		double midY = (box.maxY + box.minY) / 2;
		double midZ = (box.maxZ + box.minZ) / 2;
		switch (axis) {
		case 'x':
			for (var g : geometries) {
				if (g.box.minX > midX)
					right.add(g);
				else if (g.box.maxX < midX)
					left.add(g);
				else
					middle.add(g);
			}
			break;
		case 'y':
			for (var g : geometries) {
				if (g.box.minY > midY)
					right.add(g);
				else if (g.box.maxY < midY)
					left.add(g);
				else
					middle.add(g);
			}
			break;
		case 'z':
			for (var g : geometries) {
				if (g.box.minZ > midZ)
					right.add(g);
				else if (g.box.maxZ < midZ)
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
