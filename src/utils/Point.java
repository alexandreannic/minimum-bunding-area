package utils;

import java.util.List;

/**
 * La classe java.awt.Point utilise des entiers pour représenter les coordonnées
 * des points. Cette classe a été crée afin de pouvoir exprimer les points par
 * des double.
 */
public class Point {

	public double x;
	public double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calcule la distance entre 2 points
	 *
	 * @param that
	 * @return
	 */
	public double distance(Point that) {
		return java.awt.Point.distance(x, y, that.x, that.y);
	}

	/**
	 * Retourne le plus à l'ouest
	 *
	 * @param points
	 * @return
	 */
	public static Point getMinX(List<Point> points) {
		Point minX = points.get(0);
		for (Point p : points) {
			if (p.x < minX.x || (p.x == minX.x && p.y < minX.y)) {
				minX = p;
			}
		}
		return minX;
	}

	/**
	 * Retourne le point le plus à l'est
	 *
	 * @param points
	 * @return
	 */
	public static Point getMaxX(List<Point> points) {
		Point maxX = points.get(0);
		for (Point p : points) {
			if (p.x > maxX.x || (p.x == maxX.x && p.y > maxX.y)) {
				maxX = p;
			}
		}
		return maxX;
	}

	/**
	 * Retourne le point le plus au nord
	 *
	 * @param points
	 * @return
	 */
	public static Point getMinY(List<Point> points) {
		Point minY = points.get(0);
		for (Point p : points) {
			if (p.y < minY.y || (p.y == minY.y && p.x > minY.x)) {
				minY = p;
			}
		}
		return minY;
	}

	/**
	 * Retourne le point le plus au sud
	 *
	 * @param points
	 * @return
	 */
	public static Point getMaxY(List<Point> points) {
		Point maxY = points.get(0);
		for (Point p : points) {
			if (p.y > maxY.y || (p.y == maxY.y && p.x < maxY.x)) {
				maxY = p;
			}
		}
		return maxY;
	}

	/**
	 * Retourne un point qui est à la fois le plus à l'est et le plus au nord
	 *
	 * @param points
	 * @return
	 */
	public static Point getMin(List<Point> points) {
		return new Point(getMinX(points).x, getMinY(points).y);
	}

	/**
	 * Retourne un point qui est à la fois le plus à l'ouest et le plus au sud
	 *
	 * @param points
	 * @return
	 */
	public static Point getMax(List<Point> points) {
		return new Point(getMaxX(points).x, getMaxY(points).y);
	}
}
