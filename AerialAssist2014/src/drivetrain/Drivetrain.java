package drivetrain;

import consts.JoystickConsts;
import pid.PIDIn;
import pid.PIDOut;

/**
 * The drivetrain consists of two gearboxes, the left and the right.
 * @author Alon
 */
public class Drivetrain implements PIDIn, PIDOut {

    
    /**
     * The left gearbox
     */
    private final MonitoredGearbox leftGearbox;
    /**
     * The right gearbox
     */
    private final MonitoredGearbox rightGearbox;

    /**
     *
     * @param leftGearbox Left Gearbox
     * @param rightGearbox Right Gearbox
     */
    public Drivetrain(MonitoredGearbox leftGearbox, MonitoredGearbox rightGearbox) {
        this.leftGearbox = leftGearbox;
        this.rightGearbox = rightGearbox;
    }

    /**
     * Drive straight in speed
     *
     * @param speed movement speed in range of [-1.0,1.0]
     */
    public void straight(double speed) {
        twoSidesDrive(speed, speed);
    }

    /**
     * Rotate in speed
     *
     * @param speed rotation speed in range of [-1.0,1.0]
     */
    public void rotate(double speed) {
        twoSidesDrive(speed, -speed);
    }

    /**
     * Arcade drive. Use both movement value and rotation value to control the
     * robot.
     *
     * @param moveValue movement speed in range of [-1.0,1.0]
     * @param rotateValue rotation speed in range of [-1.0,1.0]
     */
    public void arcade(double moveValue, double rotateValue) {
        double leftMotorSpeed;
        double rightMotorSpeed;
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                rightMotorSpeed = -Math.max(-moveValue, rotateValue);
                leftMotorSpeed = moveValue + rotateValue;
            } else {
                rightMotorSpeed = moveValue - rotateValue;
                leftMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }
        twoSidesDrive(leftMotorSpeed, rightMotorSpeed);
    }

    /**
     * Stops the robot.
     */
    public void stop() {
        straight(0);
    }

    /**
     * Drives the robot given the speed of each side of the drive train.
     * Same as twoJoystickDrive().
     * 
     * @param leftSpeed The speed of the left gearbox
     * @param rightSpeed The speed of the right gearbox
     */
    public void twoSidesDrive(double leftSpeed, double rightSpeed) {
        setLeftSpeed(leftSpeed);
        setRightSpeed(rightSpeed);
    }

    /**
     * Drives the robot given the speed of each side of the drive train.
     * Same as twoSidesDrive().
     *
     * @param leftSpeed
     * @param rightSpeed
     */
    public void twoJoystickDrive(double leftSpeed, double rightSpeed) {
        twoSidesDrive(leftSpeed, rightSpeed);
    }

    /**
     * Set wanted speed to the left Gearbox.
     *
     * @param speed Wanted left speed
     */
    public void setLeftSpeed(double speed) {
        if (Math.abs(speed) > JoystickConsts.JOYSTICK_TOLERANCE) {
            leftGearbox.set(applyTolerance(-speed));
        }
        else {
            leftGearbox.set(0);
        }
    }

    /**
     * Set wanted speed to the right Gearbox.
     *
     * @param speed Right gearbox speed in range [-1.0,1.0]
     */
    public void setRightSpeed(double speed) {
        if (Math.abs(speed) > JoystickConsts.JOYSTICK_TOLERANCE) {
            rightGearbox.set(applyTolerance(speed));
        }
        else {
            rightGearbox.set(0);
        }
    }

    /**
     * apply the tolerance of the joysticks to the given value.
     * 
     * @param speed the drive speed
     * @return the drive speed after tolerance was applied
     */
    private static double applyTolerance(double speed) {
        if (Math.abs(speed) < JoystickConsts.JOYSTICK_TOLERANCE) {
            speed = 0;
        }
        return speed;
    }

    /**
     * an input method for a PID loop
     * @return the robot's position according to the encoders
     */
    public double input() {
        return (rightGearbox.getAngle() - leftGearbox.getAngle()) / 2;
    }

    /**
     * an output method for a PID loop.
     * @param pid a speed for the robot to drive straight in.
     */
    public void output(double pid) {
        straight(pid);
    }
}
