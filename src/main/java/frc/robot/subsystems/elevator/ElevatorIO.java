package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {

	@AutoLog
	public static class ElevatorIOInputs {
		public double position = 0.0;
		public double voltage = 0.0;
	}

	public default void updateInputs(ElevatorIOInputsAutoLogged inputs) {
	}

	public default void setElevatorPosition(double wantedPosition) {
	}

}
