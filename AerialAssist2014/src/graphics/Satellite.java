package graphics;

import java.awt.Color;

import physics.Scalar;
import physics.Vector;

public class Satellite extends Circle {

	private Circle circle;

	public Satellite(Circle circle, Scalar mass, Scalar roundRadius,
			double angle, Scalar velocity, Rotation dir, Scalar radius,
			Color color) {
		super(mass, circle.getCenter()
				.add(Vector.fromPolar(roundRadius, angle)), Vector.fromPolar(
				velocity, angle + dir.getAngle(Math.PI / 2)), radius, color);

	}

	public Scalar currentRotationRadius() {
		return distance(circle);
	}

	public Scalar getGravitationalEnergy() {
		return Scalar.G.multiply(getMass()).multiply(circle.getMass())
				.divide(currentRotationRadius()).negate();
	}
}
