package graphics;

import java.awt.Color;
import java.awt.Graphics;

import physics.Scalar;
import physics.Vector;

public class Circle extends Thing {

	private Color color;
	private Scalar radius;

	public Circle(Scalar mass, Vector center, Vector velocity, Scalar radius,
			Color color) {
		super(mass, center, velocity);
		this.color = color;
		this.radius = radius;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void draw(Graphics g, Vector focus) {
		g.setColor(color);
		int i_radius = Pixel.to(radius);
		Vector p = focus.add(getCenter().subtract(new Vector(radius, radius)));
		int x = Pixel.to(p.getX());
		int y = Pixel.to(p.getY());
		g.fillOval(x, y, 2 * i_radius, 2 * i_radius);

	}

	public Scalar getRadius() {
		return radius;
	}

	public Scalar getArea() {
		return radius.pow(2).multiply(Math.PI);
	}

}
