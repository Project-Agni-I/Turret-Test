package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.ElevatorConstants;

public class AKitElevatorCommands extends Command {
	private final Elevator elevatorSubsystem;
	private double targetPosition;

	public AKitElevatorCommands(Elevator elevatorSubsystem, double targetPosition) {
		this.elevatorSubsystem = elevatorSubsystem;
		this.targetPosition = targetPosition;
		addRequirements(elevatorSubsystem);
	}

	@Override
	public void initialize() {
		elevatorSubsystem.setTargetPos(targetPosition);
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	public void setStartPosition(double startPosition) {
		elevatorSubsystem.setTargetPos(startPosition);
	}

	public static AKitElevatorCommands toLevel1(Elevator elevatorSubsystem) {
		return new AKitElevatorCommands(elevatorSubsystem, ElevatorConstants.LEVEL_1_POSITION);
	}

	public static AKitElevatorCommands toLevel2(Elevator elevatorSubsystem) {
		return new AKitElevatorCommands(elevatorSubsystem, ElevatorConstants.LEVEL_2_POSITION);
	}

	public static AKitElevatorCommands toLevel3(Elevator elevatorSubsystem) {
		return new AKitElevatorCommands(elevatorSubsystem, ElevatorConstants.LEVEL_3_POSITION);
	}

	public static AKitElevatorCommands toLevel4(Elevator elevatorSubsystem) {
		return new AKitElevatorCommands(elevatorSubsystem, ElevatorConstants.LEVEL_4_POSITION);
	}
}
