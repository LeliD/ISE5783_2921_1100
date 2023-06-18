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
		super(null);
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
		super(null);
		this.geometries = new LinkedList<Intersectable>(Arrays.asList(geometries));
		createBox();
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
			createBox();
		}
		

	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		List<GeoPoint> intersections = null;
		
		if (box != null && box.IntersectionBox(ray) == false)
			return null;

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

	/**
	 * Creating a box containing all the geometries boxes
	 */
	private void createBox() {
		// Restart
		double x1 = Double.NEGATIVE_INFINITY;
		double x0 = Double.POSITIVE_INFINITY;
		double y1 = Double.NEGATIVE_INFINITY;
		double y0 = Double.POSITIVE_INFINITY;
		double z1 = Double.NEGATIVE_INFINITY;
		double z0 = Double.POSITIVE_INFINITY;
		// Adjust the size of the box to contain all the boxes
		for (Intersectable geo : geometries) {
			if (geo.getBox().getX0() < x0)
				x0 = geo.getBox().getX0();
			if (geo.getBox().getX1() > x1)
				x1 = geo.getBox().getX1();
			if (geo.getBox().getY0() < y0)
				y0 = geo.getBox().getY0();
			if (geo.getBox().getY1() > y1)
				y1 = geo.getBox().getY1();
			if (geo.getBox().getZ0() < z0)
				z0 = geo.getBox().getZ0();
			if (geo.getBox().getZ1() > z1)
				z1 = geo.getBox().getZ1();
		}
		this.box = new Box(x0, x1, y0, y1, z0, z1);
	}
	/**
	 * puts the geometries in the right boxes and creates a fitting tree
	 */
	public void createBVH() {
		if (geometries.size() <= 2) {//if there are two geometries in the box
			return;
		}
		char axis = 'x';
		double x = box.getX1() - box.getX0();
		double y = box.getY1() - box.getY0();
		double z = box.getZ1() - box.getZ0();

		if (y > x && y > z) {
			axis = 'y';
		}
		if (z > x && z > y) {
			axis = 'z';
		}

		bubbleSort(geometries, axis);

		Geometries left = new Geometries();
		Geometries right = new Geometries();
		for (int i = 0; i < geometries.size() / 2; i++) {
			left.add(geometries.get(i));
		}
		for (int i = geometries.size() / 2; i < geometries.size(); i++) {
			right.add(geometries.get(i));
		}
		right.createBVH();
		left.createBVH();

		geometries.clear();
		geometries.add(left);
		geometries.add(right);
	}

	/**
	 * sorts the list of intersectables with bubble sort
	 * 
	 * @param L list of geometries
	 * @param axis the axis that the geometries are sorted by it
	 */
	private void bubbleSort(List<Intersectable> L, char axis) {
		int n = L.size();
		for (int i = 0; i < n - 1; i++)
			for (int j = 0; j < n - i - 1; j++) {
				if (sizeRelToAxis(L.get(j), axis) > sizeRelToAxis(L.get(j + 1), axis)) {
					// swap arr[j+1] and arr[j]
					Intersectable temp = L.get(j);
					L.set(j, L.get(j + 1));
					L.set(j + 1, temp);
				}
			}
	}

	/**
	 * returns the middle of the box relatively to the selected axis
	 * 
	 * @param inter an intersectable
	 * @param axis the longest axis
	 * @return the middle point of this axis of the intersectable's box
	 */
	private double sizeRelToAxis(Intersectable inter, char axis) {
		Box temp = inter.getBox();
		double sum = 0;
		switch (axis) {
		case 'x':
			sum = (temp.getX1() + temp.getX0())/2;
			break;
		case 'y':
			sum = (temp.getY1() + temp.getY0())/2;
			break;
		case 'z':
			sum = (temp.getZ1() + temp.getZ0())/2;
			break;
		}
		return sum;
	}

}
