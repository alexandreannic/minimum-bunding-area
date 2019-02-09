package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Contient divers fonctions utilitaires
 */
public class Utils {

	/**
	 * Calcule le produit vectoriel de 3 points.
	 *
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public static double produit_vect(Point a, Point b, Point c) {
		return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
	}

	/**
	 * Calcule le centre d'un segment représenté par deux points
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	public static Point midBetween(Point a, Point b) {
		return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
	}

	/**
	 * Calcule le point le plus éloigné
	 *
	 * @param points liste des points où chercher le plus éloigné
	 * @param point  point
	 * @return
	 */
	public static Point farther(List<Point> points, Point point) {
		Point farther = points.get(0);
		double max_distance = 0;

		for (Point current : points) {
			double current_distance = point.distance(current);
			if (current_distance > max_distance) {
				farther = current;
				max_distance = current_distance;
			}
		}

		return farther;
	}

	/**
	 * Vérifie qu'un point est contenu dans un cercle
	 *
	 * @param circle
	 * @param point
	 * @return
	 */
	public static boolean isInSphere(Circle circle, Point point) {
		return point.distance(circle.getCenter()) <= circle.getRadius();
	}

	/**
	 * Algorithme quadratique pour calculer le diamètre d'un polygone convexe
	 *
	 * @param points
	 * @return
	 */
	public static Double diameter(List<Point> points) {
		if (points.size() < 3) {
			return null;
		}

		Point p = points.get(0);
		Point q = points.get(1);
		double distance = 0;

		for (Point point : points) {
			for (Point point2 : points) {
				if (distance < point.distance(point2)) {
					distance = point.distance(point2);
					p = point;
					q = point2;
				}
			}
		}

		return p.distance(q);
	}

	/**
	 * Calcule l'air d'un polygone quelconque
	 *
	 * @param polygon
	 * @return
	 */
	public static double getAreaPolygon(List<Point> polygon) {
		double area = 0;

		for (int i = 0; i < polygon.size(); i++) {
			area += polygon.get(i).x * polygon.get((i + 1) % polygon.size()).y;
			area -= polygon.get(i).y * polygon.get((i + 1) % polygon.size()).x;
		}

		return Math.round(0.5 * area * 100) / 100;
	}

	/**
	 * Cette fonction trés sale calcule l'enveloppe convexe d'un ensemble de
	 * points en utilisant la méthode Jarvis.
	 *
	 * @param points
	 * @return enveloppe convexe
	 */
	public static ArrayList<Point> convexHull(List<Point> points) {
		if (points.size() < 3) {
			return null;
		}
		ArrayList<Point> r = new ArrayList<Point>();

		Point Q = Point.getMinX(points);
		r.add(Q);
		Point P = null;

		for (Point point1 : points) {
			if (point1.equals(Q)) {
				continue;
			}
			boolean cote = true;
			for (Point point2 : points) {
				if (produit_vect(Q, point1, point2) > 0) {
					cote = false;
					break;
				}
			}
			if (cote == true) {
				P = point1;
				break;
			}
		}

		double co;
		Point PP = null;
		boolean stop = false;
		Point O = Q;
		int d = 0;

		while (!stop) {
			double res = -10000;
			for (Point point : points) {
				if (!point.equals(Q)) {
					co = (Q.x - P.x) * (point.x - Q.x) + (Q.y - P.y) * (point.y - Q.y);
					if (co / Math.abs(Q.distance(point)) > res) {
						res = co / Math.abs(Q.distance(point));
						PP = point;
					}
				}
			}
			if (PP.equals(O) || d == 20) {
				stop = true;
			}
			d = d + 1;
			P = Q;
			Q = PP;
			r.add(PP);
		}
		return r;
		// if (points.size() < 3) { return null; }
		// PointI P = points.get(0);
		// for (PointI point : points) {
		// if (point.x < P.x) {
		// P = point;
		// }
		// }
		//
		// PointI S = P;
		// boolean stop = false;
		// ArrayList<PointI> result = new ArrayList<PointI>();
		// result.add(P);
		//
		// while (!stop) {
		// PointI Q = null;
		// for (PointI point1 : points) {
		// if (point1.equals(P)) {
		// continue;
		// }
		// boolean cote = true;
		// for (PointI point2 : points) {
		// if (produit_vect(P, point1, point2) > 0) {
		// cote = false;
		// break;
		// }
		// }
		// if (cote == true) {
		// Q = point1;
		// break;
		// }
		// }
		//
		// P = Q;
		// if (P == S) break;
		// result.add(Q);
		//
		// }
		// System.out.println("fin ocnvexe");
		//
		// ArrayList<PointI> convexHull = new ArrayList<PointI>();
		// for (int i = 1; i < result.size() + 1; i++) {
		// convexHull.add(result.get(result.size() - i));
		// }
		//
		// return convexHull;
	}
}
