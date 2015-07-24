package routines;

import consts.RollerConsts;
import rollers.Roller;

/**
 *
 * @author asaf
 */
public class GyroBottomCalibrationRoutine extends Routine {
    
    private final Roller roller;

    public GyroBottomCalibrationRoutine(Roller roller) {
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
        roller.unfoldToEnd();
        roller.foldToAngle(angle);
        
    }

    protected void endRoutine() {
        roller.stopFolder();
    }
    
}
