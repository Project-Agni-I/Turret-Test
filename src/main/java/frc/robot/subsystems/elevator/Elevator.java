package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevator.ElevatorIO;

public class Elevator extends SubsystemBase {
	private final ElevatorIO io;
	private ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();
	private ElevatorState state = ElevatorState.ELEVATOR_HOME_POSITION;
	private double targetPosition;

	public Elevator(ElevatorIO io) {
		this.io = io;
		targetPosition = 0.0;
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("elevator", inputs);

		switch (state) {
			case LEVEL_1_POSITION:
				io.setElevatorPosition(ElevatorConstants.LEVEL_1_POSITION);
				break;
			case LEVEL_2_POSITION:
				io.setElevatorPosition(ElevatorConstants.LEVEL_2_POSITION);
				break;
			case LEVEL_3_POSITION:
				io.setElevatorPosition(ElevatorConstants.LEVEL_3_POSITION);
				break;
			case LEVEL_4_POSITION:
				io.setElevatorPosition(ElevatorConstants.LEVEL_4_POSITION);
				break;
			case ALGAE_LOW:
				io.setElevatorPosition(ElevatorConstants.ALGAE_LOW);
				break;
			case ALGAE_HIGH:
				io.setElevatorPosition(ElevatorConstants.ALGAE_HIGH);
				break;
			case ELEVATOR_HOME_POSITION:
				io.setElevatorPosition(ElevatorConstants.ELEVATOR_HOME_POSITION);
				break;
		}

		Logger.recordOutput("elevator/currentPosition", inputs.position);
		Logger.recordOutput("elevator/state", state);
		Logger.recordOutput("elevator/targetPosition", targetPosition);
	}

	public enum ElevatorState {
		LEVEL_1_POSITION, LEVEL_2_POSITION, LEVEL_3_POSITION, LEVEL_4_POSITION, ALGAE_LOW, ALGAE_HIGH, ELEVATOR_HOME_POSITION
	}

	public Command setState(ElevatorState newState) {
		return Commands.runOnce(() -> this.state = newState);
	}

	public void setStateNonCommand(ElevatorState newState) {
		this.state = newState;
	}

	public Command setTargetPos(double pos) {
		return Commands.runOnce(() -> targetPosition = pos);
	}

	public Command runUp() {
		return Commands.runEnd(() -> io.runManualUp(), () -> io.stop());
	}

	public Command runDown() {
		return Commands.runEnd(() -> io.runManualDown(), () -> io.stop());
	}

	public Command stop() {
		return Commands.runEnd(() -> io.stop(), () -> io.stop());
	}

	public double getTargetPose() {
		return targetPosition;
	}

	public ElevatorState getState() {
		return state;
	}

	public double getTargetPosition() {
		return targetPosition;
	}

}
