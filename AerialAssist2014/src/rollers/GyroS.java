package rollers;

import edu.wpi.first.wpilibj.Gyro;

/**
 * A gyro that can be calibrated to any angle
 *
 * @author asaf
 */
public class GyroS extends Gyro {
    
    /**
     * the angle in which the gyro was last calibrated
     */
    private double calibrateAngle;

    /**
     * Gyro constructor with only a channel.
     *
     * Use the default analog module slot.
     *
     * @param channel The analog channel the gyro is connected to.
     */
    public GyroS(int channel) {
        super(channel);
    }

    /**
     * calibrate the gyro to a given angle
     * @param startAngle the angle the gyro is currently in
     */
    public void reset(double startAngle) {
        calibrateAngle = startAngle;
        super.reset();
    }

    /**
     * @return the gyro's angle
     */
    public double getAngle() {
        return -super.getAngle() + calibrateAngle;
    }

    /**
     * @return the angle in which the gyro was last calibrated
     */
    public double getCalibrateAngle() {
        return calibrateAngle;
    }
}
