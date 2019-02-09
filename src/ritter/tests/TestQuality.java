package ritter.tests;

import static utils.Utils.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ritter.Main;
import utils.Circle;
import utils.Point;

public class TestQuality {

	/**
	 * La qualité en tant que conteneur du cercle est le ratio de son air face à
	 * l'air du polygone qu'il enveloppe. La variable qualityContainerSum
	 * contient la somme des résultats de cette formule. Elle sera divisée par
	 * instanceCount pour donner le taux moyen.
	 */
	static double qualityContainerSum = 0;
	static double qualityContainerMax = 0;
	static double qualityContainerMin = 200;

	/**
	 * L'algorithme ritter est approximatif. La qualité en tant que cerlce
	 * représente le ratio du périmètre du cercle calculé par l'algoirhtme
	 * Ritter face au périmètre du cercle de taille minimal.
	 */
	static double qualityCircleSum = 0;
	static double qualityCircleMax = 0;
	static double qualityCircleMin = 200;

	/**
	 * Nombre total d'instance de tests
	 */
	static int instanceCount = 0;

	TestQuality() {
		List<Point> points;

		Pattern pattern = Pattern.compile("^(\\d+)\\s(\\d+)$");
		Matcher matcher;
		File dir = new File("samples/sep");
		String files[] = dir.list();

		for (int i = 0; i < files.length; i++) {
			points = new ArrayList<Point>();
			try {
				InputStream ips = new FileInputStream("samples/sep/" + files[i]);
				InputStreamReader ipsr = new InputStreamReader(ips);
				BufferedReader br = new BufferedReader(ipsr);
				String line;

				while ((line = br.readLine()) != null) {
					matcher = pattern.matcher(line);
					matcher.find();

					double x = Double.parseDouble(matcher.group(1));
					double y = Double.parseDouble(matcher.group(2));

					points.add(new Point(x, y));
				}
				runInstance(points);
				br.close();
			} catch (IOException e) {
				System.out.println(e.toString());
			}
			instanceCount++;
		}
	}

	public void runInstance(List<Point> points) {
		List<Point> convexHull = convexHull(points);
		Circle c = Main.ritter(points);
		double diameterReal = diameter(points);
		double diameterApproximate = c.radius * 2;
		double areaPolygon = getAreaPolygon(convexHull);
		double areaRitter = c.radius * c.radius * Math.PI;

		// En tant que conteneur
		double qualityContainer = areaRitter / areaPolygon * 100 - 100;
		qualityContainerSum += qualityContainer;
		qualityContainerMax = (qualityContainer > qualityContainerMax) ? qualityContainer : qualityContainerMax;
		qualityContainerMin = (qualityContainer < qualityContainerMin) ? qualityContainer : qualityContainerMin;

		// Par rapport au diamètre
		double qualityCircle = diameterApproximate / diameterReal * 100 - 100;
		qualityCircleSum += qualityCircle;
		qualityCircleMax = (qualityCircle > qualityCircleMax) ? qualityCircle : qualityCircleMax;
		qualityCircleMin = (qualityCircle < qualityCircleMin) ? qualityCircle : qualityCircleMin;
	}

	public static void main(String[] args) {
		TestQuality test = new TestQuality();
		System.out.println("Qualité en tant que conteneur :\n\t" + qualityContainerMin + " " + qualityContainerSum
						/ instanceCount + " " + qualityContainerMax);
		System.out.println("Qualité par rapport au diamètre :\n\t" + qualityCircleMin + " " + qualityCircleSum
						/ instanceCount + " " + qualityCircleMax);

	}
}
