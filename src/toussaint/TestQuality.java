package toussaint.tests;

import static toussaint.Main.toussaint;
import static utils.Utils.convexHull;
import static utils.Utils.getAreaPolygon;

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

import utils.Point;

public class TestQuality {

	static double qualitySum = 0;
	static int instanceCount = 0;

	TestQuality() {
		List<Point> points;

		Pattern pattern = Pattern.compile("^(\\d+)\\s(\\d+)$");
		Matcher matcher;
		File dir = new File("samples");
		String files[] = dir.list();

		for (int i = 0; i < files.length; i++) {
			points = new ArrayList<Point>();
			try {
				InputStream ips = new FileInputStream("samples/" + files[i]);
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
		List<Point> rectangle = toussaint(points);

		double areaPolygon = getAreaPolygon(convexHull);
		double areaRectangle = getAreaPolygon(rectangle);
		double quality = areaRectangle / areaPolygon * 100;
		qualitySum += quality;

		System.out.println(areaPolygon + " ; " + areaRectangle + " => " + quality);
	}

	public static void main(String[] args) {
		System.out.println("DEBUT");
		TestQuality test = new TestQuality();
		System.out.println("FIN " + qualitySum / instanceCount);
	}
}
