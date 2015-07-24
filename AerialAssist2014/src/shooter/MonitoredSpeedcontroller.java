package shooter;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * MADE ESPICAILLY FOR SHOOTER!
 *
 * @author barak
 */
public class MonitoredSpeedcontroller implements SpeedController {

    /**
     * the speed controller
     */
    private final SpeedController speedControllr;
    
    /**
     * the encoder monitoring the speed controller
     */
    private final Encoder encoder;

    /**
     * constructor
     *
     * @param speedController the Speed Controller
     * @param encoder The Encoder
     */
    public MonitoredSpeedcontroller(SpeedController speedController, Encoder encoder) {
        this.speedControllr = speedController;
        this.encoder = encoder;
    }

    /**
     * @return the signal of the speed controller
     */
    public double get() {
        return speedControllr.get();
    }

    /**
     * generate signal and delay to the speed controller
     *
     * @param speed - the speed
     * @param b - the delay
     */
    public void set(double speed, byte b) {
        speedControllr.set(speed, b);
    }

    /**
     * generate signal to the speed controller
     *
     * @param speed
     */
    public void set(double speed) {
        speedControllr.set(speed);
    }

    /**
     * the function disables the speed controller
     */
    public void disable() {
        speedControllr.disable();
    }

    /**
     * the function for PID - not going to be used
     *
     * @param pid
     */
    public void pidWrite(double pid) {
        speedControllr.set(pid);
    }

    /**
     * @return the angle of the arch
     */
    public double getAngle() {
        return -encoder.getRaw();
    }

    /**
     * the function resets the encoder
     */
    public void resetEncoder() {
        encoder.reset();
    }
}