/**
 * 
 */
package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * The Intersectable interface represents a geometry object that can be
 * intersected by a ray.
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public abstract class Intersectable {
	/** a box- for BVH */
	protected Box box = null;

	/**
	 * C-TOR that gets a box
	 * 
	 * @param box a box
	 */
	public Intersectable(Box box) {
		this.box = box;
	}

	/**
	 * Class for representing a Box for BVH contains 6 double values of x,y,z
	 * minimum and maximum
	 * 
	 * @author Shilat Sharon and Lea Drach
	 *
	 */
	public static class Box {// for MP2
		/** x minimum */
		protected final double x0;
		/** x maximum */
		protected final double x1;
		/** y minimum */
		protected final double y0;
		/** y maximum */
		protected final double y1;
		/** z minimum */
		protected final double z0;
		/** z maximum */
		protected final double z1;

		/**
		 * Constructor, build a box around among of shapes
		 * 
		 * @param x0 x minimum
		 * @param x1 x max
		 * @param y0 y minimum
		 * @param y1 y max
		 * @param z0 z minimum
		 * @param z1 z max
		 */
		public Box(double x0, double x1, double y0, double y1, double z0, double z1) {
			super();
			this.x0 = x0;
			this.x1 = x1;
			this.y0 = y0;
			this.y1 = y1;
			this.z0 = z0;
			this.z1 = z1;
		}

		/**
		 * Get x0
		 * 
		 * @return x0 value
		 */
		public double getX0() {
			return x0;
		}

		/**
		 * Get x1
		 * 
		 * @return x1 value
		 */
		public double getX1() {
			return x1;
		}

		/**
		 * Get y0
		 * 
		 * @return y0 value
		 */
		public double getY0() {
			return y0;
		}

		/**
		 * Get y1
		 * 
		 * @return y1 value
		 */
		public double getY1() {
			return y1;
		}

		/**
		 * Get z0
		 * 
		 * @return z0 value
		 */
		public double getZ0() {
			return z0;
		}

		/**
		 * Get z1
		 * 
		 * @return z1 value
		 */
		public double getZ1() {
			return z1;
		}

		/**
		 * Returns true if the ray intersects the box
		 * 
		 * @param r ray
		 * @return True if the ray intersects the box. Otherwise, False
		 */
		public boolean IntersectionBox(Ray r) {

			double txMin = (x0 - r.getP0().getX()) / r.getDir().getX();
			double txMax = (x1 - r.getP0().getX()) / r.getDir().getX();
			if (txMin > txMax) {
				double temp = txMin;
				txMin = txMax;
				txMax = temp;
			}
			double tyMin = (y0 - r.getP0().getY()) / r.getDir().getY();
			double tyMax = (y1 - r.getP0().getY()) / r.getDir().getY();
			if (tyMin > tyMax) {
				double temp = tyMin;
				tyMin = tyMax;
				tyMax = temp;
			}
			if ((txMin > tyMax) || (tyMin > txMax))
				return false;
			if (tyMin > txMin)
				txMin = tyMin;
			if (tyMax < txMax)
				txMax = tyMax;
			double tzMin = (z0 - r.getP0().getZ()) / r.getDir().getZ();
			double tzMax = (z1 - r.getP0().getZ()) / r.getDir().getZ();
			if (tzMin > tzMax) {
				double temp = tzMin;
				tzMin = tzMax;
				tzMax = temp;
			}
			if ((txMin > tzMax) || (tzMin > txMax))
				return false;
			if (tzMin > txMin)
				txMin = tzMin;
			if (tzMax < txMax)
				txMax = tzMax;
			return true;
		}

	}

	/**
	 * Get box
	 * 
	 * @return the box
	 */
	public Box getBox() {
		return box;
	}

	/**
	 * Constructs an Intersectable object with a given box.
	 * 
	 * @param box the box of the Intersectable object
	 * @return The Intersectable object itself (for method chaining)
	 */
	public Intersectable setBox(Box box) {
		this.box = box;
		return this;
	}

	/**
	 * The GeoPoint class represents an intersection point between a ray and a
	 * geometry.
	 */
	public static class GeoPoint {
		/** The Geometry of the intersection point */
		public Geometry geometry;
		/** The Point of the intersection point */
		public Point point;

		/**
		 * Constructs a GeoPoint with the given geometry and a point.
		 *
		 * @param geometry the geometry object
		 * @param point    the point of intersection
		 */
		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			return obj instanceof GeoPoint other //
					&& this.geometry == other.geometry//
					&& this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "GeoPoint [geometry=" + geometry + ", point=" + point + "]";
		}

	}

	/**
	 * 
	 * Finds intersection points on the geometry with a given ray.
	 * 
	 * @param ray the ray to find intersection points with
	 * @return a List of intersection points on the geometry with the given ray, or
	 *         null if there are no intersections
	 */
	public List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * Finds the GeoPoint intersections on the geometry with a given ray.
	 *
	 * @param ray the ray to find GeoPoint intersections with
	 * @return a List of GeoPoints representing the intersections, or null if there
	 *         are no intersections
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray);
	}

	/**
	 * Helper method to be implemented by subclasses to find the GeoPoint
	 * intersections with a given ray.
	 *
	 * @param ray the ray to find GeoPoint intersections with
	 * @return a List of GeoPoints representing the intersections, or null if there
	 *         are no intersections
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
}
