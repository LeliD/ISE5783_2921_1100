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
 * 
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
public abstract class Intersectable {
	/**
	 * The GeoPoint class represents an intersection point between a ray and a
	 * geometry.
	 */
	public static class GeoPoint {

		public Geometry geometry;
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
