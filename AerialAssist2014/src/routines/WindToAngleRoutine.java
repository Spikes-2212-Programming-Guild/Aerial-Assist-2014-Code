package routines;

import shooter.Shooter;

/**
 *
 * @author asaf
 */
public class WindToAngleRoutine extends Routine {

    /**
     * the robot's shooter
     */
    private final Shooter shooter;
    /**
     * the target winding angle
     */
    private double targetAngle;

    /**
     * @param shooter the robot's shooter
     */
    public WindToAngleRoutine(Shooter shooter) {
        this.shooter = shooter;
    }

    /**
     * wind to the target angle
     */
    protected void doRoutine() {
        shooter.windToAngle(targetAngle);
    }

    /**
     * stop winding for fuck's sake
     */
    protected void endRoutine() {
        shooter.windBySpeed(0);
    }

    /**
     * start the routine
     * @param angle the target angle for the shooter to wind to
     * @param onThread should the routine run on a different thread?
     */
    public void start(double angle, boolean onThread) {
        targetAngle = angle;
        super.start(onThread);
    }

    /**
     * @return the target angle for the shooter to wind to 
     */
    public double getTargetAngle() {
        return targetAngle;
    }
}
