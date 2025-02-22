package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
		SmartDashboard.putNumber("elevator/position", inputs.position);
		SmartDashboard.putNumber("elevator/voltage", inputs.voltage);

		io.updateInputs(inputs);
		Logger.processInputs("elevator", inputs);

		io.setElevatorPosition(targetPosition);

		Logger.recordOutput("elevator/targetPos", targetPosition);
	}

	public void setTargetPos(double pos) {
		targetPosition = pos;
	}

}
