package frc.robot.commands;

import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.elevator.Elevator.ElevatorState;
import frc.robot.subsystems.elevator.*;
import frc.robot.subsystems.pivot.Pivot;
import frc.robot.subsystems.intake.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.vision.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;

public class AutoAlgaeAlign extends Command {

	private final Drive m_drivetrain;
	private final Elevator m_elevator;
	private final Pivot m_pivot;
	private final Intake m_intake;

	private boolean highAlgae = false;
	private boolean lowAlgae = false;

	private Pose2d m_goalPose;
	private Transform2d m_shift;

	private static final int[] HIGH_ALGAE_IDS = { 18, 20, 22, 7, 9, 11 };
	private static final int[] LOW_ALGAE_IDS = { 19, 17, 21, 8, 6, 10 };

	private double delayTime = 0.5;

	public AutoAlgaeAlign(Drive drivetrain, Elevator elevator, Pivot pivot, Intake intake, Transform2d shift) {
		m_drivetrain = drivetrain;
		m_elevator = elevator;
		m_pivot = pivot;
		m_intake = intake;
		m_shift = shift;
		addRequirements(drivetrain, elevator, pivot, intake);
	}

	@Override
	public void initialize() {

		ArrayList<Pose2d> alignPositionsHigh = new ArrayList<>();
		ArrayList<Pose2d> alignPositionsLow = new ArrayList<>();

		int[] a = { 6, 7, 8, 9, 10, 11,
				17, 18, 19, 20, 21, 22 };
		for (var tag : VisionConstants.aprilTagLayout.getTags()) {
			if (Arrays.stream(HIGH_ALGAE_IDS).anyMatch(x -> x == tag.ID)) {
				alignPositionsHigh.add(new Pose2d(tag.pose.toPose2d().getTranslation(),
						tag.pose.toPose2d().getRotation().rotateBy(Rotation2d.k180deg)));
			} else if (Arrays.stream(LOW_ALGAE_IDS).anyMatch(x -> x == tag.ID)) {
				alignPositionsLow.add(new Pose2d(tag.pose.toPose2d().getTranslation(),
						tag.pose.toPose2d().getRotation().rotateBy(Rotation2d.k180deg)));
			}
		}

		m_goalPose = m_drivetrain.findClosestNode();

		for (var tag : alignPositionsHigh) {
			if (m_goalPose.equals(tag)) {
				highAlgae = true;
			}
		}

		for (var tag : alignPositionsLow) {
			if (m_goalPose.equals(tag)) {
				lowAlgae = true;
			}
		}
		Logger.recordOutput("Drivetrain/DriveToPose/AlgaePose", m_goalPose);
		Logger.recordOutput("Drivetrain/DriveToPose/HighAlgae", highAlgae);
		Logger.recordOutput("Drivetrain/DriveToPose/LowAlgae", lowAlgae);

	}

	@Override
	public void execute() {
		if (highAlgae) {

			m_elevator.setStateNonCommand(ElevatorState.LEVEL_3_POSITION);
		} else if (lowAlgae) {

			m_elevator.setStateNonCommand(ElevatorState.LEVEL_2_POSITION);
		}

		m_intake.setState(Intake.IntakeState.INTAKE_ALGAE);

	}

	@Override
	public void end(boolean interrupted) {
		m_intake.setState(Intake.IntakeState.IDLE);
		highAlgae = false;
		lowAlgae = false;
	}

	@Override
	public boolean isFinished() {
		if (highAlgae || lowAlgae) {

			m_intake.intakeUntilAlgaeDetected(delayTime);
			return m_intake.isAlgaeDetected();
		}
		return false;
	}

	// private boolean isHighAlgaeTag(int tagID) {
	// for (int id : HIGH_ALGAE_IDS) {
	// if (tagID == id) {
	// return true;
	// }
	// }
	// return false;
	// }

	// private boolean isLowAlgaeTag(int tagID) {
	// for (int id : LOW_ALGAE_IDS) {
	// if (tagID == id) {
	// return true;
	// }
	// }
	// return false;
	// }
}
