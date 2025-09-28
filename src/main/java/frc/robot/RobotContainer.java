
package frc.robot;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.fasterxml.jackson.databind.util.internal.PrivateMaxEntriesMap;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.drive.*;
import frc.robot.subsystems.elevator.*;
import frc.robot.subsystems.elevator.Elevator.ElevatorState;
// import frc.robot.subsystems.pivot.*;
// import frc.robot.subsystems.pivot.Pivot.PivotState;
import frc.robot.subsystems.vision.*;

public class RobotContainer {
	private final Drive drive;
	// private final Climb climb;
	private final Elevator elevator;

	// private final Pivot pivot;
	private final VisionLocalizer vision;

	private final CommandXboxController controller = new CommandXboxController(0);
	public static final Joystick operatorControl = new Joystick(Constants.OperatorConstants.OPERATOR_CONTROLLER_PORT);
	private final LoggedDashboardChooser<Command> autoChooser;

	public static final JoystickButton BUTTON_1 = new JoystickButton(operatorControl, 1),
			BUTTON_2 = new JoystickButton(operatorControl, 2),
			BUTTON_3 = new JoystickButton(operatorControl, 3),
			BUTTON_4 = new JoystickButton(operatorControl, 4),
			BUTTON_5 = new JoystickButton(operatorControl, 5),
			BUTTON_6 = new JoystickButton(operatorControl, 6),
			BUTTON_7 = new JoystickButton(operatorControl, 7),
			BUTTON_8 = new JoystickButton(operatorControl, 8),
			BUTTON_9 = new JoystickButton(operatorControl, 9),
			BUTTON_10 = new JoystickButton(operatorControl, 10),
			BUTTON_11 = new JoystickButton(operatorControl, 11),
			BUTTON_12 = new JoystickButton(operatorControl, 12),
			BUTTON_13 = new JoystickButton(operatorControl, 13),
			BUTTON_14 = new JoystickButton(operatorControl, 14),
			BUTTON_15 = new JoystickButton(operatorControl, 15),
			BUTTON_16 = new JoystickButton(operatorControl, 16);

	public RobotContainer() {
		switch (Constants.currentMode) {
			case REAL:
				drive = new Drive(
						new GyroIOPigeon2(),
						new ModuleIOTalonFX(TunerConstants.FrontLeft),
						new ModuleIOTalonFX(TunerConstants.FrontRight),
						new ModuleIOTalonFX(TunerConstants.BackLeft),
						new ModuleIOTalonFX(TunerConstants.BackRight));
				// climb = new Climb(new ClimbIOReal());
				elevator = new Elevator(new ElevatorIOReal());

				// pivot = new Pivot(new PivotIOReal());
				vision = new VisionLocalizer(drive::addVisionMeasurement, drive,
						new VisionIOPhotonReal(VisionConstants.cameraNames[0], VisionConstants.vehicleToCameras[0]),
						new VisionIOPhotonReal(VisionConstants.cameraNames[1], VisionConstants.vehicleToCameras[1]));
				break;

			case SIM:
				drive = new Drive(
						new GyroIO() {
						},
						new ModuleIOSim(TunerConstants.FrontLeft),
						new ModuleIOSim(TunerConstants.FrontRight),
						new ModuleIOSim(TunerConstants.BackLeft),
						new ModuleIOSim(TunerConstants.BackRight));
				// climb = new Climb(new ClimbIOSim());
				elevator = new Elevator(new ElevatorIOSim());

				// pivot = new Pivot(new PivotIOSim());
				vision = new VisionLocalizer(
						drive::addVisionMeasurement,
						drive,
						new VisionIOPhotonSim(VisionConstants.cameraNames[0],
								VisionConstants.vehicleToCameras[0],
								drive::getPose),
						new VisionIOPhotonSim(VisionConstants.cameraNames[1],
								VisionConstants.vehicleToCameras[1], drive::getPose));
				break;

			default:
				drive = new Drive(
						new GyroIO() {
						},
						new ModuleIO() {
						},
						new ModuleIO() {
						},
						new ModuleIO() {
						},
						new ModuleIO() {
						});
				// climb = new Climb(new ClimbIO() {});
				elevator = new Elevator(new ElevatorIO() {
				});

				// pivot = new Pivot(new PivotIO() {});
				vision = new VisionLocalizer(drive::addVisionMeasurement, drive, new VisionIO() {
				});
		}

		vision.setVisionConsumer(drive::addVisionMeasurement);

		// ... NamedCommands registration (unchanged)

		autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

		autoChooser.addOption("Drive Wheel Radius Characterization",
				AkitDriveCommands.wheelRadiusCharacterization(drive));
		autoChooser.addOption("Drive Simple FF Characterization", AkitDriveCommands.feedforwardCharacterization(drive));

		configureButtonBindings();
	}

	private void configureButtonBindings() {
		drive.setDefaultCommand(
				AkitDriveCommands.joystickDrive(
						drive,
						() -> -controller.getLeftY(),
						() -> -controller.getLeftX(),
						() -> -controller.getRightX()));

		controller
				.rightBumper()
				.onTrue(
						Commands.runOnce(
								() -> drive.setPose(
										new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
								drive)
								.ignoringDisable(true));

		BUTTON_1.onTrue(
				Commands.parallel(
						elevator.setTargetPos(ElevatorConstants.LEVEL_1_POSITION)));

		BUTTON_2.onTrue(
				Commands.sequence(
						elevator.setTargetPos(ElevatorConstants.LEVEL_2_POSITION),
						Commands.waitSeconds(0.15)));

		BUTTON_3.onTrue(
				Commands.sequence(
						elevator.setTargetPos(ElevatorConstants.LEVEL_3_POSITION),
						Commands.waitSeconds(0.4)));

		BUTTON_4.onTrue(
				Commands.sequence(
						elevator.setTargetPos(ElevatorConstants.LEVEL_4_POSITION),
						Commands.waitSeconds(0.75)));

		BUTTON_8.onTrue(
				Commands.parallel(
						elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION)));

		BUTTON_16.onTrue(
				Commands.parallel(
						elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION)));

		BUTTON_11.onTrue(
				Commands.sequence(
						elevator.setTargetPos(ElevatorConstants.ALGAE_LOW),
						Commands.waitSeconds(0.5)));
		// pivot.setTargetPos(PivotConstants.ALGAE_INTAKE)));

		BUTTON_12.onTrue(
				Commands.sequence(
						elevator.setTargetPos(ElevatorConstants.ALGAE_HIGH),
						Commands.waitSeconds(0.5)));
	}

	public Command getAutonomousCommand() {
		return autoChooser.get();
	}

	public void teleopInit() {
		// Initialize subsystems for teleop mode
		// Since intake was removed, we don't need to set its state
		// pivot.setTargetPos(PivotConstants.POSITION_1);
	}
}