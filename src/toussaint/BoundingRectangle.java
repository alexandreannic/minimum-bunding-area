package toussaint;

import java.util.ArrayList;
import java.util.List;

import utils.Point;

/**
 * Cette classe représente un rectangle contenant tous les points de l'enveloppe
 * convexe.
 */
public class BoundingRectangle {

	List<Point> convexHull;
	Line a;
	Line b;
	Line c;
	Line d;

	BoundingRectangle(List<Point> convexHull) {
		this.convexHull = convexHull;

		Point right = Point.getMaxX(convexHull);
		Point bottom = Point.getMaxY(convexHull);
		Point left = Point.getMinX(convexHull);
		Point top = Point.getMinY(convexHull);

		a = new Line(convexHull, right, 90);
		b = new Line(convexHull, bottom, 180);
		c = new Line(convexHull, left, 270);
		d = new Line(convexHull, top, 0);
	}

	/**
	 * Retourne les points formant le rectangle. Ces derniers sont les points
	 * d'intersections entre les différentes droites.
	 *
	 * @return
	 */
	List<Point> getRectanglePoints() {
		List<Point> points = new ArrayList<Point>();

		points.add(d.getIntersection(a));
		points.add(a.getIntersection(b));
		points.add(b.getIntersection(c));
		points.add(c.getIntersection(d));

		return points;
	}

	/**
	 * Retourne l'air du rectangle en utilisant Pythagore.
	 *
	 * @return
	 */
	double getArea() {
		List<Point> points = getRectanglePoints();

		double dXAB = points.get(0).x - points.get(1).x;
		double dYAB = points.get(0).y - points.get(1).y;
		double dXBC = points.get(1).x - points.get(2).x;
		double dYBC = points.get(1).y - points.get(2).y;

		double lengthAB = Math.sqrt((dXAB * dXAB) + (dYAB * dYAB));
		double lengthBC = Math.sqrt((dXBC * dXBC) + (dYBC * dYBC));

		return lengthAB * lengthBC;
	}

	/**
	 * Retourne l'angle minimum permettant d'effectuer une rotation sur un
	 * nouveau cété de l'enveloppe convexe
	 *
	 * @return
	 */
	double getSmallestAngle() {
		double smallestAngle = a.getAngleToNextPoint();
		if (b.getAngleToNextPoint() < smallestAngle)
			smallestAngle = b.getAngleToNextPoint();
		if (c.getAngleToNextPoint() < smallestAngle)
			smallestAngle = c.getAngleToNextPoint();
		if (d.getAngleToNextPoint() < smallestAngle)
			smallestAngle = d.getAngleToNextPoint();

		return smallestAngle;
	}

	/**
	 * Effectue une rotation sur tous les angles des droites du rectangle.
	 *
	 * @param angle
	 */
	void rotateBy(double angle) {
		a.rotateBy(angle);
		b.rotateBy(angle);
		c.rotateBy(angle);
		d.rotateBy(angle);
	}

	/**
	 * Retourne la rotation actuellement effectuée par le rectangle. Elle est
	 * déterminée en retournant la rotation effectuée par la droite initialisée
	 * à 0.
	 *
	 * @return
	 */
	double currentRotation() {
		return d.angle;
	}
}
