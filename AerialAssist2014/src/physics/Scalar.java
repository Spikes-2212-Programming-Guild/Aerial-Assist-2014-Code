package physics;

import java.util.Objects;

public class Scalar implements Comparable<Scalar> {

	private final Quantity quantity;
	private final double value;

	public static final Scalar METER = new Scalar(Quantity.LENGTH, 1);
	public static final Scalar CENTIMETER = new Scalar(Quantity.LENGTH, 1e-2);
	public static final Scalar SECOND = new Scalar(Quantity.TIME, 1.0/365/24/60);
	public static final Scalar NANOSECOND = new Scalar(Quantity.TIME, 1e-9);
	public static final Scalar KILOGRAM = new Scalar(Quantity.MASS, 1);
	public static final Scalar NEWTONE = new Scalar(Quantity.FORCE, 1);
	public static final Scalar JOULE = new Scalar(Quantity.ENERGY, 1);
	public static final Scalar HERTZ = SECOND.inverse();
	public static final Scalar ONE = new Scalar(Quantity.NONE, 1);
	public static final Scalar G = METER.pow(3)
			.divide(product(SECOND, SECOND, KILOGRAM)).multiply(6.67384e-11);

	private Scalar(Quantity quantity, double value) {
		this.quantity = Objects.requireNonNull(quantity);
		if (Double.isNaN(value)) {
			throw new IllegalArgumentException(
					"Must be a finite or infinite number: " + value);
		}
		this.value = value;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public static Scalar zero(Quantity q) {
		return new Scalar(q, 0);
	}

	public double convert(Scalar v) {
		Util.assertQuantity(this, v);
		return value / v.value;
	}

	public double convert(UnitSystem s) {
		return convert(s.get(quantity));
	}

	public Scalar multiply(Scalar v) {
		return new Scalar(Quantity.multiply(quantity, v.quantity), value
				* v.value);
	}

	public Scalar inverse() {
		return new Scalar(Quantity.inverse(quantity), 1 / value);
	}

	public Scalar multiply(double c) {
		return new Scalar(quantity, value * c);
	}

	public Scalar divide(double c) {
		return new Scalar(quantity, value / c);
	}

	public Scalar add(Scalar v) {

		Util.assertQuantity(this, v);
		return new Scalar(quantity, value + v.value);
	}

	public Scalar negate() {
		return new Scalar(quantity, -value);
	}

	public Scalar subtract(Scalar v) {
		return add(v.negate());
	}

	public Scalar pow(int n) {
		return new Scalar(Quantity.pow(quantity, n), Math.pow(value, n));
	}

	public static Scalar sqrt(Scalar u) {
		return new Scalar(Quantity.sqrt(u.quantity), Math.sqrt(u.value));
	}

	public static Scalar product(Scalar... us) {
		Scalar res = ONE;
		for (Scalar u : us) {
			res = res.multiply(u);
		}
		return res;
	}

	public Scalar divide(Scalar v) {
		return multiply(v.inverse());
	}

	public static double atan2(Scalar y, Scalar x) {
		Util.assertQuantity(y, x);
		return Math.atan2(y.value, x.value);
	}

	@Override
	public String toString() {
		int length = quantity.getLength();
		int time = quantity.getTime();
		int mass = quantity.getMass();
		return value + " m^" + length + " " + "s^" + time + " " + "kg^" + mass;
	}

	@Override
	public int compareTo(Scalar o) {
		Util.assertQuantity(this, o);
		return Double.compare(value, o.value);
	}
}
