package toussaint;

import static utils.Utils.convexHull;
import static utils.Utils.getAreaPolygon;

import java.util.ArrayList;
import java.util.List;

import utils.Point;

public class Main {

	/**
	 * @param points
	 * @return
	 */
	public static List<Point> toussaint(List<Point> points) {
		List<Point> convexHull = convexHull(points);
		BoundingRectangle br = new BoundingRectangle(convexHull);
		List<Point> minimum = br.getRectanglePoints();
		double area = br.getArea();

		while (br.currentRotation() < 90.0) {
			List<Point> rectangle = br.getRectanglePoints();

			if (br.getArea() < area) {
				minimum = rectangle;
				area = br.getArea();
			}
			double smallestTheta = br.getSmallestAngle();

			br.rotateBy(smallestTheta);
		}

		return minimum;
	}

	public static void main(String[] args) {
		int[] xs = { -300, 200, 100, -400 };
		int[] ys = { -150, 200, -100, 0 };

		List<Point> p = new ArrayList<Point>();
		for (int i = 0; i < xs.length; i++) {
			p.add(new Point(xs[i], ys[i]));
		}
		List<Point> convexHull = convexHull(p);

		// List<PointI> minimum = enveloppeConvexe(p);
		List<Point> minimum = toussaint(p);

		// for (PointI pa : minimum) {
		// System.out.println(pa.x + " , " + pa.y);
		// }
		System.out.println(getAreaPolygon(convexHull));

		System.out.println(getAreaPolygon(minimum));
		// for(PointI po : minimum) {
		// System.out.println("(" + po.x + ", " + po.y +")");
		// }
		// for(Point2D.Double corner : minimum) {
		// System.out.printf("corner[%d] (%.1f, %.1f)%n", number++, corner.x,
		// corner.y);
		// }
		//
		// System.out.printf("%narea: %.1f", RotatingCalipers.getArea(minimum));
	}

}