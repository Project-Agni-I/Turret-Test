package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorPosition;
import frc.robot.Constants.MotorConstants;

public class Elevator extends SubsystemBase {
	private TalonFX elevatorMotorLEFT, elevatorMotorRIGHT;
	private Encoder elevatorEncoder;
	private PIDController elevatorPIDController;
	private PositionVoltage position;

	private double targetPosition;

	public Elevator(ElevatorIO elevatorIO) {
		elevatorMotorLEFT = new TalonFX(MotorConstants.MOTOR_ELEVATOR_LEFT);
		elevatorMotorRIGHT = new TalonFX(MotorConstants.MOTOR_ELEVATOR_RIGHT);
		elevatorEncoder = new Encoder(0, 1);
		elevatorPIDController = new PIDController(0.1, 0.0, 0.0);
		targetPosition = ElevatorPosition.LEVEL_1_POSITION;
		position = new PositionVoltage(0);

		Logger.start();
		Logger.recordOutput("Elevator/TargetPosition", targetPosition);

	}

	public Command moveElevator(double pos) {
		return Commands.runOnce(() -> {
			Logger.recordOutput("ElevatorMotorLeft/MoveToPosition", pos);
			elevatorMotorLEFT.setControl(position.withPosition(pos));
			Logger.recordOutput("ElevatorMotorRight/MoveToPosition", pos);
			elevatorMotorRIGHT.setControl(position.withPosition(pos));
			Logger.recordOutput("Elevator/EncoderPosition", elevatorEncoder.getDistance());

		});

	}

	@Override

	public void periodic() {
		Logger.recordOutput("Elevator/CurrentPosition", elevatorEncoder.getDistance());
		Logger.recordOutput("Elevator/Speed", elevatorEncoder.getRate());

	}

}
