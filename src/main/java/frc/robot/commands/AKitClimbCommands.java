package frc.robot.commands;

import frc.robot.subsystems.climb.Climb;
import frc.robot.subsystems.climb.ClimbConstants;
import edu.wpi.first.wpilibj2.command.Command;

public class AKitClimbCommands extends Command {
	private final Climb climbSubsystem;
	private final boolean climbUp;

	public AKitClimbCommands(Climb climbSubsystem, boolean climbUp) {
		this.climbSubsystem = climbSubsystem;
		this.climbUp = climbUp;
		addRequirements(climbSubsystem);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void execute() {
		if (climbUp) {
			climbSubsystem.getClimbIOReal().setClimbPosition(ClimbConstants.CLIMB_UP_SPEED);
		} else {
			climbSubsystem.getClimbIOReal().setClimbPosition(ClimbConstants.CLIMB_DOWN_SPEED);
		}
	}

	@Override
	public void end(boolean interrupted) {
		climbSubsystem.getClimbIOReal().setClimbPosition(0);
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
