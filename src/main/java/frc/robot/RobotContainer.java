package frc.robot;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.VisionConstants;
import frc.robot.commands.AkitDriveCommands;
import frc.robot.commands.AKitClimbCommands;
import frc.robot.commands.AKitElevatorCommands;
import frc.robot.commands.AKitPivotCommands;
import frc.robot.subsystems.climb.Climb;
import frc.robot.subsystems.climb.ClimbIO;
import frc.robot.subsystems.climb.ClimbIOReal;
import frc.robot.subsystems.climb.ClimbIOSim;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.drive.TunerConstants;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.ElevatorConstants;
import frc.robot.subsystems.elevator.ElevatorIO;
import frc.robot.subsystems.elevator.ElevatorIOReal;
import frc.robot.subsystems.elevator.ElevatorIOSim;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.pivot.PivotConstants;
import frc.robot.subsystems.pivot.PivotIO;
import frc.robot.subsystems.pivot.PivotIOReal;
import frc.robot.subsystems.pivot.PivotIOSim;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOPhotonReal;
import frc.robot.subsystems.vision.VisionIOPhotonSim;
import frc.robot.subsystems.vision.VisionLocalizer;

public class RobotContainer {
	private final Drive drive;
	private final VisionLocalizer vision;
	private final Climb climb;
	private final Elevator elevator;
	// private final Intake intake;
	private final Pivot pivot;

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
		NamedCommands.registerCommand("Shoot 2", Commands.run(() -> System.out.println("Shoot")));
		switch (Constants.currentMode) {
			case REAL:
				drive = new Drive(
						new GyroIOPigeon2(),
						new ModuleIOTalonFX(TunerConstants.FrontLeft),
						new ModuleIOTalonFX(TunerConstants.FrontRight),
						new ModuleIOTalonFX(TunerConstants.BackLeft),
						new ModuleIOTalonFX(TunerConstants.BackRight));
				vision = new VisionLocalizer(
						drive::addVisionMeasurement,
						new VisionIOPhotonReal(VisionConstants.cameraNames[0], VisionConstants.vehicleToCameras[0]));
				climb = new Climb(new ClimbIOReal());
				elevator = new Elevator(new ElevatorIOReal());
				// intake = new Intake(new IntakeIOReal());
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
				vision = new VisionLocalizer(
						drive::addVisionMeasurement,
						new VisionIOPhotonSim(VisionConstants.cameraNames[0], VisionConstants.vehicleToCameras[0],
								drive::getPose));
				climb = new Climb(new ClimbIOSim());
				elevator = new Elevator(new ElevatorIOSim());
				// intake = new Intake(new IntakeIOSim());
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
				// intake = new Intake(new IntakeIO() {
				// });
				pivot = new Pivot(new PivotIO() {
				});
				vision = new VisionLocalizer(drive::addVisionMeasurement, new VisionIO() {
				});
		}

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
				.a()
				.whileTrue(
						AkitDriveCommands.joystickDriveAtAngle(
								drive,
								() -> -controller.getLeftY(),
								() -> -controller.getLeftX(),
								() -> new Rotation2d()));

		controller.x().onTrue(Commands.runOnce(drive::stopWithX, drive));
		controller.leftBumper().onTrue(new AKitElevatorCommands(elevator, ElevatorConstants.LEVEL_1_POSITION));
		controller
				.b()
				.onTrue(
						Commands.runOnce(
								() -> drive.setPose(
										new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
								drive)
								.ignoringDisable(true));

		// controller.rightTrigger().whileTrue(
		// intake.setState(IntakeState.INTAKE_ALGAE))
		// .whileFalse(intake.setState(IntakeState.IDLE));
		// Assuming BUTTON_1 and BUTTON_2 are your button objects
		BUTTON_1.whileTrue(new AKitClimbCommands(climb, true))

				.whileFalse(new AKitClimbCommands(climb, false));

		BUTTON_2.whileTrue(new AKitClimbCommands(climb, false))

				.whileFalse(new AKitClimbCommands(climb, true));
		BUTTON_3.onTrue(new AKitElevatorCommands(elevator, ElevatorConstants.LEVEL_1_POSITION));
		BUTTON_4.onTrue(new AKitElevatorCommands(elevator, ElevatorConstants.LEVEL_2_POSITION));
		BUTTON_5.onTrue(new AKitElevatorCommands(elevator, ElevatorConstants.LEVEL_3_POSITION));
		BUTTON_6.onTrue(new AKitElevatorCommands(elevator, ElevatorConstants.LEVEL_4_POSITION));
		BUTTON_7.onTrue(new AKitPivotCommands(pivot, PivotConstants.POSITION_1));
		BUTTON_8.onTrue(new AKitPivotCommands(pivot, PivotConstants.POSITION_2));
		BUTTON_9.onTrue(new AKitPivotCommands(pivot, PivotConstants.POSITION_3));
		BUTTON_10.onTrue(new AKitPivotCommands(pivot, PivotConstants.POSITION_4));
		BUTTON_11.onTrue(new AKitPivotCommands(pivot, PivotConstants.POSITION_5));
		BUTTON_12.onTrue(new AKitPivotCommands(pivot, PivotConstants.POSITION_6));
		// BUTTON_13.whileTrue(new AKitIntakeCommands(intake, IntakeState.INTAKE_CORAL))
		// .whileFalse(new AKitIntakeCommands(intake, IntakeState.IDLE));
		// BUTTON_14.whileTrue(new AKitIntakeCommands(intake, IntakeState.OUTTAKE))
		// .whileFalse(new AKitIntakeCommands(intake, IntakeState.IDLE));
		;

		controller.rightBumper().whileTrue(
				AkitDriveCommands.wheelRadiusCharacterization(drive));
	}

	public Command getAutonomousCommand() {
		return autoChooser.get();
	}

}
