package frc.robot.subsystems.pivot;

import edu.wpi.first.math.util.Units;

public final class PivotConstants {
	public static final double PIVOT_P = 29;
	public static final double PIVOT_I = 0;
	public static final double PIVOT_D = 0;

	public static final double PIVOT_kS = 0.4;
	public static final double PIVOT_kG = 0.67;
	public static final double PIVOT_kV = 0.2;
	public static final double PIVOT_kA = 0;

	public static final double PIVOT_HOME_POSITION = Units.degreesToRotations(55);

	public static final double PIVOT_GEAR_RATIO = 14;

	public static final int PIVOT_MOTOR_ID = 4;
}
