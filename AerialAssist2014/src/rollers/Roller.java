package rollers;

import consts.GeneralConsts;
import consts.RollerConsts;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import routines.Interruptable;

/**
 * the robot's rollers
 * 
 * @author Spikes
 */
public class Roller implements Interruptable {

    // Folding Movement State Constants
    /**
     * The Folders are currently folding
     */
    public static final byte FOLDING = 1;
    /**
     * The folders are currently unfolding
     */
    public static final byte UNFOLDING = -1;
    /**
     * the folders aren't moving
     */
    public static final byte STOPPED = 0;
    
    //Folding static State Constants
    /**
     * The folders are currently fully folded
     */
    public static final byte FOLDED = 1;
    /**
     * The folders are currently fully unfolded
     */
    public static final byte UNFOLDED = -1;
    /**
     * The folders are in the range between their folded state and their unfolded state
     */
    public static final byte MIDDLE = 0;
    
    
    /**
     * The limit switch indicating that the rollers are fully folded
     */
    private DigitalInput foldedLimitSwitch;
    /**
     * The micro switch indicating that the rollers are fully unfolded
     */
    private DigitalInput unfoldedMicroSwitch;
    /**
     * The gyro indicating the folders' angle
     */
    private GyroS foldingGyro;
    /**
     * the spike that controls the folders
     */
    private Relay foldingMotor;
    /**
     * the talon that controls the rollers
     */
    private Talon rollerMotor;
    /**
     * the thread where all the gyro recalibrate magic happens
     */
    private Thread resetGyroThread;
    /**
     * is the roller currently performing autonomous folding actions
     */
    private boolean autoFoldingOn;

    /**
     * A constructor that takes references to objects of the controllers and sensors
     * @param foldedLimitSwitch The limit switch indicating that the rollers are fully folded
     * @param unfoldedMicroSwitch The micro switch indicating that the rollers are fully unfolded
     * @param gyro The gyro indicating the folders' angle
     * @param foldingMotor the spike that controls the folders
     * @param rollerMotor the talon that controls the rollers
     * @param startingAngle the angle where the folders start
     */
    public Roller(final DigitalInput foldedLimitSwitch, final DigitalInput unfoldedMicroSwitch, final GyroS gyro,
            Relay foldingMotor, Talon rollerMotor,
            double startingAngle) {
        this.foldedLimitSwitch = foldedLimitSwitch;
        this.unfoldedMicroSwitch = unfoldedMicroSwitch;
        this.foldingGyro = gyro;
        this.foldingMotor = foldingMotor;
        this.rollerMotor = rollerMotor;
        autoFoldingOn = false;

        resetGyroThread = new Thread(new Runnable() {

            public void run() {
                while (true) {
                    if (isFolded()) {
                        gyro.reset(RollerConsts.FOLDED_ANGLE);
                    } else if (isUnfolded()) {
                        gyro.reset(RollerConsts.UNFOLDED_ANGLE);
                    }
                    try {
                        Thread.sleep(GeneralConsts.ITTERATION_DELAY);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        });


        resetGyroThread.start();
        gyro.reset(RollerConsts.FOLDED_ANGLE);
    }

    /**
     * A constructor that take the channels of the controllers and sensors as parameters
     * 
     * @param foldedLimitSwitchChanel The channel for the limit switch indicating that the rollers are fully folded
     * @param unfoldedMicroSwitchChannel The channel for the micro switch indicating that the rollers are fully unfolded
     * @param gyroChannel The channel for the gyro indicating the folders' angle
     * @param foldingMotorChannel the channel for the spike that controls the folders
     * @param rollerMotorChannel the channel for the talon that controls the rollers
     * @param startingAngle the angle where the folders start
     */
    public Roller(int foldedLimitSwitchChanel, int unfoldedMicroSwitchChannel, int gyroChannel, int foldingMotorChannel, int rollerMotorChannel, double startingAngle) {
        this(new DigitalInput(foldedLimitSwitchChanel), new DigitalInput(unfoldedMicroSwitchChannel),
                new GyroS(gyroChannel), new Relay(foldingMotorChannel),
                new Talon(rollerMotorChannel), startingAngle);
    }

    /**
     * @return are the folders fully folded
     */
    public boolean isFolded() {
        return !foldedLimitSwitch.get();
    }

    /**
     * @return are the folders fully unfolded 
     */
    public boolean isUnfolded() {
        return !unfoldedMicroSwitch.get();
    }

    /**
     * reset the gyro
     */
    public void resetGyro() {
        foldingGyro.reset();
    }
    /**
     * stops the folders
     */
    public void stopFolder() {
        if (isAutoFoldingOn()) {
            interrupt();
            try {
                Thread.sleep(GeneralConsts.ITTERATION_DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        stopFolderDontStopAuto();
    }

    /**
     * stop the folders without interrupting autonomous folding
     */
    private void stopFolderDontStopAuto() {
        foldingMotor.set(Relay.Value.kOff);
    }

    /**
     * @return the angle of the folders 
     */
    public double getAngle() {
        return foldingGyro.getAngle();
    }

    /**
     * @return the rollers' speed in the range of -1 to 1
     */
    public double getRollerSpeed() {
        return rollerMotor.get();
    }

    /**
     * @return the value of the constant representing the state, 15 means
     * something bad has happened.
     */
    public byte getFoldingMovementState() {
        if (foldingMotor.get() == Relay.Value.kOff) {
            return STOPPED;
        }
        if (foldingMotor.get() == Relay.Value.kForward) {
            return FOLDING;
        }
        if (foldingMotor.get() == Relay.Value.kReverse) {
            return UNFOLDING;
        }
        return 15;
    }

    /**
     * @returnare the folders folded, unfolded, or neither?
     */
    public byte getState() {
        if (isFolded()) {
            return FOLDED;
        }
        if (isUnfolded()) {
            return UNFOLDED;
        }
        return MIDDLE;
    }

    /**
     * @return the speed in which the motor that is responsible for the folding
     * of the rollers is in, using the gyro
     */
    public double getFoldingSpeedByGyro() {
        return foldingGyro.getRate();
    }

    /**
     * spin the rollers at a given speed
     * @param speed the speed to spin the rollers in (in the range between -1 and 1)
     */
    public void spinRollers(double speed) {
        rollerMotor.set(speed);
    }

    /**
     * stop the rollers
     */
    public void stopRollers() {
        spinRollers(0);
    }

    /**
     * move the folders paying no regard to the sensors.
     * @param value the value to pass to the spike
     */
    private void moveFoldersUnrestricted(Relay.Value value) {
        foldingMotor.set(value);
    }

    /**
     * move the folders regarding the sensors.
     * @param value the value to pass to the spike
     */
    private synchronized void moveFolders(Relay.Value value) {
        switch (getState()) {
            case FOLDED:
                if (value == Relay.Value.kForward) {
                    value = Relay.Value.kOff;
                }
                break;
            case UNFOLDED:
                if (value == Relay.Value.kReverse) {
                    value = Relay.Value.kOff;
                }
                break;
        }
        moveFoldersUnrestricted(value);
    }

    /**
     * determine the value the folders have to move by in order to get to the wanted angle, then set the spike accordingly
     * @param angle the target angle of this movement
     */
    private synchronized void determineRelayValue(double angle) {
        if (Math.abs(angle - foldingGyro.getAngle()) < RollerConsts.FOLDER_ANGLE_TOLERANCE) {
            stopFolderDontStopAuto();
        } else if (angle > foldingGyro.getAngle()) {
            foldDontStopAuto();
        } else {
            unfoldDontStopAuto();
        }
    }

    /**
     * fold the rollers until they reach the wanted angle. delays the thread that calls it.
     * @param angle the target angle of this movement
     */
    public void foldToAngle(double angle) {
        if (isAutoFoldingOn()) {
            interrupt();
            try {
                Thread.sleep(GeneralConsts.ITTERATION_DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        autoFoldingOn = true;
        while (Math.abs(angle - foldingGyro.getAngle()) > RollerConsts.FOLDER_ANGLE_TOLERANCE && autoFoldingOn) {
            determineRelayValue(angle);
            try {
                Thread.sleep(GeneralConsts.ITTERATION_DELAY);
            } catch (InterruptedException ex) {
            }
        }
        autoFoldingOn = false;
        moveFolders(Relay.Value.kOff);
    }

    /**
     * fold the folders
     */
    public void fold() {
        if (isAutoFoldingOn()) {
            interrupt();
            try {
                Thread.sleep(GeneralConsts.ITTERATION_DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        foldDontStopAuto();
    }

    /**
     * fold the folders without regard to the sensors
     */
    private void foldDontStopAuto() {
        moveFolders(Relay.Value.kForward);
    }

    /**
     * unfold the folders
     */
    public void unfold() {
        if (isAutoFoldingOn()) {
            interrupt();
            try {
                Thread.sleep(GeneralConsts.ITTERATION_DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        unfoldDontStopAuto();
    }

    /**
     * unfold the folders without regard to the sensors
     */
    private void unfoldDontStopAuto() {
        moveFolders(Relay.Value.kReverse);
    }

    /**
     * fold the folders until they're fully folded
     */
    public void foldToEnd() {
        if (isAutoFoldingOn()) {
            interrupt();
            try {
                Thread.sleep(GeneralConsts.ITTERATION_DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        autoFoldingOn = true;
        moveFolders(Relay.Value.kForward);
        while (!isFolded() && autoFoldingOn) {
            try {
                Thread.sleep(GeneralConsts.ITTERATION_DELAY);
            } catch (InterruptedException ex) {
            }
        }
        autoFoldingOn = false;
        moveFolders(Relay.Value.kOff);
    }

    /**
     * unfold the folders until they're fully unfolded
     */
    public void unfoldToEnd() {
        if (isAutoFoldingOn()) {
            interrupt();
            try {
                Thread.sleep(GeneralConsts.ITTERATION_DELAY);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        autoFoldingOn = true;
        moveFolders(Relay.Value.kReverse);
        while (!isUnfolded() && autoFoldingOn) {
            try {
                Thread.sleep(GeneralConsts.ITTERATION_DELAY);
            } catch (InterruptedException ex) {
            }
        }
        autoFoldingOn = false;
        moveFolders(Relay.Value.kOff);
    }

    /**
     * @return are the folders currently folding autonomously?
     */
    public boolean isAutoFoldingOn() {
        return autoFoldingOn;
    }

    /**
     * interrupt autonomous folding
     */
    public void interrupt() {
        autoFoldingOn = false;
    }
}