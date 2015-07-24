package graphics;

import physics.Quantity;
import physics.Scalar;
import physics.Util;
import physics.Vector;

public abstract class Thing implements Drawable {

	private final Scalar mass;
	private Vector center;
	private Vector velocity;
	private Vector acceleration;

	public Thing(Scalar mass, Vector center, Vector velocity) {
		this.mass = mass;
		this.center = center;
		this.velocity = velocity;
		this.acceleration = Vector.zero(Quantity.ACCELERATION);
	}

	public Scalar getMass() {
		return mass;
	}

	public Vector getCenter() {
		return center;
	}

	public Vector getVelocity() {
		return velocity;
	}

	public Vector getAcceleration() {
		return acceleration;
	}

	public Scalar getGravitationalField(Scalar radius) {
		Util.assertQuantity(radius, Quantity.LENGTH);
		return Scalar.G.multiply(mass).divide(radius.pow(2));
	}

	public Scalar getKinecticEnergy() {
		return Scalar.product(velocity.getMagnitude(), velocity.getMagnitude(),
				mass).multiply(0.5);
	}

	public void setAcceleration(Vector acceleration) {
		Util.assertQuantity(acceleration, Quantity.ACCELERATION);
		this.acceleration = acceleration;
	}

	public void setForce(Vector force) {
		Util.assertQuantity(force, Quantity.FORCE);
		this.acceleration = force.divide(mass);
	}

	public void move(Scalar dt) {
		Util.assertQuantity(dt, Quantity.TIME);
		center = center.add(velocity.multiply(dt));
		velocity = velocity.add(acceleration.multiply(dt));
	}

	public Scalar distance(Thing b) {
		return center.subtract(b.center).getMagnitude();
	}

	public double angle(Thing s) {
		return s.center.subtract(center).getPhase();
	}

}
