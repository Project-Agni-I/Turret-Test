package frc.robot.subsystems.ball;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class BallIOReal implements BallIO {
	private final TalonFX leftMotor = new TalonFX(BallConstants.MOTOR_BALL_LEFT);
	private final TalonFX rightMotor = new TalonFX(BallConstants.MOTOR_BALL_RIGHT);

	public BallIOReal() {
		leftMotor.setNeutralMode(NeutralModeValue.Brake);
		rightMotor.setNeutralMode(NeutralModeValue.Brake);
	}

	@Override
	public void updateInputs(BallIOInputs inputs) {
		inputs.leftMotorSpeed = leftMotor.get();
		inputs.rightMotorSpeed = rightMotor.get();
	}

	@Override
	public void intake() {
		leftMotor.set(-BallConstants.INTAKE_SPEED);
		rightMotor.set(BallConstants.INTAKE_SPEED);
	}

	@Override
	public void outtake() {
		leftMotor.set(BallConstants.OUTTAKE_SPEED);
		rightMotor.set(-BallConstants.OUTTAKE_SPEED);
	}

	@Override
	public void stop() {
		leftMotor.set(0.0);
		rightMotor.set(0.0);
	}

	@Override
	public void shoot() {
		leftMotor.set(BallConstants.OUTTAKE_SPEED);
		rightMotor.set(-BallConstants.OUTTAKE_SPEED);
	}
}
