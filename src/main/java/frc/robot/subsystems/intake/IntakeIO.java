package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {

	@AutoLog
	public static class IntakeIOInputs {
		public double coralMotorSpeed = 0.0;

		public boolean coralSensed = false;
	}

	public default void updateInputs(IntakeIOInputs inputs) {
	}

	public default void setSpeeds(double speed1) {
	}

}
