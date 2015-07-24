package physics;

public class UnitSystem {

	public static final UnitSystem SI = new UnitSystem(Scalar.METER,
			Scalar.SECOND, Scalar.KILOGRAM);
	public static final UnitSystem CGS = new UnitSystem(
			Scalar.METER.multiply(1e-2), Scalar.SECOND,
			Scalar.KILOGRAM.multiply(1e-3));

	private final Scalar length;
	private final Scalar time;
	private final Scalar mass;

	public UnitSystem(Scalar length, Scalar time, Scalar mass) {
		this.length = length;
		this.time = time;
		this.mass = mass;
	}

	public Scalar get(Quantity q) {
		return Scalar.product(length.pow(q.getLength()), time.pow(q.getTime()),
				mass.pow(q.getMass()));
	}
}
