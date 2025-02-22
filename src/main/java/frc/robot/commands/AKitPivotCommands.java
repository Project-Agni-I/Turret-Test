package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.pivot.Pivot;

public class AKitPivotCommands extends Command {
	private final Pivot pivotSubsystem;
	private final double targetPosition;

	public AKitPivotCommands(Pivot pivotSubsystem, double targetPosition) {
		this.pivotSubsystem = pivotSubsystem;
		this.targetPosition = targetPosition;
		addRequirements(pivotSubsystem);
	}

	@Override
	public void initialize() {
		pivotSubsystem.setTargetPos(targetPosition);
	}

	@Override
	public void execute() {
	}

	@Override
	public boolean isFinished() {
		return Math.abs(pivotSubsystem.getPosition() - targetPosition) < 0.01;
	}

	@Override
	public void end(boolean interrupted) {
	}
}
