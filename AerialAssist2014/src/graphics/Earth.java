package graphics;

import java.awt.Color;

import physics.Scalar;

public class Earth extends Satellite {

	public Earth(Circle sun, Scalar velocity, double angle, Rotation dir) {
		super(sun, Constants.EARTH_MASS, Constants.EARTH_ROUND_RADIUS, angle,
				velocity, dir, Constants.EARTH_RADIUS, Color.GREEN);
	}
}
