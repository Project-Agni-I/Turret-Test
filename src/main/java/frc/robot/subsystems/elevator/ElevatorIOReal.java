package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants;
import frc.robot.Constants.MotorConstants;

public class ElevatorIOReal implements ElevatorIO {

	private final TalonFX elevatorMotorLEFT;
	private final TalonFX elevatorMotorRIGHT;
	private final Encoder elevatorEncoder;
	private double targetPositionInRotations;

	public ElevatorIOReal() {
		elevatorMotorLEFT = new TalonFX(MotorConstants.MOTOR_ELEVATOR_LEFT);
		elevatorMotorRIGHT = new TalonFX(MotorConstants.MOTOR_ELEVATOR_RIGHT);
		elevatorEncoder = new Encoder(0, 1);
		targetPositionInRotations = 0.0;
	}

	public static double convertDegreesToRotations(double degrees) {
		return degrees / 360.0;
	}

	@Override
	public void setElevatorPosition(double wantedPosition) {
		targetPositionInRotations = convertDegreesToRotations(wantedPosition);

		PositionVoltage positionVoltageLEFT = new PositionVoltage(targetPositionInRotations);
		PositionVoltage positionVoltageRIGHT = new PositionVoltage(targetPositionInRotations);

		elevatorMotorLEFT.setControl(positionVoltageLEFT);
		elevatorMotorRIGHT.setControl(positionVoltageRIGHT);
	}

	@Override
	public double getElevatorPosition() {
		return elevatorEncoder.getDistance();
	}

	@Override
	public boolean isElevatorAtTarget() {
		double currentPosition = getElevatorPosition();
		double targetPositionInDegrees = targetPositionInRotations * 360.0;
		return Math.abs(
				currentPosition - targetPositionInDegrees) < Constants.ElevatorPosition.ELEVATOR_POSITION_TOLERANCE;
	}
}
