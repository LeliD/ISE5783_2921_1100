package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.isZero;

/**
 * Sphere class for representing a Sphere, extends RadialGeometry
 * 
 * @author Shilat Sharon and Leli Drach
 *
 */
public class Sphere extends RadialGeometry {
	/** center of a Sphere */
	private final Point center;

	/**
	 * Constructor to initialize a Sphere
	 * 
	 * @param p center of a Sphere
	 * @param r radius of a Sphere
	 */
	public Sphere(Point p, double r) {
		super(r);
		center = p;
	}

	/**
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	@Override
	public String toString() {
		return "Sphere [center=" + center + ", radius=" + radius + "]";
	}

	@Override
	public Vector getNormal(Point p) {
		return (p.subtract(center)).normalize();
	}
	/**
	 * Finding intersection points on the geometry with a given ray
	 * @param ray - the ray to find intersection points with
	 * @return List of intersection-points on the geometry with the given ray
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		//get ray point and vector
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        //vector between p0 start and sphere center-O
        Vector u = null;
        try 
        {
            u = center.subtract(p0);
        } 
        catch (Exception ex)//p0=center
        {
            //return p0 + r*v
            return List.of(p0.add(v.scale(radius)));
        }

        double tm = v.dotProduct(u);

        double lengthU = u.length();

        //get the distance between the ray and the sphere center
        double d = Math.sqrt(Math.abs((lengthU * lengthU)-(tm * tm)));

        //the ray doesn't cross the sphere
        if (d>radius)
            return null;

        //the ray tangent the sphere
        if (isZero(d- radius)) //if d=radius
        {                  
            return null;
        }
        //the ray crosses the sphere in two places
        double th = Math.sqrt(Math.abs((radius * radius)- d*d));
        //get the distance to the two points
        double t1 = tm- th;
        double t2 =tm+th;

        //return the points that are after the ray
        if ((t1 < 0 || isZero(t1)) && (t2 < 0|| isZero(t2))) 
        	return null;
        if (t1 > 0 && t2 > 0) 
        	return List.of(p0.add(v.scale(t1)),p0.add(v.scale(t2))); //P1 , P2
        if (t1 > 0)
            return List.of(p0.add(v.scale(t1)));
        else //t2 > 0
            return List.of(p0.add(v.scale(t2)));
	}
}
