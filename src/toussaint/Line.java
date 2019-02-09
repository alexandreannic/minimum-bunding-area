package toussaint;

import java.util.List;

import utils.Point;

/**
 * Cette classe représente une des 4 droites utilisées pour représenter le
 * 'BoundingRectangle'. On représente ici une droite par un point et un angle.
 */
public class Line {

	final static double LOWNUMBER = 0.00000000001;

	final List<Point> convexHull;
	Point point;
	double angle;

	Line(List<Point> convexHull, Point pointIndex, double currentAngle) {
		this.convexHull = convexHull;
		this.point = pointIndex;
		this.angle = currentAngle;
	}

	/**
	 * Retourne le point suivant dans la liste
	 *
	 * @param point
	 * @return
	 */
	Point getNextPoint(Point point) {
		int index = convexHull.indexOf(point);
		return convexHull.get((index + 1) % convexHull.size());
	}

	/**
	 * Calcule l'angle entre le point cible et son successeur
	 *
	 * @return
	 */
	double getAngleToNextPoint() {
		Point nextPoint = getNextPoint(point);

		double dX = nextPoint.x - point.x;
		double dY = nextPoint.y - point.y;
		double angleNextPoint = Math.atan2(dY, dX) * 180 / Math.PI;

		if (angleNextPoint < 0)
			angleNextPoint += 360;
		angleNextPoint -= angle;
		if (angleNextPoint < 0)
			angleNextPoint = 360;

		return angleNextPoint;
	}

	/**
	 * Calcule le point d'intersection entre la droite cible et celle passée en
	 * paramétre
	 *
	 * @param line
	 * @return
	 */
	Point getIntersection(Line line) {
		Point intersection;

		// Si vertical
		if ((Math.abs(angle - 90.0) < LOWNUMBER) || (Math.abs(angle - 270.0) < LOWNUMBER)) {
			intersection = new Point(point.x, line.getConstant());
		}
		// Si horizontal
		else if ((Math.abs(angle) < LOWNUMBER) || (Math.abs(angle - 180.0) < LOWNUMBER)) {
			intersection = new Point(line.point.x, this.getConstant());
		} else {
			double x = (line.getConstant() - this.getConstant()) / (this.getSlope() - line.getSlope());
			double y = (this.getSlope() * x) + this.getConstant();
			intersection = new Point(x, y);

		}

		return intersection;
	}

	/**
	 * Calcule l'ordonné à l'origine
	 *
	 * @return
	 */
	double getConstant() {
		return point.y - (getSlope() * point.x);
	}

	/**
	 * Calcule le coefficient directeur
	 *
	 * @return
	 */
	double getSlope() {
		return Math.tan(Math.toRadians(angle));
	}

	/**
	 * EFfectue une rotation de l'angle du point. Si l'angle entre le point
	 * cible et son successeur est le minimum de tous les angles réalisés par
	 * les autres objet Line du BoundingRectangle, alors le successeur devient
	 * le point cible.
	 *
	 * @param angle
	 */
	void rotateBy(double angle) {

		if (this.getAngleToNextPoint() == angle) {
			point = getNextPoint(point);
		}

		this.angle += angle;
	}
}