package utils;

/**
 * Cette classe représente un cercle
 */
public class Circle {

	public Point center;
	public double radius;

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point point) {
		this.center = point;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public Circle(Point point, double radius) {
		super();
		this.center = point;
		this.radius = radius;
	}

}
