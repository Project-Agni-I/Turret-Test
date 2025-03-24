// package frc.robot;

// import frc.robot.subsystems.drive.Drive;
// import frc.robot.subsystems.elevator.Elevator;
// import frc.robot.subsystems.elevator.Elevator.ElevatorState;
// import frc.robot.subsystems.intake.Intake;
// import frc.robot.subsystems.pivot.Pivot;
// import frc.robot.subsystems.pivot.Pivot.PivotState;
// import frc.robot.subsystems.vision.VisionLocalizer;
// import frc.robot.subsystems.intake.Intake.IntakeState;
// import frc.robot.subsystems.elevator.ElevatorConstants;
// import frc.robot.subsystems.pivot.PivotConstants;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.Commands;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// public class RobotState {

// private final Drive drive;
// private final Elevator elevator;
// private final Intake intake;
// private final Pivot pivot;
// private final VisionLocalizer vision;

// private Pose2d robotPose;
// private ElevatorState elevatorState;
// private IntakeState intakeState;
// private PivotState pivotState;

// public RobotState(Drive drive, Elevator elevator, Intake intake, Pivot pivot,
// VisionLocalizer vision) {
// this.drive = drive;
// this.elevator = elevator;
// this.intake = intake;
// this.pivot = pivot;
// this.vision = vision;

// this.robotPose = new Pose2d();
// this.elevatorState = ElevatorState.LEVEL_1_POSITION;
// this.intakeState = IntakeState.IDLE;
// this.pivotState = PivotState.POSITION_1;
// }

// public void updateRobotState() {
// this.robotPose = drive.getPose();
// }

// public Pose2d getRobotPose() {
// return this.robotPose;
// }

// public ElevatorState getElevatorState() {
// return this.elevatorState;
// }

// public IntakeState getIntakeState() {
// return this.intakeState;
// }

// public PivotState getPivotState() {
// return this.pivotState;
// }

// public void setElevatorState(ElevatorState newState) {
// if (this.elevatorState != newState) {
// this.elevatorState = newState;
// Command elevatorCommand = Commands.runOnce(() -> {
// switch (newState) {
// case LEVEL_1_POSITION:
// elevator.setTargetPos(ElevatorConstants.LEVEL_1_POSITION);
// break;
// case LEVEL_2_POSITION:
// elevator.setTargetPos(ElevatorConstants.LEVEL_2_POSITION);
// break;
// case LEVEL_3_POSITION:
// elevator.setTargetPos(ElevatorConstants.LEVEL_3_POSITION);
// break;
// case LEVEL_4_POSITION:
// elevator.setTargetPos(ElevatorConstants.LEVEL_4_POSITION);
// break;
// case ELEVATOR_HOME_POSITION:
// elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION);
// break;
// default:
// break;
// }
// });

// elevatorCommand.schedule();
// }
// }

// public void setPivotState(PivotState newState) {
// if (this.pivotState != newState) {
// this.pivotState = newState;
// Command pivotCommand = Commands.runOnce(() -> {
// switch (newState) {
// case POSITION_1:
// pivot.setTargetPos(PivotConstants.POSITION_1);
// break;
// case POSITION_2:
// pivot.setTargetPos(PivotConstants.POSITION_2);
// break;
// case POSITION_3:
// pivot.setTargetPos(PivotConstants.POSITION_3);
// break;
// case POSITION_4:
// pivot.setTargetPos(PivotConstants.POSITION_4);
// break;
// case ALGAE_HOLD:
// pivot.setTargetPos(PivotConstants.ALGAE_HOLD);
// break;
// case ALGAE_INTAKE:
// pivot.setTargetPos(PivotConstants.ALGAE_INTAKE);
// break;
// default:
// break;
// }
// });

// pivotCommand.schedule();
// }
// }

// public void setIntakeState(IntakeState newState) {
// this.intakeState = newState;
// Command intakeCommand = Commands.runOnce(() -> intake.setState(newState));
// intakeCommand.schedule();
// }

// public void reset() {
// this.robotPose = new Pose2d();
// this.elevatorState = ElevatorState.ELEVATOR_HOME_POSITION;
// this.intakeState = IntakeState.IDLE;
// this.pivotState = PivotState.POSITION_1;

// elevator.setTargetPos(ElevatorConstants.ELEVATOR_HOME_POSITION);
// intake.setState(IntakeState.IDLE);
// pivot.setTargetPos(PivotConstants.POSITION_1);
// }

// public void startAutoCommandSequence() {
// SequentialCommandGroup autoCommandSequence = new SequentialCommandGroup(
// Commands.runOnce(() -> setElevatorState(ElevatorState.LEVEL_4_POSITION)),
// Commands.runOnce(() -> setPivotState(PivotState.POSITION_4)),
// Commands.runOnce(() -> setIntakeState(IntakeState.EJECT_CORAL)));

// autoCommandSequence.schedule();
// }
// }
