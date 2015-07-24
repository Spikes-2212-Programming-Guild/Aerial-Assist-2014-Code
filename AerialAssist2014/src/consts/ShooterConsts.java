package consts;

import shooter.Shooter;

/**
 * constants relating to the robot's shooter
 */
public class ShooterConsts {

    /**
     * the default wind speed of the shooter
     */
    public static final double WIND_SPEED = 1.0;
    /**
     * the winder's tolerance to encoder values
     */
    public static final double WIND_TOLERANCE = 1;
    /**
     * the wait time between the trigger's unlocking and the winder's unwinding
     */
    public static final long WAIT_TIME_BETWEEN_UNLOCK_AND_UNWIND = 2500;
    /**
     * the wait time between the winder's unwinding and the trigger's locking
     */
    public static final long WAIT_TIME_BETWEEN_UNWIND_AND_LOCK = 500;
    /**
     * the winder's axle's radius
     */
    public static final double AXIS_RADIUS = 1.29; //cm
    /**
     * the maximum time it could take the shooter to lock or unlock
     */
    public static final long LOCK_MAX_RUN_TIME_LOCK = 1000;
    /**
     * the angle the winders need to wind to in order to pass the ball over the top of the robot
     */
    public static final double OVER_THE_TOP_PASS_ANGLE = 65;
    /**
     * the shooter's maximum wind angle
     */
    public static final int MAX_WIND_ANGLE = 70;
    /**
     * the wait time at the end of unwinding before stopping the unwinding
     */
    public static final long WAIT_AT_THE_END_OF_UNWIND = 500;

    /**
     * the robot's shooter instance
     */
    public static final shooter.Shooter SHOOTER = new Shooter(Channels.WIND_MOTOR_CHANNEL, Channels.ENCODER_CHANNEL_A, Channels.ENCODER_CHANNEL_B, Channels.TRIGGER_CHANNEL, Channels.LOCK_LIMIT_SWITCH_CHANNEL, Channels.UNLOCK_LIMIT_SWITCH_CHANNEL, Channels.COCKED_MICRO_SWITCH_CHANNEL);

    /**
     * the shooter's controllers and sensors' channels
     */
    public static class Channels {

        public static final int WIND_MOTOR_CHANNEL = 5;
        public static final int ENCODER_CHANNEL_A = 13;
        public static final int ENCODER_CHANNEL_B = 14;
        public static final int TRIGGER_CHANNEL = 4;
        public static final int LOCK_LIMIT_SWITCH_CHANNEL = 9;
        public static final int UNLOCK_LIMIT_SWITCH_CHANNEL = 10;
        public static final int COCKED_MICRO_SWITCH_CHANNEL = 11;
    }

}
