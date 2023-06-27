package unittests.renderer;

import static java.awt.Color.BLACK;
import static java.awt.Color.ORANGE;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Color.darkGray;
import static java.awt.Color.white;

import org.junit.jupiter.api.Test;

import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;
/**
 * Tests for glossy and diffusive
 * 
 * @author Shilat and Leli
 */
class MiniProject1Tests {

	private Scene scene = new Scene("Test scene");

	

	/*
	 * Performs transparency test with Glossy and Diffuse improvement. 
	 * (Mini project 1)
	 */
	@Test
	public void transparencyGD() {

		Camera camera = new Camera(new Point(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0))
				.setVPSize(2500, 2500).setVPDistance(9000);
		scene.setAmbientLight(new AmbientLight(new Color(BLACK), 0.1));

		scene.geometries.add(

				new Sphere(new Point(-1000, -50, 1600), 200).setEmission(new Color(0, 25, 51))
						.setMaterial(new Material().setKr(0.7).setKd(0.8).setShininess(200).setKs(0.8)),

				new Sphere(new Point(1000, -50, 1600), 200).setEmission(new Color(0, 25, 51))
						.setMaterial(new Material().setKr(0.7).setKd(0.8).setShininess(200).setKs(0.8)),

				new Sphere(new Point(0, -50, 1600), 200).setEmission(new Color(0, 25, 51))
						.setMaterial(new Material().setKr(0.7).setKd(0.8).setShininess(200).setKs(0.8)),
				new Polygon(new Point(-330, 185, -8200), new Point(-330, -20, -8200), new Point(-130, -20, -8200),
						new Point(-130, 185, -8200)).setEmission(new Color(WHITE).reduce(10))
						.setMaterial(new Material().setKt(1)),
				new Polygon(new Point(-130, 185, -8200), new Point(-130, -20, -8200), new Point(100, -20, -8200),
						new Point(100, 185, -8200)).setEmission(new Color(WHITE).reduce(10))
						.setMaterial(new Material().setKt(1).setGd(15)),
				new Polygon(new Point(100, 185, -8200), new Point(100, -20, -8200), new Point(390, -20, -8200),
						new Point(390, 185, -8200)).setEmission(new Color(WHITE).reduce(10))
						.setMaterial(new Material().setKt(1).setGd(30)),

				new Plane(new Point(1500, 1500, 0), new Point(-1500, -1500, 3850), new Point(-1500, 1500, 0))
						.setEmission(new Color(BLACK).reduce(5))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(2000)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(0, 300, -400), new Vector(-1, 1, 4))
				.setKl(0.00001).setKq(0.000005));
		scene.lights.add(new SpotLight(new Color(20, 40, 0), new Point(800, 100, -300), new Vector(-1, 1, 4))
				.setKl(0.00001).setKq(0.000005));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-800, 100, -300), new Vector(-1, 1, 4))
				.setKl(0.00001).setKq(0.000005));
		scene.lights.add(new DirectionalLight(new Color(darkGray), new Vector(-0.5, 0.5, 0)));

		RayTracerBasic rayTracer = new RayTracerBasic(scene);
		rayTracer.setDistanceGrid(5000);
		camera.setImageWriter(new ImageWriter("transparencyGD", 1000, 1000)) //
				.setRayTracer(rayTracer)//
				.renderImage() //
				.writeToImage();

	}

	/*
	 * Performs reflection test with Glossy and Diffuse improvement. 
	 * (Mini project 1)
	 */
	@Test
	public void reflectionGD() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);
		scene.background = new Color(30, 30, 220);
		scene.geometries.add(
				new Polygon(new Point(-40, 20, -390), new Point(140, 20, -390), new Point(100, -120, 410),
						new Point(-80, -120, 410)) // down square
						.setEmission(new Color(130, 160, 210)) //
						.setMaterial(new Material().setKr(0.1).setGd(40)),
				new Polygon(new Point(-140, 20, -400), new Point(140, 20, -400), new Point(100, -120, 400),
						new Point(-100, -120, 400)) // down square
						.setEmission(new Color(WHITE)) //
						.setMaterial(new Material()),

				new Sphere(new Point(10, -20, -60), 25) // body
						.setEmission(new Color(155, 182, 224))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Sphere(new Point(0, -6, -40), 3) // button
						.setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(-3, -18, -36), 3) // button
						.setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, -30, -40), 3) // button
						.setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(10, 15, -60), 15) // head
						.setEmission(new Color(155, 182, 224)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),

				new Sphere(new Point(12, 20, -35), 2.5) // right eye
						.setEmission(new Color(BLACK)), //
				new Sphere(new Point(0, 20, -32), 2.5) // left eye
						.setEmission(new Color(BLACK)), //

				new Triangle(new Point(-3, 12, -25), new Point(8, 12, -35), new Point(6, 16, -35)) //
						.setEmission(new Color(ORANGE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)));

		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 200), new Point(-250, 400, 500), new Vector(-1, -1, -2)) //
						.setKl(0.00001).setKq(0.000001));

		RayTracerBasic rayTracer = new RayTracerBasic(scene);
		rayTracer.setDistanceGrid(5000);
		camera.setImageWriter(new ImageWriter("reflectionGD", 1000, 1000)) //
				.setRayTracer(rayTracer)//
				.renderImage() //
				.writeToImage();
	}

}
