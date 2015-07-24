package routines;

import consts.ShooterConsts;
import shooter.Shooter;

/**
 * a routine meant to pass the ball to the low goal or to another robot over the
 * high end of the robot using the kicker
 *
 * @author asaf
 */
public class PassOverTheTopRoutine extends Routine {

    /**
     * the robot's shooter
     */
    private final Shooter shooter;

    /**
     * @param shooter the robot's shooter
     */
    public PassOverTheTopRoutine(Shooter shooter) {
        this.shooter = shooter;
    }

    /**
     * passes the ball over the high end of the robot (unwinds the kicker,
     * unlocks the trigger, winds the kicker until the ball passes over the top,
     * then unwinds it again)
     */
    protected void doRoutine() {
        shooter.unwind();
        shooter.unlock();
        shooter.windToAngle(ShooterConsts.OVER_THE_TOP_PASS_ANGLE);
        shooter.unwind();
    }

    /**
     * stops the winding
     */
    protected void endRoutine() {
        shooter.windBySpeed(0);
    }
}
