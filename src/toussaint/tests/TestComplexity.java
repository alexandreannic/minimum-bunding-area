package toussaint.tests;

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

import toussaint.Main;
import utils.Point;

public class TestComplexity {

	TestComplexity() {
		List<Point> points = new ArrayList<Point>();
		Pattern pattern = Pattern.compile("^(\\d+)\\s(\\d+)$");
		Matcher matcher;
		File dir = new File("samples");
		String files[] = dir.list();

		// Rempli la hashmap
		for (int i = 0; i < files.length; i++) {
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
					if (points.size() % 20000 == 0) {
						runInstance(points);
					}
				}
				br.close();
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
	}

	void runInstance(List<Point> points) {
		int nbTests = 20;
		long avgTime = 0;
		for (int i = 0; i < nbTests; i++) {
			long startTime = System.currentTimeMillis();
			Main.toussaint(points);
			long endTime = System.currentTimeMillis();
			avgTime += (endTime - startTime) / nbTests;
		}
		System.out.println(points.size() + "\t " + avgTime);
	}

	public static void main(String[] args) {
		TestComplexity test = new TestComplexity();
	}
}
