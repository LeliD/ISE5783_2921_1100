package unittests.renderer;

import java.util.Random;

import org.junit.Test;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Tests for BVH improvement
 * 
 * @author Shilat and Leli
 */
public class MiniProject2Tests {
	/**
	 * Produce the picture by boxing manually
	 * 
	 */
	@Test
	public void miniPro2part1() {
		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(10, -10, -1000), new Vector(0, 0, 50), new Vector(50, -100, 0))
				.setVPDistance(600).setVPSize(200, 200);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		Geometry a = new Sphere(new Point(60, -50, 50), 30).setEmission(new Color(BLUE))
				.setMaterial(new Material().setKd(0.2).setKs(0.2).setKt(0.6).setKr(0).setShininess(30));
		Geometry b = new Sphere(new Point(0, 0, 115), 30).setEmission(new Color(MAGENTA))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70));
		Geometry c = new Sphere(new Point(0, -100, 70), 15).setEmission(new Color(ORANGE))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.5).setKr(0).setShininess(50));
		Geometry e = new Sphere(new Point(50, -80, 90), 15).setEmission(new Color(GREEN))
				.setMaterial(new Material().setKd(0.5).setKs(0).setKt(0.5).setKr(0).setShininess(100));
		Geometry g = new Sphere(new Point(-50, -10, 80), 20).setEmission(new Color(CYAN))
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));
		Geometry d = new Triangle(new Point(-150, 150, 115), new Point(150, 150, 135), new Point(75, -75, 150))
				.setEmission(Color.BLACK).setMaterial(new Material().setGd(1).setKs(1).setShininess(60));
		Geometry f = new Triangle(new Point(-150, 150, 115), new Point(-70, -70, 140), new Point(75, -75, 150))
				.setEmission(Color.BLACK)
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0).setKr(0.25).setShininess(50));
		Geometry h = new Sphere(new Point(20, -60, 10), 15).setEmission(new Color(DARK_GRAY))
				.setMaterial(new Material().setKd(0.2).setKs(0.5).setKt(0.6).setKr(0).setShininess(30));
		Geometries geos1 = new Geometries(a, b, c);
		Geometries geos2 = new Geometries(d, e, g);
		Geometries geos3 = new Geometries(geos2, f, geos1, h);
		Geometries geos = new Geometries(geos2, geos3, geos1);
		scene.geometries.add(geos);
		scene.lights.add(new SpotLight(new Color(WHITE), new Point(70, -70, -60), new Vector(-1, 2, 3)).setKc(1)
				.setKl(0.000005).setKq(0.00000005));
		scene.lights.add(new DirectionalLight(new Color(BLACK), new Vector(-10, 20, 30)));
		scene.lights.add(new PointLight(new Color(BLUE), new Point(-30, 70, 140)).setKc(2.5).setKl(0.0000005)
				.setKq(0.0000000005));
		scene.lights.add(new PointLight(new Color(YELLOW), new Point(-50, 50, 100)).setKc(2.5).setKl(0.0000005)
				.setKq(0.0000000005));
		RayTracerBasic rayTracer = new RayTracerBasic(scene);
		rayTracer.setDistanceGrid(1000);
		camera.setImageWriter(new ImageWriter("miniPro2part1", 2000, 2000)) //
				.setRayTracer(rayTracer)//
				.renderImage() //
				.writeToImage();

	}

	/**
	 * Produce the picture without any improvement
	 */
	@Test
	public void miniPro2part2() {
		long startTime = System.currentTimeMillis();

		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(10, -10, -1000), new Vector(0, 0, 50), new Vector(50, -100, 0))
				.setVPDistance(600).setVPSize(200, 200);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		// Create 10 special spheres and triangles
		scene.geometries.add(
				new Sphere(new Point(60, -50, 50), 30).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setKt(0.6).setKr(0).setShininess(30)),
				new Sphere(new Point(0, 0, 115), 30).setEmission(new Color(MAGENTA))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)),
				new Sphere(new Point(0, -100, 70), 15).setEmission(new Color(ORANGE))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.5).setKr(0).setShininess(50)),
				new Sphere(new Point(50, -80, 90), 15).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.5).setKs(0).setKt(0.5).setKr(0).setShininess(100)),
				new Sphere(new Point(-50, -10, 80), 20).setEmission(new Color(CYAN))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
				new Triangle(new Point(-150, 150, 115), new Point(150, 150, 135), new Point(75, -75, 150))
						.setEmission(Color.BLACK).setMaterial(new Material().setKd(1).setKs(1).setShininess(60)),
				new Triangle(new Point(-150, 150, 115), new Point(-70, -70, 140), new Point(75, -75, 150))
						.setEmission(Color.BLACK)
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0).setKr(0.25).setShininess(50)),
				new Sphere(new Point(20, -60, 10), 15).setEmission(new Color(DARK_GRAY))
						.setMaterial(new Material().setKd(0.2).setKs(0.5).setKt(0.6).setKr(0).setShininess(30)),
				new Sphere(new Point(0, 60, 115), 10).setEmission(new Color(GRAY))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)),
				new Sphere(new Point(20, 60, 115), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)));

		// Create 25 more spheres
		Random r = new Random();
		Color Co = Color.BLACK;
		for (int i = 0; i < 250; i++) {
			if (i % 2 == 0)
				Co = new Color(CYAN);
			if (i % 3 == 0)
				Co = new Color(RED);
			if (i % 5 == 0)
				Co = new Color(MAGENTA);
			if (i % 6 == 0)
				Co = new Color(BLUE);
			if (i % 7 == 0)
				Co = new Color(GREEN);
			if (i % 9 == 0)
				Co = new Color(ORANGE);
			scene.geometries.add(new Sphere(new Point(r.nextInt(160) - 50, r.nextInt(200) - 80, r.nextInt(40) - 30),
					r.nextInt(10) + 5).setEmission(Co)
					.setMaterial(new Material().setKd(0.5).setKs(0.4).setShininess(70)));
		}
		// scene.setCBR();
		scene.lights.add(new SpotLight(new Color(WHITE), new Point(70, -70, -60), new Vector(-1, 2, 3)).setKc(1)
				.setKl(0.000005).setKq(0.00000005));
		scene.lights.add(new DirectionalLight(new Color(BLACK), new Vector(-10, 20, 30)));

		scene.lights.add(new PointLight(new Color(BLUE), new Point(-30, 70, 140)).setKc(2.5).setKl(0.0000005)
				.setKq(0.0000000005));
		scene.lights.add(new PointLight(new Color(YELLOW), new Point(-50, 50, 100)).setKc(2.5).setKl(0.0000005)
				.setKq(0.0000000005));

		RayTracerBasic rayTracer = new RayTracerBasic(scene);
		rayTracer.setDistanceGrid(1000);
		camera.setImageWriter(new ImageWriter("miniPro2part2", 2000, 2000)) //
				.setRayTracer(rayTracer)//
				.renderImage() //
				.writeToImage();

		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;

		System.out.println("Execution time without any improvement: " + executionTime + " milliseconds");

	}

	/**
	 * Produce the picture with CBR improvement
	 */
	@Test
	public void miniPro2part3() {
		long startTime = System.currentTimeMillis();

		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(10, -10, -1000), new Vector(0, 0, 50), new Vector(50, -100, 0))
				.setVPDistance(600).setVPSize(200, 200);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		// Create 10 special spheres and triangles
		scene.geometries.add(
				new Sphere(new Point(60, -50, 50), 30).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setKt(0.6).setKr(0).setShininess(30)),
				new Sphere(new Point(0, 0, 115), 30).setEmission(new Color(MAGENTA))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)),
				new Sphere(new Point(0, -100, 70), 15).setEmission(new Color(ORANGE))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.5).setKr(0).setShininess(50)),
				new Sphere(new Point(50, -80, 90), 15).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.5).setKs(0).setKt(0.5).setKr(0).setShininess(100)),
				new Sphere(new Point(-50, -10, 80), 20).setEmission(new Color(CYAN))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
				new Triangle(new Point(-150, 150, 115), new Point(150, 150, 135), new Point(75, -75, 150))
						.setEmission(Color.BLACK).setMaterial(new Material().setKd(1).setKs(1).setShininess(60)),
				new Triangle(new Point(-150, 150, 115), new Point(-70, -70, 140), new Point(75, -75, 150))
						.setEmission(Color.BLACK)
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0).setKr(0.25).setShininess(50)),
				new Sphere(new Point(20, -60, 10), 15).setEmission(new Color(DARK_GRAY))
						.setMaterial(new Material().setKd(0.2).setKs(0.5).setKt(0.6).setKr(0).setShininess(30)),
				new Sphere(new Point(0, 60, 115), 10).setEmission(new Color(GRAY))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)),
				new Sphere(new Point(20, 60, 115), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)));

		// Create 25 more spheres
		Random r = new Random();
		Color Co = Color.BLACK;
		for (int i = 0; i < 250; i++) {
			if (i % 2 == 0)
				Co = new Color(CYAN);
			if (i % 3 == 0)
				Co = new Color(RED);
			if (i % 5 == 0)
				Co = new Color(MAGENTA);
			if (i % 6 == 0)
				Co = new Color(BLUE);
			if (i % 7 == 0)
				Co = new Color(GREEN);
			if (i % 9 == 0)
				Co = new Color(ORANGE);
			scene.geometries.add(new Sphere(new Point(r.nextInt(160) - 50, r.nextInt(200) - 80, r.nextInt(40) - 30),
					r.nextInt(10) + 5).setEmission(Co)
					.setMaterial(new Material().setKd(0.5).setKs(0.4).setShininess(70)));
		}
		scene.setCBR();
		scene.lights.add(new SpotLight(new Color(WHITE), new Point(70, -70, -60), new Vector(-1, 2, 3)).setKc(1)
				.setKl(0.000005).setKq(0.00000005));
		scene.lights.add(new DirectionalLight(new Color(BLACK), new Vector(-10, 20, 30)));

		scene.lights.add(new PointLight(new Color(BLUE), new Point(-30, 70, 140)).setKc(2.5).setKl(0.0000005)
				.setKq(0.0000000005));
		scene.lights.add(new PointLight(new Color(YELLOW), new Point(-50, 50, 100)).setKc(2.5).setKl(0.0000005)
				.setKq(0.0000000005));

		RayTracerBasic rayTracer = new RayTracerBasic(scene);
		rayTracer.setDistanceGrid(1000);
		camera.setImageWriter(new ImageWriter("miniPro2part3", 2000, 2000)) //
				.setRayTracer(rayTracer)//
				.renderImage() //
				.writeToImage();

		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;

		System.out.println("Execution time for CBR improvement: " + executionTime + " milliseconds");

	}

	/**
	 * Produce the picture with CBR + BVH improvement
	 */
	@Test
	public void MiniPro2part4() {
		long startTime = System.currentTimeMillis();

		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point(10, -10, -1000), new Vector(0, 0, 50), new Vector(50, -100, 0))
				.setVPDistance(600).setVPSize(200, 200);
		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
		// Create 10 special spheres and triangles
		scene.geometries.add(
				new Sphere(new Point(60, -50, 50), 30).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setKt(0.6).setKr(0).setShininess(30)),
				new Sphere(new Point(0, 0, 115), 30).setEmission(new Color(MAGENTA))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)),
				new Sphere(new Point(0, -100, 70), 15).setEmission(new Color(ORANGE))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.5).setKr(0).setShininess(50)),
				new Sphere(new Point(50, -80, 90), 15).setEmission(new Color(GREEN))
						.setMaterial(new Material().setKd(0.5).setKs(0).setKt(0.5).setKr(0).setShininess(100)),
				new Sphere(new Point(-50, -10, 80), 20).setEmission(new Color(CYAN))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
				new Triangle(new Point(-150, 150, 115), new Point(150, 150, 135), new Point(75, -75, 150))
						.setEmission(Color.BLACK).setMaterial(new Material().setKd(1).setKs(1).setShininess(60)),
				new Triangle(new Point(-150, 150, 115), new Point(-70, -70, 140), new Point(75, -75, 150))
						.setEmission(Color.BLACK)
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0).setKr(0.25).setShininess(50)),
				new Sphere(new Point(20, -60, 10), 15).setEmission(new Color(DARK_GRAY))
						.setMaterial(new Material().setKd(0.2).setKs(0.5).setKt(0.6).setKr(0).setShininess(30)),
				new Sphere(new Point(0, 60, 115), 10).setEmission(new Color(GRAY))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)),
				new Sphere(new Point(20, 60, 115), 10).setEmission(new Color(RED))
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(70)));

		// Create 25 more spheres
		Random r = new Random();
		Color Co = Color.BLACK;
		for (int i = 0; i < 250; i++) {
			if (i % 2 == 0)
				Co = new Color(CYAN);
			if (i % 3 == 0)
				Co = new Color(RED);
			if (i % 5 == 0)
				Co = new Color(MAGENTA);
			if (i % 6 == 0)
				Co = new Color(BLUE);
			if (i % 7 == 0)
				Co = new Color(GREEN);
			if (i % 9 == 0)
				Co = new Color(ORANGE);
			scene.geometries.add(new Sphere(new Point(r.nextInt(160) - 50, r.nextInt(200) - 80, r.nextInt(40) - 30),
					r.nextInt(10) + 5).setEmission(Co)
					.setMaterial(new Material().setKd(0.5).setKs(0.4).setShininess(70)));
		}
		scene.setCBR().setBVH();
		scene.lights.add(new SpotLight(new Color(WHITE), new Point(70, -70, -60), new Vector(-1, 2, 3)).setKc(1)
				.setKl(0.000005).setKq(0.00000005));
		scene.lights.add(new DirectionalLight(new Color(BLACK), new Vector(-10, 20, 30)));

		scene.lights.add(new PointLight(new Color(BLUE), new Point(-30, 70, 140)).setKc(2.5).setKl(0.0000005)
				.setKq(0.0000000005));
		scene.lights.add(new PointLight(new Color(YELLOW), new Point(-50, 50, 100)).setKc(2.5).setKl(0.0000005)
				.setKq(0.0000000005));

		RayTracerBasic rayTracer = new RayTracerBasic(scene);
		rayTracer.setDistanceGrid(1000);
		camera.setImageWriter(new ImageWriter("miniPro2part4", 2000, 2000)) //
				.setRayTracer(rayTracer)//
				.renderImage() //
				.writeToImage();

		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;

		System.out.println("Execution time for CBR + BVH improvement: " + executionTime + " milliseconds");
	}

}
