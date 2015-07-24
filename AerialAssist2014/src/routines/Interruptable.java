package routines;

/**
 * Represents a class that containing an autonomous function that can be interrupted
 *
 * @author Developer
 */
public interface Interruptable {
    
    /**
     * interrupt autonomous actions
     */
    public void interrupt();
}