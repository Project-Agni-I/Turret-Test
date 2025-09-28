package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {

	@AutoLog
	public static class ElevatorIOInputs {
		public double position = 0.0;
	}

	public default void updateInputs(ElevatorIOInputs inputs) {
	}

	public default void setElevatorPosition(double wantedPosition) {
	}

	public default void runManualUp() {
	}

	public default void runManualDown() {
	}

	public default void stop() {
	}
}
