package consts;

import joystick.JoystickS;

/**
 * constants regarding the joysticks
 */
public class JoystickConsts {
    
    /**
     * the driver's left joystick
     */
    public static final JoystickS DRIVER_LEFT_JOYSTICK = new JoystickS(JoystickConsts.DRIVER_LEFT_JOYSTICK_PORT);
    /**
     * the driver's right joystick
     */
    public static final JoystickS DRIVER_RIGHT_JOYSTICK = new JoystickS(JoystickConsts.DRIVER_RIGHT_JOYSTICK_PORT);
    /**
     * he navigator's joystick
     */
    public static final JoystickS NAVIGATOR_JOYSTICK = new JoystickS(JoystickConsts.NAVIGATOR_JOYSTICK_PORT);

    /**
     * the driver's left joystick's port in the driver station
     */
    public static final int DRIVER_LEFT_JOYSTICK_PORT = 1;
    /**
     * the driver's right joystick's port in the driver station
     */
    public static final int DRIVER_RIGHT_JOYSTICK_PORT = 2;
    /**
     * the navigator's joystick's port in the driver station
     */
    public static final int NAVIGATOR_JOYSTICK_PORT = 3;

    /**
     * the maximum number of buttons a joystick can have
     */
    public static final int MAX_BUTTON_NUMBER = 12;
    /**
     * the tolerance beneath which the joystick's value is considered to be zero
     */
    public static final double JOYSTICK_TOLERANCE = 0.2;
    
    /**
     * the driver's buttons
     */
    public static class DriverButtons {
        public static final int STRAIGHT_BUTTON = 1;
        public static final int ROTATE_BUTTON = 2;
    }
    
    /**
     * the navigator's buttons
     */
    public static class NavigatorButtons {
        //manual
        public static final int LOCK_BUTTON = 3;
        public static final int UNLOCK_BUTTON = 5;
        public static final int RELEASE_BLOCKAGE_BUTTON = 10;
        public static final int ROLLERS_INTAKE_SPIN_BUTTON = 6;
        public static final int ROLLERS_SPIN_OUTWARD_BUTTON = 4;
//        public static final int STOP_ROLLERS_BUTTON = 1;
        public static final int MOVE_FOLDERS_BUTTON = 1;
        public static final int MOVE_WINDERS_BUTTON = 2;
        
        //routines
        public static final int SHOOT_AND_RECOCK_BUTTON = 9;
        public static final int WIND_TO_ANGLE_BUTTON = 7;
//        public static final int END_GAME_BUTTON = 1;
        public static final int TRUSS_SHOOT_FOLD = 12;
        public static final int GOAL_SHOOT_FOLD = 8;
        public static final int PASS_OVER_THE_TOP_BUTTON = 11;
//        public static final int GYRO_BOTTOM_CALIBRATE_BUTTOM = 10;
    }

}
