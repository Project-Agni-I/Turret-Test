package frc.robot.subsystems.ball;

public class BallIOSim implements BallIO {
	private double left = 0.0;
	private double right = 0.0;

	@Override
	public void updateInputs(BallIOInputs inputs) {
		inputs.leftMotorSpeed = left;
		inputs.rightMotorSpeed = right;
	}

	@Override
	public void intake() {
		left = -BallConstants.INTAKE_SPEED;
		right = BallConstants.INTAKE_SPEED;
	}

	@Override
	public void outtake() {
		left = BallConstants.OUTTAKE_SPEED;
		right = -BallConstants.OUTTAKE_SPEED;
	}

	@Override
	public void stop() {
		left = 0.0;
		right = 0.0;
	}

	@Override
	public void shoot() {
		left = BallConstants.OUTTAKE_SPEED;
		right = -BallConstants.OUTTAKE_SPEED;
	}
}
