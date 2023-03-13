package primitives;

public class Point 
{
	final Double3 xyz;
	  /** Constructor to initialize Double3 based object with its three number values
	    * @param d1 first number value
	    * @param d2 second number value
	    * @param d3 third number value */
	public Point(double d1, double d2, double d3) 
	{
	      xyz= new Double3(d1,d2,d3);
	}
	Point(Double3 obj) 
	{
		   xyz= new Double3(obj.d1,obj.d2,obj.d3);
	}
	
/**
 * subtract between two points and return vector
 * @param otherPoint the second point
 * @return new vector after subtract
 * @throws IllegalArgumentException
 */
public Vector subtract(Point otherPoint) 
{
	
xyz.subtract(new Double3(otherPoint.xyz.d1,otherPoint.xyz.d2,otherPoint.xyz.d3));
return new Vector(newX, newY, newZ);
}

	
}
