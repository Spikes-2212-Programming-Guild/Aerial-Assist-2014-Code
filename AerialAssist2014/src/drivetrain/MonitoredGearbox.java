package drivetrain;

import consts.DrivetrainConsts;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author alon & roni && ETHAN RULES!!
 */
public class MonitoredGearbox extends Gearbox {

    /**
     * The radius of the wheels, scaled by any unit you want.
     */
    private double wheelRadius;
    /**
     * The encoder used by this MonitoredGearbox.
     */
    private Encoder encoder;

    /**
     * SpeedControllers are Talons by default.
     *
     * @param encoderChannelA Channel of the A digital source
     * @param encoderChannelB Channel of the B digital source
     * @param wheelRadius Radius of the wheels
     * @param frontChannel Channel of the front controller
     * @param rearChannel Channel of the rear controller
     */
    public MonitoredGearbox(int encoderChannelA, int encoderChannelB, double wheelRadius, int frontChannel, int rearChannel) {
        this(new Encoder(encoderChannelA, encoderChannelB), wheelRadius, frontChannel, rearChannel);
    }

    /**
     * SpeedControllers are Talons by default.
     *
     * @param encoder An encoder used by this gearbox.
     * @param wheelRadius Radius of the wheels
     * @param frontChannel Channel of the front controller
     * @param rearChannel Channel of the rear controller
     */
    public MonitoredGearbox(Encoder encoder, double wheelRadius, int frontChannel, int rearChannel) {
        this(encoder, wheelRadius, new Talon(frontChannel), new Talon(rearChannel));
    }

    /**
     *
     * @param encoder An encoder used by this gearbox.
     * @param wheelRadius Radius of the wheels
     * @param front Front SpeedController
     * @param rear Rear SpeedController
     */
    public MonitoredGearbox(Encoder encoder, double wheelRadius, SpeedController front, SpeedController rear) {
        super(front, rear);
        this.wheelRadius = wheelRadius;
        this.encoder = encoder;
    }

    /**
     * Get the distance the robot has driven since the last reset.
     *
     * @return The distance the robot has driven
     */
    public double getDistance() {
        return encoder.getDistance();
    }
    

    /**
     * Get the angle of the encoder.
     *
     * @return Angle
     */
    public double getAngle() {
        return encoder.get();
    }

    /**
     * Reset the distance the robot has driven to 0.
     */
    public void reset() {
        encoder.reset();
    }

    /**
     * Set a speed to the Gearbox.
     *
     * @param speed Speed to set to the Gearbox.
     */
    public void set(double speed) {
        super.set(speed);
    }

    /**
     * @param radius The radius of the wheels in centimeters
     */
    public void setWheelRadius(double radius) {
        this.wheelRadius = radius;
        encoder.setDistancePerPulse(2 * Math.PI * radius / DrivetrainConsts.ENCODER_TICKS);
    }

    /**
     * Get the radius of the wheels.
     *
     * @return The radius of the wheels in centimeters
     */
    public double getWheelRadius() {
        return wheelRadius;
    }
}
