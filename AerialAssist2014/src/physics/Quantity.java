package physics;

public class Quantity {

	public static final Quantity NONE = new Quantity(0, 0, 0);
	public static final Quantity LENGTH = new Quantity(1, 0, 0);
	public static final Quantity TIME = new Quantity(0, 1, 0);
	public static final Quantity MASS = new Quantity(0, 0, 1);
	public static final Quantity VELOCITY = divide(LENGTH, TIME);
	public static final Quantity ACCELERATION = divide(VELOCITY, TIME);
	public static final Quantity FORCE = multiply(MASS, ACCELERATION);
	public static final Quantity WORK = multiply(FORCE, LENGTH);
	public static final Quantity ENERGY = WORK;
	public static final Quantity AREA = pow(LENGTH, 2);
	public static final Quantity VOLUME = pow(LENGTH, 3);
	public static final Quantity DENSITY = divide(MASS, VOLUME);

	public static Quantity multiply(Quantity p, Quantity q) {
		return new Quantity(p.length + q.length, p.time + q.time, p.mass
				+ q.mass);
	}

	public static Quantity pow(Quantity q, int n) {
		return new Quantity(q.length * n, q.time * n, q.mass * n);
	}

	public static Quantity root(Quantity q, int n) {
		if (q.length % n != 0 || q.time % n != 0 || q.mass % n != 0) {
			throw new IllegalArgumentException("Root " + n + " not possible");
		}
		return new Quantity(q.length / n, q.time / n, q.mass / n);
	}

	public static Quantity sqrt(Quantity q) {
		return root(q, 2);
	}

	public static Quantity divide(Quantity p, Quantity q) {
		return multiply(p, inverse(q));
	}

	public static Quantity inverse(Quantity q) {
		return new Quantity(-q.length, -q.time, -q.mass);
	}

	private final int length, time, mass;

	private Quantity(int length, int time, int mass) {
		this.length = length;
		this.time = time;
		this.mass = mass;
	}

	public int getLength() {
		return length;
	}

	public int getMass() {
		return mass;
	}

	public int getTime() {
		return time;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Quantity && this.equalsQuantity((Quantity) obj);
	}

	private boolean equalsQuantity(Quantity p) {
		return this.length == p.length && this.time == p.time
				&& this.mass == p.mass;
	}

	@Override
	public String toString() {
		return "L^" + length + " " + "T^" + time + " " + "M^" + mass;
	}
}
