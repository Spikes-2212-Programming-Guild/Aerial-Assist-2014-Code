package consts;

/**
 * constants relating to the robot's rollers
 */
public class RollerConsts {
    
    /**
     * the tolerance to the gyro's angle reading
     */
    public static final double FOLDER_ANGLE_TOLERANCE = 3;
    /**
     * the rollers' angle when they are fully folded
     */
    public static final double FOLDED_ANGLE = 76;
    /**
     * the rollers' angle when they are fully unfolded
     */
    public static final double UNFOLDED_ANGLE = 15;
    /**
     * the default intake speed of the rollers
     */
    public static final double INTAKE_SPEED = -1;
    /**
     * the angle needed in order to shoot to the high goal
     */
    public static final double HIGH_GOAL_SHOOT_FOLD_ANGLE = 76;
    /**
     * the angle needed in order to shoot over the truss
     */
    public static final double TRUSS_PASS_FOLD_ANGLE = 73;
    
    /**
     * the robot's roller instance
     */
    public static final rollers.Roller ROLLER = new rollers.Roller(Channels.FOLDED_BUTTON_CHANNEL, Channels.UNFOLDED_BUTTON_CHANNEL, Channels.GYRO_CHANNEL, Channels.FOLD_RELAY_CHANNEL, Channels.SPIN_TALON_CHANNEL, RollerConsts.FOLDED_ANGLE);

    /**
     * the rollers' controllers and sensors' channels
     */
    public static class Channels {

        public static final int FOLD_RELAY_CHANNEL = 3;
        public static final int SPIN_TALON_CHANNEL = 6;
        public static final int GYRO_CHANNEL = 1;
        public static final int FOLDED_BUTTON_CHANNEL = 5;
        public static final int UNFOLDED_BUTTON_CHANNEL = 7;
    }
}
