package frc.robot.subsystems.climb;

import org.littletonrobotics.junction.AutoLog;

public interface ClimbIO {

	@AutoLog
	public static class ClimbIOInputs {
		public double position = ClimbConstants.CLIMB_HOME_POSITION;

	}

	public default void updateInputs(ClimbIOInputs inputs) {
	}

	public default void setClimbPosition(double wantedPosition) {
	}

	public default void runManualUp() {
	}

	public default void runManualDown() {
	}

	public default void stop() {
	}
}
