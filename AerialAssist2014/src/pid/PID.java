package pid;

/**
 * A PID control loop
 */
public class PID implements Runnable {

    /**
     * the proportional part's constant factor
     */
    private double kp;
    /**
     * the integral part's constant factor
     */
    private double ki;
    /**
     * the derivative part's constant factor
     */
    private double kd;
    /**
     * the current proportional part
     */
    private double p;
    /**
     * the current integral part
     */
    private double i;
    /**
     * the current derivative part
     */
    private double d;
    /**
     * the PID loop's target value
     */
    private double target;
    /**
     * the loop's previous error
     */
    private double prevError;
    /**
     * the loop's current error
     */
    private double error;
    /**
     * the time difference between iterations
     */
    private final long dt;
    /**
     * is the loop running?
     */
    private boolean running;
    /**
     * the tolerance when checking if the loop has finished
     */
    private final double tolerance;
    /**
     * the PID's input channel
     */
    private final PIDIn input;
    /**
     * the PID's output channel
     */
    private final PIDOut output;
    /**
     * the thread in which the PID runs
     */
    private Thread pidThread;

    /**
     * 
     * @param input the PID's input channel
     * @param output the PID's output channel
     * @param kp the proportional part's constant factor
     * @param ki the integral part's constant factor
     * @param kd the derivative part's constant factor
     * @param dt the time difference between iterations
     * @param tolerance the tolerance when checking if the loop has finished
     * @param target the PID loop's target value
     */
    public PID(PIDIn input, PIDOut output, double kp, double ki, double kd, long dt, double tolerance, double target) {
        this.dt = dt;
        this.kd = kd;
        this.ki = ki;
        this.kp = kp;
        this.tolerance = tolerance;
        this.target = target;
        this.input = input;
        this.output = output;
        running = true;
        pidThread = new Thread(this);
    }

    /**
     * @return is the PID loop running?
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * run the PID loop
     */
    public void run() {
        running = true;
        error = target - input.input();
        while (!(hasArrived()) && running) {
            System.out.println(error);
            prevError = error;
            error = target - input.input();
            p = kp * error;
            i += ki * (prevError + error) * dt / 2;
            d = kd * (error - prevError) / dt;
            output.output(p + i + d);
            try {
                Thread.sleep(dt);
            } catch (InterruptedException ex) {
                System.err.println("error!!!!!");
            }
        }
        output.output(0);
        stop();
    }

    /**
     * stop the PID loop
     */
    public void stop() {
        running = false;
        pidThread = new Thread(this);
    }

    /**
     * reset the PID
     */
    public void reset() {
        p = 0;
        i = 0;
        d = 0;
        error = 0;
        prevError = 0;
        running = false;
    }

    /**
     * @return has the loop arrived to its destination?
     */
    public boolean hasArrived() {
        return (Math.abs(error) <= tolerance);
    }

    /**
     * @return the PID loop's target value
     */
    public double getTarget() {
        return target;
    }

    /**
     * set the loop's target value
     * @param target the PID loop's new target value
     */
    public void setTarget(double target) {
        this.target = target;
    }

    /**
     * @return the proportional part's constant factor
     */
    public double getKp() {
        return kp;
    }

    /**
     * set the proportional part's constant factor
     * @param kp the proportional part's new constant factor
     */
    public void setKp(double kp) {
        this.kp = kp;
    }

    /**
     * @return the integral part's constant factor
     */
    public double getKi() {
        return ki;
    }

    /**
     * set the integral part's constant factor
     * @param ki the integral part's new constant factor
     */
    public void setKi(double ki) {
        this.ki = ki;
    }

    /**
     * @return the derivative part's constant factor
     */
    public double getKd() {
        return kd;
    }

    /**
     * set the derivative part's constant factor
     * @param kd the derivative part's new constant factor
     */
    public void setKd(double kd) {
        this.kd = kd;
    }

    /**
     * run the PID loop in a new Thread
     */
    public void start() {
        pidThread.start();
    }
}
