/**
 * 
 */
package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;
import renderer.ImageWriter;
import primitives.Color;
import org.junit.jupiter.api.Test;

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
		// Create an instance of ImageWriter
		ImageWriter imageWriter = new ImageWriter("image1", 801, 501);
		// Get the width and height of the image
		int nX = imageWriter.getNx();// nX= how many columns
		int nY = imageWriter.getNy();// nY= how many rows
		// Define colors
		Color b = new Color(0, 0, 200); // Blue color -the background color
		Color r = new Color(200, 0, 0); // Red color  -the grid's color

		// Loop through the image pixels
		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				if (i % 50 == 0 || j % 50 == 0)
					imageWriter.writePixel(j, i, r);// the grid
				else
					imageWriter.writePixel(j, i, b);// the background

			}
		}
		imageWriter.writeToImage();
	}

}
