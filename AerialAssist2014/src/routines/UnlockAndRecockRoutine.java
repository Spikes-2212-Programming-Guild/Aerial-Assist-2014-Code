package routines;

import consts.ShooterConsts;
import shooter.Shooter;

/**
 *
 * @author asaf
 */
public class UnlockAndRecockRoutine extends Routine {
    
    /**
     * the robot's shooter
     */
    private final Shooter shooter;

    /**
     * @param shooter the robot's shooter
     */
    public UnlockAndRecockRoutine(Shooter shooter) {
        this.shooter = shooter;
    }

    /**
     * unlock the trigger and release the kicker, then unwind the kicker
     */
    protected void doRoutine() {
        try {
            shooter.unlock();
            Thread.sleep(ShooterConsts.WAIT_TIME_BETWEEN_UNLOCK_AND_UNWIND);
            shooter.unwind();
        } catch (InterruptedException ex) {
        }
    }

    /**
     * stop the winders
     */
    protected void endRoutine() {
        shooter.windBySpeed(0);
    } 
}