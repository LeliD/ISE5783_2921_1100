package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.isZero;

/**
 * Triangle class for representing a Triangle, extends Polygon
 * 
 * @author Shilat Sharon and Leli Drach
 *
 */
public class Triangle extends Polygon {
	/**
	 * Constructor to initialize a Triangle
	 * 
	 * @param p1 first point in the Triangle
	 * @param p2 second point in the Triangle
	 * @param p3 third point in the Triangle
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}
	/**
	 * Finding intersection points on the geometry with a given ray
	 * @param ray - the ray to find intersection points with
	 * @return List of intersection-points on the geometry with the given ray
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersections = plane.findIntersections(ray);
	    if (intersections == null)//if the ray doesn't cross the plane of the triangle
	    	return null;
	    Point p0 = ray.getP0();
	    Vector v = ray.getDir();
	    Vector v1 = vertices.get(0).subtract(p0);//p1-p0
	    Vector v2 =vertices.get(1).subtract(p0);//p2-p0
	    Vector v3 = vertices.get(2).subtract(p0);//p3-p0
	    
	    Vector n1=v1.crossProduct(v2);//normal to the plane of v1,v2
	    double s1 = v.dotProduct(n1);
	    if (isZero(s1)) //if v contain in plane of v1, v2 ,there aren't Intsersections points 
	    	return null;
	    
	    Vector n2=v2.crossProduct(v3);//normal to the plane of v2,v3
	    double s2 = v.dotProduct(n2);
	    if (isZero(s2))//if v contain in plane of v2, v3 ,there aren't Intsersections points 
	    	return null;
	    
	    Vector n3=v3.crossProduct(v1);//normal to the plane of v3,v1
	    double s3 = v.dotProduct(n3);
	    if (isZero(s3)) //if v contain in plane of v3, v1 ,there aren't Intsersections points 
	    	return null;
	    
	    //The point is inside if all v*ni have the same sign (+/-)
	    return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? intersections : null;
	}
}
