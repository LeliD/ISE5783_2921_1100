
package renderer;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.MissingResourceException;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * 
 * Camera class for representing a camera in a 3D space. It defines the position
 * of the camera
 * 
 * and the direction it faces, and is used to construct rays for rendering an
 * image.
 * 
 * @author Shilat Sharon and Lea Drach
 */
public class Camera {

	/** The position of the camera. */
	private Point p0;
	/** The up direction vector of the camera. */
	private Vector vUp;
	/** The forward direction vector of the camera. */
	private Vector vTo;
	/** The right direction vector of the camera. */
	private Vector vRight;
	/** The width of the view plane. */
	private double width;
	/** The height of the view plane. */
	private double height;
	/** The distance from the camera to the view plane. */
	private double distance;
	/** The image writer */
	private ImageWriter imageWriter;
	/** The ray tracer */
	private RayTracerBase rayTracer;

	/**
	 * 
	 * Constructs a new Camera object with the given position, forward direction
	 * vector, and up direction vector. The right direction vector is computed
	 * automatically.
	 * 
	 * @param p0  The position of the camera.
	 * @param vTo The forward direction vector of the camera.
	 * @param vUp The up direction vector of the camera.
	 * @throws IllegalArgumentException if the up direction vector and the forward
	 *                                  direction vector are not orthogonal.
	 */
	public Camera(Point p0, Vector vTo, Vector vUp) {
		if (!isZero(vUp.dotProduct(vTo)))
			throw new IllegalArgumentException("Error: vUp and vTo are not orthogonal");

		this.p0 = p0;
		this.vUp = vUp.normalize();
		this.vTo = vTo.normalize();
		this.vRight = this.vTo.crossProduct(this.vUp);
	}

	/**
	 * 
	 * Returns the position of the camera.
	 * 
	 * @return The position of the camera.
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * 
	 * Returns the up direction vector of the camera.
	 * 
	 * @return The up direction vector of the camera.
	 */
	public Vector getvUp() {
		return vUp;
	}

	/**
	 * 
	 * Returns the forward direction vector of the camera.
	 * 
	 * @return The forward direction vector of the camera.
	 */
	public Vector getvTo() {
		return vTo;
	}

	/**
	 * 
	 * Returns the right direction vector of the camera.
	 * 
	 * @return The right direction vector of the camera.
	 */
	public Vector getvRight() {
		return vRight;
	}

	/**
	 * 
	 * Returns the width of the view plane.
	 * 
	 * @return The width of the view plane.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * 
	 * Returns the height of the view plane.
	 * 
	 * @return The height of the view plane.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * 
	 * Returns the distance from the camera to the view plane.
	 * 
	 * @return The distance from the camera to the view plane.
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * 
	 * Sets the size of the view plane to the given width and height.
	 * 
	 * @param width  The width of the view plane.
	 * @param height The height of the view plane.
	 * @return This Camera object.
	 * @throws IllegalArgumentException if width or height are 0 or negative
	 */
	public Camera setVPSize(double width, double height) {
		if (alignZero(width) <= 0 || alignZero(height) <= 0)
			throw new IllegalArgumentException(
					"The view-plane's fields width and height must get a positive number value");
		this.width = width;
		this.height = height;
		return this;
	}

	/**
	 * 
	 * Sets the distance from the camera to the view plane.
	 * 
	 * @param distance the distance from the camera to the view plane
	 * @return the camera object
	 * @throws IllegalArgumentException if the distance is 0 or negative
	 */
	public Camera setVPDistance(double distance) {
		if (alignZero(distance) <= 0)
			throw new IllegalArgumentException("The view-plane's field distance must get a positive number value");
		this.distance = distance;
		return this;
	}

	/***
	 * Sets the RayTracerBase object to be used for rendering images with the
	 * camera.
	 * 
	 * @param rayTracer The RayTracerBase object to be set
	 * @return This Camera object
	 */
	public Camera setRayTracer(RayTracerBase rayTracer) {
		this.rayTracer = rayTracer;
		return this;
	}

	/***
	 * Sets the ImageWriter object to be used for writing rendered images.
	 * 
	 * @param imageWriter The ImageWriter object to be set.
	 * @return This Camera object.
	 */
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * 
	 * Constructs a ray through a specified pixel on the view plane.
	 * 
	 * @param nX the number of pixels in the x direction
	 * @param nY the number of pixels in the y direction
	 * @param j  the x coordinate of the pixel
	 * @param i  the y coordinate of the pixel
	 * @return the ray passing through the specified pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		Point pc = p0.add(vTo.scale(distance));

		double rY = height / nY;// height of each pixel
		double rX = width / nX;// width of each pixel
		// the middle of the pixel (xj,yi)
		double yi = (i - (nY - 1) / 2d) * rY;
		double xj = (j - (nX - 1) / 2d) * rX;

		Point pij = pc;

		if (alignZero(xj) != 0)
			pij = pij.add(vRight.scale(xj));

		if (alignZero(yi) != 0)
			pij = pij.add(vUp.scale(-yi));

		return new Ray(p0, pij.subtract(p0));

	}

	/**
	 * Renders the image by casting rays and writing pixels to the image writer.
	 * 
	 * @throws MissingResourceException if any required field is null
	 */
	public void renderImage() {
		if (p0 == null || vUp == null || vTo == null || vRight == null)
			throw new MissingResourceException("All the render's fields mustn't be null", "Camera", null);
		if (imageWriter == null)
			throw new MissingResourceException("All the render's fields mustn't be null, including the imageWriter",
					"Camera", null);
		if (rayTracer == null)
			throw new MissingResourceException("All the render's fields mustn't be null, including the rayTracer",
					"Camera", null);
		// throw new UnsupportedOperationException();
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
		for (int i = 0; i < nY; i++)
			for (int j = 0; j < nX; j++)
				imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
	}

	/**
	 * 
	 * Casts a ray and returns the color of the traced ray.
	 * 
	 * @param nX the number of pixels in the x direction
	 * @param nY the number of pixels in the y direction
	 * @param j  The horizontal position of the pixel.
	 * @param i  The vertical position of the pixel.
	 * @return The color of the traced ray.
	 */
	private Color castRay(int nX, int nY, int j, int i) {
		Ray ray = constructRay(nX, nY, j, i);
		return this.rayTracer.traceRay(ray);
	}

	/**
	 * 
	 * Prints a grid on the image with the given interval and color.
	 * 
	 * @param interval The interval between grid lines.
	 * @param color    The color of the grid lines.
	 * @return This Camera object.
	 * @throws MissingResourceException if the imageWriter field is null.
	 * @throws IllegalArgumentException if the interval is not a divisor of both nX
	 *                                  and nY.
	 */
	public Camera printGrid(int interval, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException("The render's field imageWriter mustn't be null", "Camera", null);

		if (imageWriter.getNx() % interval != 0 || imageWriter.getNy() % interval != 0)
			throw new IllegalArgumentException(
					"The grid is supposed to have squares, therefore the given interval must be a divisor of both nX and nY");
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		for (int i = 0; i < nY; i++)
			for (int j = 0; j < nX; j++)
				if (i % interval == 0 || j % interval == 0)
					imageWriter.writePixel(j, i, color);
		return this;
	}

	/**
	 * 
	 * Writes the image to the image writer.
	 * 
	 * @return The Camera object.
	 * @throws MissingResourceException if the imageWriter field is null.
	 */
	public Camera writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException("The render's field imageWriter mustn't be null", "Camera", null);
		imageWriter.writeToImage();
		return this;
	}
}