package physics;

public class Vector {

	public static final Vector METER_X = new Vector(1, 0, Scalar.METER);
	public static final Vector METER_Y = new Vector(0, 1, Scalar.METER);

	private final Scalar x;
	private final Scalar y;

	public Vector(Scalar x, Scalar y) {
		Util.assertQuantity(x, y);
		this.x = x;
		this.y = y;
	}

	public Vector(double x, double y, Scalar s) {
		this(s.multiply(x), s.multiply(y));
	}

	public static Vector zero(Quantity q) {
		return new Vector(Scalar.zero(q), Scalar.zero(q));
	}

	public Vector multiply(Scalar s) {
		return new Vector(x.multiply(s), y.multiply(s));
	}

	public Quantity getQuantity() {
		return x.getQuantity();
	}

	public static Vector fromPolar(Scalar r, double phase) {
		return new Vector(r.multiply(Math.cos(phase)), r.multiply(Math
				.sin(phase)));
	}

	public Scalar getMagnitude() {
		return Scalar.sqrt(x.pow(2).add(y.pow(2)));
	}

	public double getPhase() {
		return Scalar.atan2(y, x);
	}

	public Vector subtract(Vector v) {
		return add(v.negate());
	}

	public Vector add(Vector w) {
		return new Vector(x.add(w.x), y.add(w.y));
	}

	public Vector negate() {
		return new Vector(x.negate(), y.negate());
	}

	public Scalar getX() {
		return x;
	}

	public Scalar getY() {
		return y;
	}

	@Override
	public String toString() {
		return "Vector(" + x + ", " + y + ")";
	}

	public Vector divide(Scalar s) {
		return multiply(s.inverse());
	}
}
