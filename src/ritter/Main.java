package ritter;

import static utils.Utils.farther;
import static utils.Utils.isInSphere;
import static utils.Utils.midBetween;

import java.util.ArrayList;
import java.util.List;

import utils.Circle;
import utils.Point;

public class Main {

	/**
	 * Calcule une approximation du cercle de taille minimale recouvrant un ensemble de points
	 *
	 * @param points
	 * @return
	 */
	public static Circle ritter(List<Point> points) {
		Point dummy = points.get(0);
		Point P = farther(points, dummy);
		Point Q = farther(points, P);
		Point C = midBetween(P, Q);
		Circle CP = new Circle(C, C.distance(P));

		ArrayList<Point> outside_points = new ArrayList<Point>(points);

		while (outside_points.size() > 0) {
			// Retire les points qui sont dans le cercle
			for (Point current : points) {
				if (isInSphere(CP, current)) {
					outside_points.remove(current);
				}
			}
			points = new ArrayList<Point>(outside_points);

			// Calcule le nouveau centre
			if (outside_points.size() > 0) {
				Point S = outside_points.get(0);

				double CS = C.distance(S);
				double CPS = (CP.getRadius() + CS) / 2;
				double CCP = CS - CPS;

				C = new Point(((CPS / CS) * C.x + (CCP / CS) * S.x), ((CPS / CS) * C.y + (CCP / CS) * S.y));
				CP = new Circle(C, C.distance(S));
			}

		}

		return CP;
	}
}
