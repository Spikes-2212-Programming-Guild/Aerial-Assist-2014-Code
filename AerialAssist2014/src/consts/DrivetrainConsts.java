package consts;

import drivetrain.Drivetrain;
import drivetrain.MonitoredGearbox;
import pid.PID;

/**
 * constants regarding the robot's drive train
 */
public class DrivetrainConsts {

    /**
     * the left gearbox in the drive train
     */
    private static final MonitoredGearbox LEFT_GEARBOX = new MonitoredGearbox(Channels.LEFT_ENCODER_CHANNEL_A, Channels.LEFT_ENCODER_CHANNEL_B, DrivetrainConsts.WHEEL_RADIUS, Channels.LEFT_FRONT_TALON_CHANNEL, Channels.LEFT_REAR_TALON_CHANNEL);
    /**
     * the right gearbox in the drive train
     */
    private static final MonitoredGearbox RIGHT_GEARBOX = new MonitoredGearbox(Channels.RIGHT_ENCODER_CHANNEL_A, Channels.RIGHT_ENCODER_CHANNEL_B, DrivetrainConsts.WHEEL_RADIUS, Channels.RIGHT_FRONT_TALON_CHANNEL, Channels.RIGHT_REAR_TALON_CHANNEL);
    /**
     * the robot's drive train instance
     */
    public static final Drivetrain DRIVETRAIN = new Drivetrain(LEFT_GEARBOX, RIGHT_GEARBOX);
    
    /**
     * the number of ticks an encoder has in one full rotation
     */
    public static final int ENCODER_TICKS = 60;
    /**
     * the drive train wheels' radius
     */
    public static double WHEEL_RADIUS = 5.08; // in centimeters
    /**
     * the drive train's PID P constant
     */
    public static final double DRIVETRAIN_PID_KP = 6e-4;
    /**
     * the drive train's PID I constant
     */
    public static final double DRIVETRAIN_PID_KI = 0;
    /**
     * the drive train's PID D constant
     */
    public static final double DRIVETRAIN_PID_KD = 0;
    /**
     * the drive train's PID tolerance
     */
    public static final double DRIVETRAIN_PID_TOLERANCE = 200;
    /**
     * a constant representing the number of ticks in a meter
     */
    public static final double DRIVETRAIN_METERS_TO_ENCODER = 770;
    /**
     * the distance the robot has to pass in autonomous mode
     */
    public static final double DRIVETRAIN_AUTONOMUS_DRIVE_DISTANCE = 5;
    
    /**
     * the drive train's PID
     */
    public static final PID DRIVE_PID = new PID(DRIVETRAIN, DRIVETRAIN, DRIVETRAIN_PID_KP, DRIVETRAIN_PID_KI, DRIVETRAIN_PID_KD, GeneralConsts.ITTERATION_DELAY, DRIVETRAIN_PID_TOLERANCE, DRIVETRAIN_METERS_TO_ENCODER * DRIVETRAIN_AUTONOMUS_DRIVE_DISTANCE);

    /**
     * the drive train's controllers and sensors' channels
     */
    public static class Channels {

        public static final int LEFT_FRONT_TALON_CHANNEL = 1;
        public static final int LEFT_REAR_TALON_CHANNEL = 2;
        public static final int RIGHT_FRONT_TALON_CHANNEL = 9;
        public static final int RIGHT_REAR_TALON_CHANNEL = 10;
        public static final int LEFT_ENCODER_CHANNEL_A = 1;
        public static final int LEFT_ENCODER_CHANNEL_B = 2;
        public static final int RIGHT_ENCODER_CHANNEL_A = 3;
        public static final int RIGHT_ENCODER_CHANNEL_B = 4;
    }
}
