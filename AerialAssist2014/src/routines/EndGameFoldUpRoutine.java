package routines;

import consts.ShooterConsts;
import rollers.Roller;
import shooter.Shooter;

/**
 *
 * @author asaf
 */
public class EndGameFoldUpRoutine extends Routine {

    private final Roller roller;
    private final Shooter shooter;

    public EndGameFoldUpRoutine(Roller roller, Shooter shooter) {
        this.roller = roller;
        this.shooter = shooter;
    }

    protected void doRoutine() {
        shooter.windBySpeed(0);
        roller.stopFolder();
        roller.stopRollers();
        shooter.windToAngle(0);
        shooter.unlock();
        shooter.unwind();
        try {
            Thread.sleep(ShooterConsts.WAIT_TIME_BETWEEN_UNWIND_AND_LOCK);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        shooter.lock();
        roller.foldToEnd();
    }

    protected void endRoutine() {
        shooter.windBySpeed(0);
        roller.stopFolder();
    }

}
