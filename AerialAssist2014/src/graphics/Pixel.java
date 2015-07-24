package graphics;

import physics.Quantity;
import physics.Scalar;
import physics.Util;

public class Pixel {
	private static Scalar pixel = Scalar.METER.multiply(1e9);

	public static void scroll(double c) {
		if (c <= 0) {
			throw new IllegalArgumentException("must be positive");
		}
		pixel = pixel.multiply(c);
	}

	public static void set(Scalar s) {
		pixel = Util.assertQuantity(s, Quantity.LENGTH);
	}

	public static int to(Scalar s) {
		return (int) s.convert(pixel);
	}

	public static Scalar from(int n) {
		return pixel.multiply(n);
	}

	public static Scalar get() {
		return pixel;
	}
}