package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Intake.IntakeState;

public class AKitIntakeCommands extends Command {
	private final Intake intake;
	private final IntakeState direction;

	public AKitIntakeCommands(Intake intake, IntakeState direction) {
		this.intake = intake;
		this.direction = direction;
		addRequirements(intake);
	}

	@Override
	public void initialize() {
		intake.setState(direction);
	}

	@Override
	public void end(boolean interrupted) {
		intake.setState(IntakeState.IDLE);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
