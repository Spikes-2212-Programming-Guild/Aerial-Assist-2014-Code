package graphics;

import physics.Scalar;

public class Constants {
	public static final Scalar SUN_MASS = Scalar.KILOGRAM.multiply(1.99e30);
	public static final Scalar SUN_RADIUS = Pixel.from(64); // Scalar.METER.multiply(6.96e8);
	public static final Scalar EARTH_MASS = Scalar.KILOGRAM.multiply(5.974e24);
	public static final Scalar EARTH_RADIUS = Pixel.from(16); // Scalar.METER.multiply(6.38e6);
	public static final Scalar EARTH_ROUND_RADIUS = Scalar.METER
			.multiply(149.6e9);
	public static final Scalar MOON_MASS = Scalar.KILOGRAM.multiply(7.35e22);
	public static final Scalar MOON_RADIUS = Pixel.from(4); // Scalar.METER.multiply(1.74e6);
	public static final Scalar MOON_ROUND_RADIUS = Scalar.METER
			.multiply(3.84e8);
}
