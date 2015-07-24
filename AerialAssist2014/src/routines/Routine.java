package routines;

/**
 * A general lass for all the autonomous routines the robot has to do
 * 
 * @author asaf
 */
public abstract class Routine implements Runnable {

    /**
     * the routine that is currently the active one
     */
    private static Routine activeRoutine;

    /**
     * @return is there a routine running now?
     */
    public static boolean isRoutineRunning() {
        if (activeRoutine == null) {
            return false;
        }
        return activeRoutine.isRunning();
    }

    /**
     * stops the active routine
     */
    public synchronized static void stopRoutine() {
        if (activeRoutine != null) {
            activeRoutine.stop();
        }
    }
    
    
    /**
     * is the routine running?
     */
    private boolean running;
    /**
     * the thread this routine runs in
     */
    private Thread thread;

    public Routine() {
        this.running = false;
        thread = new Thread(this);
    }

    /**
     * the body of the routine, where all the magic happens. here the routine's autonomous actions should be called. this function is used internally in the Routine class, and should not be called unless you know what you're doing,
     */
    protected abstract void doRoutine();

    /**
     * here the actions needed to end the routine (or stop it in the middle) should be called. this function is used internally in the Routine class, and should not be called unless you know what you're doing,
     */
    protected abstract void endRoutine();

    /**
     * @return is the routine running?
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * stop the routine and anything it may be running right now.
     */
    public void stop() {
        endRoutine();
        thread.interrupt();
        running = false;
    }

    /**
     * run the routine. this function is used internally in the Routine class, and should not be called unless you know what you're doing,
     */
    public void run() {
        running = true;
        doRoutine();
        stop();
    }

    /**
     * start the autonomous routine.
     * @param onThread should the routine run on a different thread?
     */
    public void start(boolean onThread) {
        stopRoutine();
        activeRoutine = this;
        if (onThread) {
            thread = new Thread(this);
            thread.start();
        } else {
            run();
        }
    }
}