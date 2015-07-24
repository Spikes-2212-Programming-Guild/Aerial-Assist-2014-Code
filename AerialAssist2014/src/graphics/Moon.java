package graphics;

import java.awt.Color;

import physics.Scalar;

public class Moon extends Satellite {

	public Moon(Circle earth, Scalar velocity, double angle, Rotation dir) {
		super(earth, Constants.MOON_MASS, Constants.MOON_ROUND_RADIUS, angle,
				velocity, dir, Constants.MOON_RADIUS, Color.WHITE);
	}

}
