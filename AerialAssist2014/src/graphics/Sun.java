package graphics;

import java.awt.Color;

import physics.Quantity;
import physics.Vector;

public class Sun extends Circle {

	public Sun(Vector center) {
		super(Constants.SUN_MASS, center, Vector.zero(Quantity.VELOCITY),
				Constants.SUN_RADIUS, Color.YELLOW);
	}

}
