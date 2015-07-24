package org.usfirst.frc.team2212.robot;

import joystick.JoystickS;
import pid.PID;
import rollers.Roller;
import routines.Routine;
import shooter.Shooter;
import consts.DrivetrainConsts;
import consts.JoystickConsts;
import consts.RollerConsts;
import consts.RoutineConsts;
import consts.ShooterConsts;
import drivetrain.Drivetrain;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	Drivetrain drivetrain = DrivetrainConsts.DRIVETRAIN;
	Shooter shooter = ShooterConsts.SHOOTER;
	Roller roller = RollerConsts.ROLLER;
	JoystickS navJoystick = JoystickConsts.NAVIGATOR_JOYSTICK;
	JoystickS leftDriveJoystick = JoystickConsts.DRIVER_LEFT_JOYSTICK;
	JoystickS rightDriveJoystick = JoystickConsts.DRIVER_RIGHT_JOYSTICK;
	PID drivePID = DrivetrainConsts.DRIVE_PID;

	public void autonomousInit() {
		drivePID.run();
		shooter.windToAngle(ShooterConsts.MAX_WIND_ANGLE);
		RoutineConsts.UNLOCK_AND_RECOCK_ROUTINE.start(false);
	}

	public void teleopInit() {
		Routine.stopRoutine();
		drivetrain.stop();
	}

	public void disabledInit() {
		Routine.stopRoutine();
		drivetrain.stop();
	}

	public void teleopPeriodic() {
		// Driver
		if (rightDriveJoystick
				.getRawButton(JoystickConsts.DriverButtons.STRAIGHT_BUTTON)) {
			drivetrain.straight(rightDriveJoystick.getY());
		} else if (rightDriveJoystick
				.getRawButton(JoystickConsts.DriverButtons.ROTATE_BUTTON)) {
			drivetrain.rotate(rightDriveJoystick.getX());
		} else {
			drivetrain.twoJoystickDrive(leftDriveJoystick.getY(),
					rightDriveJoystick.getY());
		}

		// Navigator
		// roller spinning
		if (navJoystick
				.getRawButton(JoystickConsts.NavigatorButtons.ROLLERS_INTAKE_SPIN_BUTTON)) {
			roller.spinRollers(RollerConsts.INTAKE_SPEED);
		} else if (navJoystick
				.getRawButton(JoystickConsts.NavigatorButtons.ROLLERS_SPIN_OUTWARD_BUTTON)) {
			roller.spinRollers(-RollerConsts.INTAKE_SPEED);
		} else {
			roller.spinRollers(0);
		}

		// roller folding
		if (navJoystick
				.getRawButton(JoystickConsts.NavigatorButtons.MOVE_FOLDERS_BUTTON)) {
			if (navJoystick.getY() > JoystickConsts.JOYSTICK_TOLERANCE) {
				roller.unfold();
			} else if (navJoystick.getY() < -JoystickConsts.JOYSTICK_TOLERANCE) {
				roller.fold();
			} else {
				roller.stopFolder();
			}
		} else if (!roller.isAutoFoldingOn()) {
			roller.stopFolder();
		}

		// shooter winding
		if (navJoystick
				.getRawButton(JoystickConsts.NavigatorButtons.MOVE_WINDERS_BUTTON)) {
			if (Math.abs(navJoystick.getY()) > JoystickConsts.JOYSTICK_TOLERANCE) {
				shooter.windBySpeed(navJoystick.getY());
			} else {
				shooter.windBySpeed(0);
			}
		} else if (!shooter.isAutoWindOn()) {
			shooter.stopWinder();
		}

		// shooter locking and unlocking
		if (navJoystick
				.getRawButtonFirstTime(JoystickConsts.NavigatorButtons.RELEASE_BLOCKAGE_BUTTON)) {
			shooter.unlockBlockage();
		} else if (navJoystick
				.getRawButtonFirstTime(JoystickConsts.NavigatorButtons.LOCK_BUTTON)) {
			shooter.lock();
		} else if (navJoystick
				.getRawButtonFirstTime(JoystickConsts.NavigatorButtons.UNLOCK_BUTTON)) {
			shooter.unlock();
		}

		// routines
		if (navJoystick
				.getRawButtonFirstTime(JoystickConsts.NavigatorButtons.PASS_OVER_THE_TOP_BUTTON)) {
			RoutineConsts.PASS_OVER_THE_TOP_ROUTINE.start(true);
		} else if (navJoystick
				.getRawButtonFirstTime(JoystickConsts.NavigatorButtons.SHOOT_AND_RECOCK_BUTTON)) {
			RoutineConsts.UNLOCK_AND_RECOCK_ROUTINE.start(true);
		} else if (navJoystick
				.getRawButtonFirstTime(JoystickConsts.NavigatorButtons.WIND_TO_ANGLE_BUTTON)) {
			RoutineConsts.WIND_TO_ANGLE_ROUTINE.start(
					ShooterConsts.MAX_WIND_ANGLE, true);
		} else if (navJoystick
				.getRawButtonFirstTime(JoystickConsts.NavigatorButtons.GOAL_SHOOT_FOLD)) {
			RoutineConsts.FOLD_TO_ANGLE_ROUTINE.start(
					RollerConsts.HIGH_GOAL_SHOOT_FOLD_ANGLE, true);
		} else if (navJoystick
				.getRawButtonFirstTime(JoystickConsts.NavigatorButtons.TRUSS_SHOOT_FOLD)) {
			RoutineConsts.FOLD_TO_ANGLE_ROUTINE.start(
					RollerConsts.TRUSS_PASS_FOLD_ANGLE, true);
		}
	}

	public void testInit() {
		RoutineConsts.END_GAME_FOLD_UP_ROUTINE.start(true);
	}
}
