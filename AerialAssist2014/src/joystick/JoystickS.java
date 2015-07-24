package joystick;

import consts.JoystickConsts;
import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author Developer
 */
public class JoystickS extends Joystick {
    
    /**
     * an array of all the buttons, storing whether they were pressed during the las iteration or not
     */
    private final boolean[] wasPressed;

    /**
     * @param port the driver station port for this joystick
     */
    public JoystickS(int port) {
        super(port);
        wasPressed = new boolean[JoystickConsts.MAX_BUTTON_NUMBER + 1];
    }

    /**
     * was the button just pressed for the first time (not held pressed)
     * @param button the number of the button
     * @return was the button just pressed for the first time (not held pressed)?
     */
    public boolean getRawButtonFirstTime(int button) {
        boolean value = !wasPressed[button] && super.getRawButton(button);
        wasPressed[button] = super.getRawButton(button);
        return value;
    }
}
