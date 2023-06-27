/**
 * 
 */
package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
	/** Sets CBR improvement */
	protected static boolean cbr = false;

	/**
	 * Set CBR improvement
	 * 
	 */
	public static void createCBR() {
		Intersectable.cbr = true;
	}

	/**
	 * Class for representing a Box for BVH contains 6 double values of x,y,z
	 * minimum and maximum
	 * 
	 * @author Shilat Sharon and Lea Drach
	 *
	 */
	public static class Box {
		/** x minimum */
		protected double x0;
		/** x maximum */
		protected double x1;
		/** y minimum */
		protected double y0;
		/** y maximum */
		protected double y1;
		/** z minimum */
		protected double z0;
		/** z maximum */
		protected double z1;

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
			this.x0 = x0;
			this.x1 = x1;
			this.y0 = y0;
			this.y1 = y1;
			this.z0 = z0;
			this.z1 = z1;
		}

		/**
		 * Empty Constructor, builds an infinity box
		 * 
		 */
		public Box() {
			this.x0 = Double.POSITIVE_INFINITY;
			this.x1 = Double.NEGATIVE_INFINITY;
			this.y0 = Double.POSITIVE_INFINITY;
			this.y1 = Double.NEGATIVE_INFINITY;
			this.z0 = Double.POSITIVE_INFINITY;
			this.z1 = Double.NEGATIVE_INFINITY;
		}

		/**
		 * Returns true if the ray intersects the box
		 * 
		 * @param r ray
		 * @return True if the ray intersects the box. Otherwise, False
		 */
		public boolean IntersectionBox(Ray r) {

			Point origin = r.getP0();
			double originX = origin.getX();
			double originY = origin.getY();
			double originZ = origin.getZ();
			Vector dir = r.getDir();
			double dirX = dir.getX();
			double dirY = dir.getY();
			double dirZ = dir.getZ();

			// Initially will receive the values of tMinX and tMaxX
			double tMin = Double.NEGATIVE_INFINITY;
			double tMax = Double.POSITIVE_INFINITY;

			// the values are depend on the direction of the ray

			if (dirX > 0) {
				tMin = (x0 - originX) / dirX; // b=D*t+O => y=mx+b =>dirx*tmin+originx=minx
				tMax = (x1 - originX) / dirX;
			} else if (dirX < 0) {
				tMin = (x1 - originX) / dirX;
				tMax = (x0 - originX) / dirX;
			}

			double tMinY = Double.NEGATIVE_INFINITY;
			double tMaxY = Double.POSITIVE_INFINITY;
			if (dirY > 0) {
				tMinY = (y0 - originY) / dirY;
				tMaxY = (y1 - originY) / dirY;
			} else if (dirY < 0) {
				tMinY = (y1 - originY) / dirY;
				tMaxY = (y0 - originY) / dirY;
			}

			// If either the max value of Y is smaller than overall min value, or min value
			// of Y is bigger than the overall
			// max, we can already return false.
			// Otherwise we'll update the overall min and max values and perform the same
			// check on the Z values.
			if ((tMin > tMaxY) || (tMinY > tMax))
				return false;

			if (tMinY > tMin)
				tMin = tMinY;
			if (tMaxY < tMax)
				tMax = tMaxY;

			double tMinZ = Double.NEGATIVE_INFINITY;
			double tMaxZ = Double.POSITIVE_INFINITY;
			if (dirZ > 0) {
				tMinZ = (z0 - originZ) / dirZ;
				tMaxZ = (z1 - originZ) / dirZ;
			} else if (dirZ < 0) {
				tMinZ = (z1 - originZ) / dirZ;
				tMaxZ = (z0 - originZ) / dirZ;
			}

			// If either the max value of Z is smaller than overall min value, or min value
			// of Z is bigger than the overall
			// max, we can already return false. Otherwise we can return true since no other
			// coordinate checks are needed.
			return tMin <= tMaxZ && tMinZ <= tMax;
		}

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
		return box != null && !box.IntersectionBox(ray) ? null : findGeoIntersectionsHelper(ray);
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
