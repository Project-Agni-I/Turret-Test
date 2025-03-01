package frc.robot;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.fasterxml.jackson.databind.util.internal.PrivateMaxEntriesMap;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
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
import frc.robot.subsystems.climb.*;
import frc.robot.subsystems.drive.*;
import frc.robot.subsystems.elevator.*;
import frc.robot.subsystems.intake.*;
import frc.robot.subsystems.intake.Intake.IntakeState;
import frc.robot.subsystems.pivot.*;
import frc.robot.subsystems.vision.*;

public class RobotContainer {
	private final Drive drive;
	private final Climb climb;
	private final Elevator elevator;
	private final Intake intake;
	private final Pivot pivot;
	private double offset1 = 0, offset2 = 0;

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
				climb = new Climb(new ClimbIOReal());
				elevator = new Elevator(new ElevatorIOReal());
				intake = new Intake(new IntakeIOReal());
				pivot = new Pivot(new PivotIOReal());
				break;

			case SIM:
				drive = new Drive(
						new GyroIO() {
						},
						new ModuleIOSim(TunerConstants.FrontLeft),
						new ModuleIOSim(TunerConstants.FrontRight),
						new ModuleIOSim(TunerConstants.BackLeft),
						new ModuleIOSim(TunerConstants.BackRight));
				climb = new Climb(new ClimbIOSim());
				elevator = new Elevator(new ElevatorIOSim());
				intake = new Intake(new IntakeIOSim());
				pivot = new Pivot(new PivotIOSim());
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
				climb = new Climb(new ClimbIO() {
				});
				elevator = new Elevator(new ElevatorIO() {
				});
				intake = new Intake(new IntakeIO() {
				});
				pivot = new Pivot(new PivotIO() {
				});
		}

		NamedCommands.registerCommand("L4",
				Commands.sequence(
						Commands.parallel(
								elevator.setTargetPos(ElevatorConstants.LEVEL_4_POSITION),
								pivot.setTargetPos(PivotConstants.POSITION_4)),
						Commands.waitSeconds(1.5),
						Commands.race(
								intake.setState(IntakeState.EJECT_CORAL),
								Commands.waitSeconds(1)),
						Commands.parallel(
								elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION),
								pivot.setTargetPos(PivotConstants.PIVOT_HOME_POSITION)),
						Commands.waitSeconds(1.5)));
		NamedCommands.registerCommand("Algae Low",
				Commands.sequence(
						Commands.parallel(
								elevator.setTargetPos(ElevatorConstants.ALGAE_LOW),
								pivot.setTargetPos(PivotConstants.ALGAE_INTAKE),
								Commands.waitSeconds(1.5)),
						intake.setState(IntakeState.INTAKE_CORAL)));
		NamedCommands.registerCommand("Intake", Commands.race(
				intake.runManual(),
				Commands.waitSeconds(0.5)));
		NamedCommands.registerCommand("Algae Home", Commands.parallel(
				elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION),
				pivot.setTargetPos(PivotConstants.GROUND_INTAKE - 35)));
		NamedCommands.registerCommand("Algae Score",
				Commands.sequence(
						pivot.setTargetPos(PivotConstants.GROUND_INTAKE - 35),
						Commands.waitSeconds(1),
						Commands.race(
								intake.setState(IntakeState.OUTTAKE_CORAL),
								Commands.waitSeconds(1)),
						pivot.setTargetPos(PivotConstants.PIVOT_HOME_POSITION),
						Commands.waitSeconds(1)));

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
		// controller
		// .a()
		// .whileTrue(
		// AkitDriveCommands.joystickDriveAtAngle(
		// drive,
		// () -> -controller.getLeftY(),
		// () -> -controller.getLeftX(),
		// () -> new Rotation2d()));
		controller
				.rightBumper()
				.onTrue(
						Commands.runOnce(
								() -> drive.setPose(
										new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
								drive)
								.ignoringDisable(true));

		controller.a().whileTrue(
				intake.setState(IntakeState.INTAKE_CORAL));

		controller.b().whileTrue(
				intake.setState(IntakeState.EJECT_CORAL)).onFalse(
						Commands.parallel(
								intake.setState(IntakeState.IDLE),
								elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION),
								pivot.setTargetPos(PivotConstants.PIVOT_HOME_POSITION)));

		controller.x().whileTrue(
				intake.setState(IntakeState.EJECT_CORAL))
				.onFalse(Commands.parallel(
						elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION),
						pivot.setTargetPos(PivotConstants.ALGAE_HOLD)));
		controller.y().whileTrue(
				intake.setState(IntakeState.OUTTAKE_CORAL))
				.onFalse(
						Commands.parallel(
								intake.setState(IntakeState.IDLE),
								elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION),
								pivot.setTargetPos(PivotConstants.PIVOT_HOME_POSITION)));

		BUTTON_1.onTrue(
				Commands.parallel(
						elevator.setTargetPos(ElevatorConstants.LEVEL_1_POSITION),
						pivot.setTargetPos(PivotConstants.POSITION_1)));
		BUTTON_2.onTrue(
				Commands.parallel(
						elevator.setTargetPos(ElevatorConstants.LEVEL_2_POSITION),
						pivot.setTargetPos(PivotConstants.POSITION_2)));
		BUTTON_3.onTrue(
				Commands.parallel(
						elevator.setTargetPos(ElevatorConstants.LEVEL_3_POSITION),
						pivot.setTargetPos(PivotConstants.POSITION_3)));
		BUTTON_4.onTrue(
				Commands.parallel(
						elevator.setTargetPos(ElevatorConstants.LEVEL_4_POSITION),
						pivot.setTargetPos(PivotConstants.POSITION_4)));

		BUTTON_8.onTrue(
				Commands.parallel(
						elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION),
						pivot.setTargetPos(PivotConstants.PIVOT_HOME_POSITION)));

		BUTTON_16.onTrue(
				Commands.parallel(
						elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION),
						pivot.setTargetPos(PivotConstants.ALGAE_HOLD)));

		BUTTON_9.onTrue(pivot.setTargetPos(PivotConstants.GROUND_INTAKE));
		BUTTON_10.onTrue(pivot.setTargetPos(PivotConstants.GROUND_INTAKE - 35));
		BUTTON_11.onTrue(
				Commands.sequence(
						elevator.setTargetPos(ElevatorConstants.ALGAE_LOW),
						Commands.waitSeconds(0.5),
						pivot.setTargetPos(PivotConstants.ALGAE_INTAKE)));
		BUTTON_12.onTrue(
				Commands.sequence(
						elevator.setTargetPos(ElevatorConstants.ALGAE_HIGH),
						Commands.waitSeconds(0.5),
						pivot.setTargetPos(PivotConstants.ALGAE_INTAKE)));

		BUTTON_13.whileTrue(climb.runUp()).whileFalse(climb.stop());
		BUTTON_14.whileTrue(climb.runDown()).whileFalse(climb.stop());

		BUTTON_5.whileTrue(intake.setState(IntakeState.INTAKE_CORAL)).onFalse(intake.setState(IntakeState.IDLE));
		BUTTON_6.whileTrue(intake.setState(IntakeState.OUTTAKE_CORAL)).onFalse(intake.setState(IntakeState.IDLE));
		BUTTON_7.whileTrue(intake.setState(IntakeState.EJECT_CORAL)).onFalse(intake.setState(IntakeState.IDLE));
		BUTTON_15.onTrue(
				intake.setState(IntakeState.IDLE));

		controller.rightTrigger().whileTrue(
				new ReefBranchAlign(drive,
						new Transform2d(Units.inchesToMeters(-4.5), Units.inchesToMeters(2.25 + offset1),
								new Rotation2d()),
						() -> controller.getLeftY()));

		controller.leftTrigger().whileTrue(
				new ReefBranchAlign(drive,
						new Transform2d(Units.inchesToMeters(-4.5), Units.inchesToMeters(offset2 + 2.25),
								new Rotation2d()),
						() -> controller.getLeftY()));
		controller.leftBumper().onTrue(
				AkitDriveCommands.joystickDrive(
						drive,
						() -> -controller.getLeftY(),
						() -> -controller.getLeftX(),
						() -> -controller.getRightX()));
	}

	public Command getAutonomousCommand() {
		return autoChooser.get();
	}

	public void teleopInit() {
		final VisionLocalizer vision;
		switch (Constants.currentMode) {
			case REAL:
				vision = new VisionLocalizer(
						drive::addVisionMeasurement,
						new VisionIOPhotonReal(VisionConstants.cameraNames[0],
								VisionConstants.vehicleToCameras[0]),
						new VisionIOPhotonReal(VisionConstants.cameraNames[1],
								VisionConstants.vehicleToCameras[1]));
				break;

			case SIM:
				vision = new VisionLocalizer(
						drive::addVisionMeasurement,
						new VisionIOPhotonSim(VisionConstants.cameraNames[0],
								VisionConstants.vehicleToCameras[0],
								drive::getPose),
						new VisionIOPhotonSim(VisionConstants.cameraNames[0],
								VisionConstants.vehicleToCameras[0], drive::getPose));
				break;

			default:
				vision = new VisionLocalizer(drive::addVisionMeasurement, new VisionIO() {
				});
		}
		intake.setState(IntakeState.IDLE);
		elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION);
		pivot.setTargetPos(PivotConstants.PIVOT_HOME_POSITION);

		if (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Blue) {
			offset1 = 0.75;
			offset2 = 15;
		} else {
			offset1 = -0.75;
			offset2 = -15;
		}
	}

}
