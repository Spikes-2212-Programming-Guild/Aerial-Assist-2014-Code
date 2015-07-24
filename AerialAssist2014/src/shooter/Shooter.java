package shooter;

import consts.GeneralConsts;
import consts.ShooterConsts;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import routines.Interruptable;

/**
 *
 * @author BARAK
 */
public class Shooter implements Interruptable {

	/**
	 * a constant value meaning that the trigger is unlocked
	 */
	public static final byte UNLOCKED = -1;
	/**
	 * a constant value meaning that the trigger is locked
	 */
	public static final byte LOCKED = 1;
	/**
	 * a constant value meaning that the trigger is neither locked nor unlocked
	 */
	public static final byte MIDDLE = 0;

	/**
	 * the speed controller that controls the winder
	 */
	private final MonitoredSpeedcontroller windingMotor;
	/**
	 * the relay that controls the trigger that releases the kicker
	 */
	private final Relay trigger;
	/**
	 * the trigger's state: is it locked, unlocked or none of the above.
	 */
	private byte triggerState;
	/**
	 * the limit switch that tells if the trigger is locked
	 */
	private final DigitalInput lockLimitSwitch;
	/**
	 * the limit switch that tells if the trigger is unlocked
	 */
	private final DigitalInput unlockLimitSwitch;
	/**
	 * the micro switch that tells if the kicker is locked
	 */
	private DigitalInput cockedMicroSwitch;
	/**
	 * the thread that recalibrates the encoder
	 */
	private Thread resetThread;
	/**
	 * is the winder currently winding autonomously
	 */
	private boolean autoWindOn;

	/**
	 * @param windingSpeedController
	 *            the speed controller that controls the winder
	 * @param triggerMotor
	 *            the relay that controls the trigger that releases the kicker
	 * @param lockLimitSwitch
	 *            the limit switch that tells if the trigger is locked
	 * @param unlockLimitSwitch
	 *            the limit switch that tells if the trigger is unlocked
	 * @param cockedMicroSwitch
	 *            the micro switch that tells if the kicker is locked
	 */
	public Shooter(MonitoredSpeedcontroller windingSpeedController,
			Relay triggerMotor, DigitalInput lockLimitSwitch,
			DigitalInput unlockLimitSwitch, DigitalInput cockedMicroSwitch) {
		this.windingMotor = windingSpeedController;
		this.trigger = triggerMotor;
		this.lockLimitSwitch = lockLimitSwitch;
		this.unlockLimitSwitch = unlockLimitSwitch;
		this.triggerState = LOCKED;
		this.cockedMicroSwitch = cockedMicroSwitch;
		autoWindOn = false;
		resetThread = new Thread(new Runnable() {

			public void run() {
				while (true) {
					if (isCocked()) {
						windingMotor.resetEncoder();
					}
					try {
						Thread.sleep(GeneralConsts.ITTERATION_DELAY);
					} catch (Exception e) {
					}
				}
			}
		});
		resetThread.start();
	}

	public Shooter(int highMotorPort, int encoderPortA, int encoderPortB,
			int trigerPort, int lockLimitSwitchPort, int unlockLimitSwitchPort,
			int cockedMicroSwitchPort) {
		this(new MonitoredSpeedcontroller(new Talon(highMotorPort),
				new Encoder(encoderPortA, encoderPortB)),
				new Relay(trigerPort), new DigitalInput(lockLimitSwitchPort),
				new DigitalInput(unlockLimitSwitchPort), new DigitalInput(
						cockedMicroSwitchPort));
	}

	/**
	 * determine the speed the winder has to move in to reach the given angle
	 * 
	 * @param angle
	 *            the angle that the winder should reach
	 */
	private void determineWindVelocity(double angle) {
		byte direction;
		if (Math.abs(windingMotor.getAngle() - angle) < ShooterConsts.WIND_TOLERANCE) {
			direction = 0;
		} else if (windingMotor.getAngle() < angle) {
			direction = 1;
		} else {
			direction = -1;
		}
		windBySpeedDontStop(direction * ShooterConsts.WIND_SPEED);
	}

	/**
	 * move the winder in the given speed - don't regard the microswitch
	 * 
	 * @param speed
	 */
	private void windBySpeedDontStop(double speed) {
		windingMotor.set(speed);
	}

	/**
	 * stop the winder
	 */
	public void stopWinder() {
		windBySpeed(0);
	}

	/**
	 * wind to a given angle in a different Thread.
	 * 
	 * @param angle
	 */
	public void windToAngle(double angle) {
		if (isAutoWindOn()) {
			interrupt();
			try {
				Thread.sleep(GeneralConsts.ITTERATION_DELAY);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		autoWindOn = true;
		while (Math.abs(windingMotor.getAngle() - angle) > ShooterConsts.WIND_TOLERANCE
				&& autoWindOn) {
			determineWindVelocity(angle);
			try {
				Thread.sleep(GeneralConsts.ITTERATION_DELAY);
			} catch (InterruptedException ex) {
			}
		}
		windBySpeed(0);
		autoWindOn = false;
	}

	/**
	 * unwind the the shooter in a different thread
	 */
	public void unwind() {
		if (isAutoWindOn()) {
			interrupt();
			try {
				Thread.sleep(GeneralConsts.ITTERATION_DELAY);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		autoWindOn = true;
		if (windingMotor.getAngle() == 0) {
			while (!isCocked() && autoWindOn) {

				windBySpeed(-ShooterConsts.WIND_SPEED);
				try {
					Thread.sleep(GeneralConsts.ITTERATION_DELAY);
				} catch (InterruptedException ex) {
				}
			}
		} else {
			while (!isCocked() && autoWindOn) {
				determineWindVelocity(0);
				try {
					Thread.sleep(GeneralConsts.ITTERATION_DELAY);
				} catch (InterruptedException ex) {
				}
			}
		}
		try {
			Thread.sleep(ShooterConsts.WAIT_AT_THE_END_OF_UNWIND);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		windBySpeed(0);
		autoWindOn = false;
	}

	/**
	 * what is the winder's angle
	 * 
	 * @return the winder's angle
	 */
	public double getWindingAngle() {
		return windingMotor.getAngle();
	}

	/**
	 * unlock the trigger
	 */
	public void lock() {
		trigger.set(Relay.Value.kReverse);
		triggerState = MIDDLE;
		long startTime = System.currentTimeMillis();
		while (!lockLimitSwitch.get()
				&& (System.currentTimeMillis() - startTime) < ShooterConsts.LOCK_MAX_RUN_TIME_LOCK) {
			try {
				Thread.sleep(GeneralConsts.ITTERATION_DELAY);
			} catch (InterruptedException ex) {
			}
		}
		trigger.set(Relay.Value.kOff);
		triggerState = LOCKED;
	}

	/**
	 * lock the trigger
	 */
	public void unlock() {
		trigger.set(Relay.Value.kForward);
		triggerState = MIDDLE;
		long startTime = System.currentTimeMillis();
		while (!unlockLimitSwitch.get()
				&& (System.currentTimeMillis() - startTime) < ShooterConsts.LOCK_MAX_RUN_TIME_LOCK) {
			try {
				Thread.sleep(GeneralConsts.ITTERATION_DELAY);
			} catch (InterruptedException ex) {
			}
		}
		trigger.set(Relay.Value.kOff);
		triggerState = UNLOCKED;
	}

	/**
	 * is the trigger locked, unlocked, or neither
	 * 
	 * @return a byte representing the state of the trigger, the possible values
	 *         are constants
	 */
	public byte getTriggerState() {
		return triggerState;
	}

	/**
	 * @return is the kicker cocked?
	 */
	public boolean isCocked() {
		return !cockedMicroSwitch.get();
	}

	/**
	 * @return the angle of the winder
	 */
	public double getAngle() {
		return windingMotor.getAngle();
	}

	/**
	 * spin the winder at a given speed
	 * 
	 * @param speed
	 *            the speed to spin the winders
	 */
	public void windBySpeed(double speed) {
		if (isAutoWindOn()) {
			interrupt();
			try {
				Thread.sleep(GeneralConsts.ITTERATION_DELAY);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		windBySpeedDontStop(speed);
	}

	/**
	 * @return is the shooter winding autonomously?
	 */
	public boolean isAutoWindOn() {
		return autoWindOn;
	}

	/**
	 * interrupt autonomous winding
	 */
	public void interrupt() {
		autoWindOn = false;
	}

	/**
	 * release a blockage in the trigger
	 */
	public void unlockBlockage() {
		trigger.set(Relay.Value.kForward);
		triggerState = MIDDLE;
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < ShooterConsts.LOCK_MAX_RUN_TIME_LOCK) {
			try {
				Thread.sleep(GeneralConsts.ITTERATION_DELAY);
			} catch (InterruptedException ex) {
			}
		}
		trigger.set(Relay.Value.kOff);
		triggerState = UNLOCKED;
	}
}
