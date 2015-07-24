package routines;

import rollers.Roller;

/**
 *
 * @author asaf
 */
public class FoldToAngleRoutine extends Routine {
    
    private final Roller roller;
    private double targetAngle;

    public FoldToAngleRoutine(Roller roller) {
        this.roller = roller;
    }

    protected void doRoutine() {
        roller.foldToAngle(targetAngle);
    }

    protected void endRoutine() {
        roller.stopFolder();
    }

    public void start(double targetAngle, boolean onThread) {
        this.targetAngle = targetAngle;
        super.start(onThread);
    }

    public double getTargetAngle() {
        return targetAngle;
    }
}