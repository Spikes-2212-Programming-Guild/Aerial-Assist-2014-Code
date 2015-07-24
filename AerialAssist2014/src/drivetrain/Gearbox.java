package drivetrain;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author asaf
 */
public class Gearbox implements SpeedController {

    private SpeedController frontController, rearController;

    /**
     * Create new Gearbox with Talons in the written ports.
     *
     * @param frontChannel channel of the first SpeedController
     * @param rearChannel channel of the second SpeedController
     */
    public Gearbox(int frontChannel, int rearChannel) {
        this(new Talon(frontChannel), new Talon(rearChannel));
    }

    /**
     * Create a Gearbox with the speedControllers;
     *
     * @param front front SpeedController
     * @param rear rear SpeedController
     */
    public Gearbox(SpeedController front, SpeedController rear) {
        this.frontController = front;
        this.rearController = rear;
    }

    /**
     * Stops the gearbox.
     */
    public void stop() {
        set(0);
    }
    /**
     * @return Speed of the robot in range of [-1.0,1.0]
     */
    public double get() {
        return frontController.get();
    }


    /**
     * @param speed Speed of the Gearbox in range of [-1.0,1.0]
     */
    public void set(double speed) {
        if (speed > 1) {
            speed = 1;
        } else if (speed < -1) {
            speed = -1;
        }
        frontController.set(speed);
        rearController.set(speed);
    }

    /**
     * Disable all the speed controllers of the gearbox.
     */
    public void disable() {
        frontController.disable();
        rearController.disable();
    }

    /**
     * Same as set(speed).
     * Use in PID computations.
     * @param speed 
     */
    public void pidWrite(double speed) {
        set(speed);
    }
    
    
    
    /**
     * @deprecated Byte primitive type is not used
     * @param speed
     * @param b
     */
    public void set(double speed, byte b) {
        set(speed);
    }
}
