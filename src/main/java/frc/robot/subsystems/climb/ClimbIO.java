package frc.robot.subsystems.climb;

import org.littletonrobotics.junction.AutoLog;

public interface ClimbIO {

	@AutoLog
	public static class ClimbIOInputs {
		public double position = 0.0;
		public double voltage = 0.0;
	}

	public default void updateInputs(ClimbIOInputsAutoLogged inputs) {
	}

	public default void setClimbPosition(double wantedPosition) {
	}

}
