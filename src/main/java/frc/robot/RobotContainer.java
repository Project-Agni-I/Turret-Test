package frc.robot;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.VisionConstants;
import frc.robot.commands.AkitDriveCommands;
import frc.robot.subsystems.Pivot.Pivot;
import frc.robot.subsystems.Pivot.PivotIOReal;
import frc.robot.subsystems.Pivot.PivotIOSim;
import frc.robot.subsystems.climb.Climb;
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
import frc.robot.subsystems.elevator.ElevatorIOReal;
import frc.robot.subsystems.elevator.ElevatorIOSim;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIOReal;
import frc.robot.subsystems.intake.IntakeIOSim;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOPhotonReal;
import frc.robot.subsystems.vision.VisionIOPhotonSim;
import frc.robot.subsystems.vision.VisionLocalizer;

public class RobotContainer {
	private final Drive drive;
	private final VisionLocalizer vision;
	// private final Climb climb;
	// private final Elevator elevator;
	// private final Intake intake;
	// private final Pivot pivot;

	private final CommandXboxController controller = new CommandXboxController(0);

	private final LoggedDashboardChooser<Command> autoChooser;

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
				// climb = new Climb(new ClimbIOReal());
				// elevator = new Elevator(new ElevatorIOReal());
				// intake = new Intake(new IntakeIOReal());
				// pivot = new Pivot(new PivotIOReal());
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
				// climb = new Climb(new ClimbIOSim());
				// elevator = new Elevator(new ElevatorIOSim());
				// intake = new Intake(new IntakeIOSim());
				// pivot = new Pivot(new PivotIOSim());
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
				vision = new VisionLocalizer(drive::addVisionMeasurement, new VisionIO() {
				});
				// climb = new Climb(new ClimbIOReal());
				// elevator = new Elevator(new ElevatorIOReal());
				// intake = new Intake(new IntakeIOReal());
				// pivot = new Pivot(new PivotIOReal());
				break;
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

		controller
				.b()
				.onTrue(
						Commands.runOnce(
								() -> drive.setPose(
										new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
								drive)
								.ignoringDisable(true));

		controller.rightTrigger().whileTrue(
				AkitDriveCommands.feedforwardCharacterization(drive));

		controller.rightBumper().whileTrue(
				AkitDriveCommands.wheelRadiusCharacterization(drive));
	}

	public Command getAutonomousCommand() {
		return autoChooser.get();
	}
}
