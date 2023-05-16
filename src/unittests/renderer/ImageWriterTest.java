/**
 * 
 */
package unittests.renderer;

import org.junit.jupiter.api.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * Unit tests for renderer.ImageWriter class
 * 
 * @author Shilat Sharon and Lea Drach
 *
 */
class ImageWriterTest {

	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}.
	 */
	@Test
	void testWriteToImage() {
		// TC01: Constructing a experimental picture of a grid
		// the resolution of the image
		int nX = 801;
		int nY = 501;
		int interval = 50;
		// Create an instance of ImageWriter
		ImageWriter imageWriter = new ImageWriter("image1", nX, nY);

		// Define colors
		Color b = new Color(java.awt.Color.BLUE); // Blue color - the background color
		Color r = new Color(java.awt.Color.RED); // Red color - the grid's color

		// Loop through the image pixels
		for (int i = 0; i < nY; i++)
			for (int j = 0; j < nX; j++)
				imageWriter.writePixel(j, i, i % interval == 0 || j % interval == 0 ? r : b);// the grid

		imageWriter.writeToImage();
	}

}
