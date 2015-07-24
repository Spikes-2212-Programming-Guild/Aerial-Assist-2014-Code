package consts;

import routines.*;

/**
 * the static instances of the routines
 */
public class RoutineConsts {
    
    public static final EndGameFoldUpRoutine END_GAME_FOLD_UP_ROUTINE = new EndGameFoldUpRoutine(RollerConsts.ROLLER, ShooterConsts.SHOOTER);
    public static final GyroBottomCalibrationRoutine GYRO_BOTTOM_CALIBRATION_ROUTINE = new GyroBottomCalibrationRoutine(RollerConsts.ROLLER);
    public static final GyroTopCalibrationRoutine GYRO_TOP_CALIBRATION_ROUTINE = new GyroTopCalibrationRoutine(RollerConsts.ROLLER);
    public static final FoldToAngleRoutine FOLD_TO_ANGLE_ROUTINE = new FoldToAngleRoutine(RollerConsts.ROLLER);
    public static final PassOverTheTopRoutine PASS_OVER_THE_TOP_ROUTINE = new PassOverTheTopRoutine(ShooterConsts.SHOOTER);
    public static final UnlockAndRecockRoutine UNLOCK_AND_RECOCK_ROUTINE = new UnlockAndRecockRoutine(ShooterConsts.SHOOTER);
    public static final WindToAngleRoutine WIND_TO_ANGLE_ROUTINE = new WindToAngleRoutine(ShooterConsts.SHOOTER);
    
}