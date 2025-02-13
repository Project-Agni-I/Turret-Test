package frc.robot.subsystems.climb;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants;
import frc.robot.Constants.MotorConstants;

public class ClimbIOReal implements ClimbIO {

	private final TalonFX climbMotor;
	private final Encoder climbEncoder;
	private double targetPositionInRotations;

	public ClimbIOReal() {
		climbMotor = new TalonFX(MotorConstants.CLIMB_MOTOR_ID);
		climbEncoder = new Encoder(0, 1);
		targetPositionInRotations = 0.0;
	}

	public static double convertDegreesToRotations(double degrees) {
		return degrees / 360.0;
	}

	@Override
	public void setClimbPosition(double wantedPosition) {
		targetPositionInRotations = convertDegreesToRotations(wantedPosition);
		PositionVoltage positionVoltage = new PositionVoltage(targetPositionInRotations);
		climbMotor.setControl(positionVoltage);
	}

	@Override
	public double getClimbPosition() {
		return climbEncoder.getDistance();
	}

	@Override
	public boolean isClimbAtTarget() {
		double currentPosition = getClimbPosition();
		double targetPositionInDegrees = targetPositionInRotations * 360.0;
		return Math.abs(currentPosition - targetPositionInDegrees) < Constants.ClimbConstants.POSITION_TOLERANCE;
	}
}
