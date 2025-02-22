package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {

	@AutoLog
	public static class IntakeIOInputs {
		// public double algaeMotorSpeed = 0.0;
		public double coralMotorSpeed = 0.0;
		public double topRollerMotorSpeed = 0.0;

		public boolean coralSensed = false;
		// public double algaeCurrent = 0.0;
	}

	public default void updateInputs(IntakeIOInputs inputs) {
	}

	public default void setSpeeds(double speed1, double speed2, double speed3) {
	}

}
