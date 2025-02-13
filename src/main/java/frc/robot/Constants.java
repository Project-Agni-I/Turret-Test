package frc.robot;

import com.ctre.phoenix6.hardware.Pigeon2;

import java.util.List;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.swerve.*;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.ClosedLoopOutputType;
import com.ctre.phoenix6.swerve.SwerveModuleConstants.SteerFeedbackType;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

	public static final Mode simMode = Mode.SIM;
	public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

	public static enum Mode {
		/** Running on a real robot. */
		REAL,

		/** Running a physics simulator. */
		SIM,

		/** Replaying from a log file. */
		REPLAY
	}

	public static final class ElevatorPosition {
		public static final double LEVEL_1_POSITION = 2;
		public static final double LEVEL_2_POSITION = 2;
		public static final double LEVEL_3_POSITION = 2;
		public static final double LEVEL_4_POSITION = 2;
		public static final double ELEVATOR_POSITION_TOLERANCE = 0;

	}

	public static final class MotorConstants {
		public static final int MOTOR_ELEVATOR_LEFT = 1;
		public static final int MOTOR_ELEVATOR_RIGHT = 2;
		public static final int MOTOR_ALGAE_ID = 3;
		public static final int MOTOR_COMBINATION_ID = 4;
		public static final int MOTOR_CORAL1_ID = 5;
		public static final int MOTOR_PIVOT_ID = 6;
		public static final int CLIMB_MOTOR_ID = 7;

	}

	public static final class CombinationConstants {
		public static final double COMBINATION_INTAKE_MOTOR_SPEED = .5;
		public static final double COMBINATION_STOP_MOTOR_SPEED = 0;
		public static final double COMBINATION_OUTTAKE_MOTOR_SPEED = -.5;
	}

	public static final class AlgaeConstants {
		public static final double ALGAE_INTAKE_MOTOR_SPEED = .5;
		public static final double ALGAE_STOP_MOTOR_SPEED = 0;
		public static final double ALGAE_OUTTAKE_MOTOR_SPEED = -.5;
	}

	public static final class CoralConstants {
		public static final double CORAL_INTAKE_MOTOR_SPEED = .5;
		public static final double CORAL_STOP_MOTOR_SPEED = 0;
		public static final double CORAL_OUTTAKE_MOTOR_SPEED = -.5;

	}

	public static final class PivotConstants {
		public static final double PIVOT_P = 29;
		public static final double PIVOT_I = 0;
		public static final double PIVOT_D = 0;

		public static final double PIVOT_kS = 0.4;
		public static final double PIVOT_kG = 0.67;
		public static final double PIVOT_kV = 0.2;
		public static final double PIVOT_kA = 0;

		public static final double PIVOT_HOME_POSITION = Units.degreesToRotations(55);

		public static final double PIVOT_GEAR_RATIO = 14;
	}

	public static final class OperatorConstants {
		public static final int DRIVER_CONTROLLER_PORT = 1;
		public static final int OPERATOR_CONTROLLER_PORT = 0;
	}

	public static final class ClimbConstants {
		public static final int CLIMB_P = 0;
		public static final int CLIMB_I = 0;
		public static final int CLIMB_D = 0;
		public static final int CLIMB_kG = 0;
		public static final int CLIMB_kA = 0;
		public static final int CLIMB_kV = 0;
		public static final int CLIMB_kS = 0;
		public static final int CLIMB_POS = 0;
		public static final int CLIMB_POS_RETRACT = 0;
		public static final double POSITION_TOLERANCE = 0;

	}

	public static final class VisionConstants {

		public static record CameraParams(
				String name,
				Transform3d transforms) {
		};

		public static final String[] cameraNames = {
				"FL",
				"FR",
				"BL",
				"BR"
		};

		public static final Transform3d[] vehicleToCameras = { // 10 deg yaw, 5 deg pitch
				new Transform3d(new Translation3d(Units.inchesToMeters(6.5), Units.inchesToMeters(11.75),
						Units.inchesToMeters(25.5)), new Rotation3d(0, 0, 0)), // Fl
				new Transform3d(new Translation3d(Units.inchesToMeters(6.5), Units.inchesToMeters(-11.75),
						Units.inchesToMeters(25.5)), new Rotation3d(0, 0, 0)), // FR
				new Transform3d(new Translation3d(Units.inchesToMeters(-13.5), Units.inchesToMeters(10), // BL
						Units.inchesToMeters(20.6666666666)), new Rotation3d(0, 0, Units.degreesToRadians(-180))),
				new Transform3d(new Translation3d(Units.inchesToMeters(-13.5), Units.inchesToMeters(-10), // BR
						Units.inchesToMeters(20.66666666)), new Rotation3d(0, 0, Units.degreesToRadians(-180)))
		};

		public static final List<CameraParams> cameras = List.of(
				new CameraParams(cameraNames[0], vehicleToCameras[2]),
				new CameraParams(cameraNames[3], vehicleToCameras[3]));

		public static AprilTagFieldLayout aprilTagLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

	}

}
