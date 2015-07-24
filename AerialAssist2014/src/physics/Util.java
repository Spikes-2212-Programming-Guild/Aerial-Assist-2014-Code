package physics;

import graphics.Thing;

import java.util.Objects;

public class Util {

	public static void assertQuantity(Scalar s, Scalar t) {
		Objects.requireNonNull(s, "Not null!");
		Objects.requireNonNull(t, "Not null!");
		if (!s.getQuantity().equals(t.getQuantity())) {
			System.out.println(s);
			System.out.println(t);
			throw new QuantityMismatchException(s.getQuantity(),
					t.getQuantity());
		}
	}

	public static Scalar assertQuantity(Scalar s, Quantity expected) {
		assertQuantity(s.getQuantity(), expected);
		return s;
	}

	public static Vector assertQuantity(Vector v, Quantity expected) {
		assertQuantity(v.getQuantity(), expected);
		return v;
	}

	public static void assertQuantity(Quantity found, Quantity expected) {
		Objects.requireNonNull(expected, "Not null!");
		Objects.requireNonNull(found, "Not null!");
		if (!expected.equals(found)) {
			throw new QuantityMismatchException(expected, found);
		}
	}

	public static Scalar forCircularMovement(Thing t, Scalar radius) {
		return Scalar.sqrt(t.getGravitationalField(radius).multiply(radius));
	}

}

class QuantityMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public QuantityMismatchException(Quantity expected, Quantity found) {
		super("Quantity mismatch: expected " + expected + ", found " + found);
	}
}
