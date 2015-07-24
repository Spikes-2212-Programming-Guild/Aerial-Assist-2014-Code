package pid;

/**
 * an interface for output channels of PID loops
 *
 * @author asaf
 */
public interface PIDOut {
    
    /**
     * the output function for the PID loop
     * 
     * @param pid the PID loop's output value
     */
    public void output(double pid);
}