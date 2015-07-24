package graphics;

import java.awt.Graphics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import physics.Quantity;
import physics.Scalar;
import physics.Vector;

public class PhysicalSystem implements Drawable {
	private List<Thing> things;

	public PhysicalSystem(Thing... things) {
		this.things = new CopyOnWriteArrayList<Thing>(things);
	}

	public synchronized void applyGravityForces() {
		for (Thing t : things) {
			Vector a = Vector.zero(Quantity.ACCELERATION);
			for (Thing s : things) {
				if (t != s) {
					a = a.add(Vector.fromPolar(
							s.getGravitationalField(t.distance(s)), t.angle(s)));
				}
			}
			t.setAcceleration(a);
		}
	}

	public synchronized Scalar getKineticEnergy() {
		Scalar sum = Scalar.zero(Quantity.ENERGY);
		for (Thing t : things) {
			sum = sum.add(t.getKinecticEnergy());
		}
		return sum;
	}

	@Override
	public synchronized void draw(Graphics g, Vector focus) {
		for (Thing t : things) {
			t.draw(g, focus);
		}
	}

	public synchronized void move(Scalar dt) {
		for (Thing t : things) {
			t.move(dt);
		}
	}
}
