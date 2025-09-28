package frc.robot.subsystems.ball;

import org.littletonrobotics.junction.AutoLog;

public interface BallIO {
	@AutoLog
	public static class BallIOInputs {
		public double leftMotorSpeed = 0.0;
		public double rightMotorSpeed = 0.0;
	}

	public default void updateInputs(BallIOInputs inputs) {
	}

	public default void intake() {
	}

	public default void outtake() {
	}

	public default void stop() {
	}

	public default void shoot() {
	}
}
