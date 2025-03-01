package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.elevator.ElevatorIO;
import frc.robot.subsystems.elevator.ElevatorIOInputsAutoLogged;

public class Elevator extends SubsystemBase {
	private final ElevatorIO io;
	private ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();
	private double targetPosition;

	public Elevator(ElevatorIO io) {
		this.io = io;
		targetPosition = 0.0;
	}

	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("elevator", inputs);

		io.setElevatorPosition(targetPosition);

		Logger.recordOutput("elevator/targetPos", targetPosition);
		Logger.recordOutput("elevator/currentPosition", inputs.position);
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
}