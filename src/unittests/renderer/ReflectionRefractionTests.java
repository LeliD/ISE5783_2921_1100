/**
 * 
 */
package unittests.renderer;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Double3;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */

	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
						.setMaterial(
								new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(new Double3(0.5, 0, 0))),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Performs the rendering of the "ProjectShilat&amp;Leli" scene of a snowman.
	 * The method sets up the camera, scene objects, lights, and renders the image
	 * using ray tracing. The rendered image is saved to a file.
	 */
	@Test
	public void projectShilatAndLeli() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);
		scene.background = new Color(30, 30, 220);
		scene.geometries.add( //
				new Sphere(new Point(10, 15, -60), 15) // head
						.setEmission(new Color(155, 182, 224)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Sphere(new Point(10, -20, -60), 25) // body
						.setEmission(new Color(155, 182, 224)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Sphere(new Point(12, 20, -35), 2.5) // right eye
						.setEmission(new Color(java.awt.Color.BLACK)), //
				// .setMaterial(new Material()),
				new Sphere(new Point(0, 20, -32), 2.5) // left eye
						.setEmission(new Color(java.awt.Color.BLACK)), //
				// .setMaterial(new
				// Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Polygon(new Point(-40, 20, -390), new Point(140, 20, -390), new Point(100, -120, 410),
						new Point(-80, -120, 410)) // down squere
						.setEmission(new Color(130, 160, 210)) //
						.setMaterial(new Material().setKr(0.1).setGd(0)),
				new Polygon(new Point(-140, 20, -400), new Point(140, 20, -400), new Point(100, -120, 400),
						new Point(-100, -120, 400)) // down squere
						.setEmission(new Color(java.awt.Color.WHITE)) //
						.setMaterial(new Material().setKr(0.1)),

				new Triangle(new Point(-3, 12, -25), new Point(8, 12, -35), new Point(6, 16, -35)) //
						.setEmission(new Color(java.awt.Color.ORANGE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				// left cloud
				new Sphere(new Point(-42, 50, -40), 5) // right cloud
						.setEmission(new Color(java.awt.Color.white)),
				new Sphere(new Point(-52, 50, -40), 5) // left cloud
						.setEmission(new Color(java.awt.Color.white)),
				new Sphere(new Point(-47, 52, -40), 7) // middle cloud
						.setEmission(new Color(java.awt.Color.white)),
				// middle cloud
				new Sphere(new Point(-5, 60, -40), 5) // right cloud
						.setEmission(new Color(java.awt.Color.white)),
				new Sphere(new Point(0, 62, -40), 7) // left cloud
						.setEmission(new Color(java.awt.Color.white)),
				new Sphere(new Point(5, 60, -40), 5) // middle cloud
						.setEmission(new Color(java.awt.Color.white)),
				// right cloud
				new Sphere(new Point(44, 55, -40), 5) // right cloud
						.setEmission(new Color(java.awt.Color.white)),
				new Sphere(new Point(54, 55, -40), 5) // left cloud
						.setEmission(new Color(java.awt.Color.white)),
				new Sphere(new Point(49, 57, -40), 7) // middle cloud
						.setEmission(new Color(java.awt.Color.white)),
				// buttons
				new Sphere(new Point(0, -6, -40), 3) // button
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-3, -18, -36), 3) // button
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, -30, -40), 3) // button
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				// snow
				new Sphere(new Point(-40, 29, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-42, 37, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-48, 38, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-52, 34, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-48, 23, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-43, 20, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-54, 18, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-60, 27, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(43, 26, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(38, 23, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(49, 21, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(55, 30, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-15, 25, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-23, 22, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),

				new Sphere(new Point(45, 38, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(42, 45, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(47, 43, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(52, 39, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				//
				new Sphere(new Point(50, 35, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, 40, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-1, 45, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-6, 37, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),

				new Sphere(new Point(32, 32, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-24, 33, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-16, 46, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-25, 40, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(10, 42, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(13, 40, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(8, 35, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(19, 40, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-30, 39, -20), 0.7) //
						.setEmission(new Color(java.awt.Color.white)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)));

		// scene.lights.add(new DirectionalLight(new Color(800, 500, 0), new Vector(-1,
		// -1, -2)));

		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 200), new Point(-250, 400, 500), new Vector(-1, -1, -2)) //
						.setKl(0.00001).setKq(0.000001));
		// scene.lights.add( //
		// new SpotLight(new Color(1000, 600, 200), new Point(-200, -300, 200), new
		// Vector(1, 1, -2)) //
		// .setKl(0.00001).setKq(0.000001));
		// scene.lights.add( //
		// new SpotLight(new Color(1000, 600, 200), new Point(0, -6, -35), new Vector(1,
		// 1, -2)) //
		// .setKl(0.00001).setKq(0.000001));

		camera.setImageWriter(new ImageWriter("ProjectShilat&Leli", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();

	}

	@Test
	public void projPicture1() throws Exception {

		Camera camera = new Camera(new Point(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0))
				.setVPSize(2500, 2500).setVPDistance(9000);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.BLACK), 0.1));

		scene.geometries.add(

				new Sphere(new Point(-1000, -50, 1600), 200).setEmission(new Color(0, 25, 51))
						.setMaterial(new Material().setKr(0.7).setKd(0.8).setShininess(200).setKs(0.8)),

				new Sphere(new Point(1000, -50, 1600), 200).setEmission(new Color(0, 25, 51))
						.setMaterial(new Material().setKr(0.7).setKd(0.8).setShininess(200).setKs(0.8)),

				new Sphere(new Point(0, -50, 1600), 200).setEmission(new Color(0, 25, 51))
						.setMaterial(new Material().setKr(0.7).setKd(0.8).setShininess(200).setKs(0.8)),
				new Polygon(new Point(-330, 185, -8200), new Point(-330, -20, -8200), new Point(-130, -20, -8200),new Point(-130, 185, -8200))
				        .setEmission(new Color(java.awt.Color.WHITE).reduce(10)).setMaterial(new Material().setKt(1)),
			    new Polygon(new Point(-130, 185, -8200), new Point(-130, -20, -8200), new Point(100, -20, -8200),new Point(100, 185, -8200))
			    .setEmission(new Color(java.awt.Color.WHITE).reduce(10))
				.setMaterial(new Material().setKt(1).setGd(15)),
				new Polygon(new Point(100, 185, -8200), new Point(100, -20, -8200), new Point(390, -20, -8200),new Point(390, 185, -8200))
				.setEmission(new Color(java.awt.Color.WHITE).reduce(10))
				.setMaterial(new Material().setKt(1).setGd(30)),
				
				/*new Triangle(new Point(40, -15, -8200), new Point(350, -15, -8200), new Point(350, 185, -8200))
						.setEmission(new Color(java.awt.Color.WHITE).reduce(10)).setMaterial(new Material().setKt(1)),

				new Triangle(new Point(40, -15, -8200), new Point(350, 185, -8200), new Point(-270, 185, -8200))
						.setEmission(new Color(java.awt.Color.WHITE).reduce(10))
						.setMaterial(new Material().setKt(1).setGd(15)),

				new Triangle(new Point(40, -15, -8200), new Point(-270, -15, -8200), new Point(-270, 185, -8200))
						.setEmission(new Color(java.awt.Color.WHITE).reduce(10))
						.setMaterial(new Material().setKt(1).setGd(30)),
*/
				new Plane(new Point(1500, 1500, 0), new Point(-1500, -1500, 3850), new Point(-1500, 1500, 0))
						.setEmission(new Color(java.awt.Color.BLACK).reduce(5))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(2000)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(0, 300, -400), new Vector(-1, 1, 4))
				.setKl(0.00001).setKq(0.000005));
		scene.lights.add(new SpotLight(new Color(20, 40, 0), new Point(800, 100, -300), new Vector(-1, 1, 4))
				.setKl(0.00001).setKq(0.000005));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-800, 100, -300), new Vector(-1, 1, 4))
				.setKl(0.00001).setKq(0.000005));
		scene.lights.add(new DirectionalLight(new Color(java.awt.Color.darkGray), new Vector(-0.5, 0.5, 0)));

		RayTracerBasic rayTracer = new RayTracerBasic(scene);
		rayTracer.setDistanceGrid(5000);
		camera.setImageWriter(new ImageWriter("Project1", 1000, 1000)) //
				.setRayTracer(rayTracer)//
				.renderImage() //
				.writeToImage();

	}

}
