package routines;

import consts.RollerConsts;
import rollers.Roller;

/**
 * Calibrate the gyro using the top limit switch
 *
 * @author asaf
 */
public class GyroTopCalibrationRoutine extends Routine {
    
    /**
     * the robot's roller
     */
    private final Roller roller;

    /**
     * @param roller the robot's roller
     */
    public GyroTopCalibrationRoutine(Roller roller) {
        this.roller = roller;
    }

    protected void doRoutine() {
        double angle = roller.getAngle();
        if (angle > RollerConsts.FOLDED_ANGLE) {
            angle = RollerConsts.FOLDED_ANGLE;
        }
        else if (angle < RollerConsts.UNFOLDED_ANGLE) {
            angle = RollerConsts.UNFOLDED_ANGLE;
        }
        roller.foldToEnd();
        roller.foldToAngle(angle);
        
    }

    protected void endRoutine() {
        roller.stopFolder();
    }
}