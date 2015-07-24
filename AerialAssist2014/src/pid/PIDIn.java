package pid;

/**
 * an interface for input channels of PID loops
 */
public interface PIDIn {
    
    /**
     * the input function for the PID loop
     * 
     * @return the PID loop's input value
     */
    public double input();
}