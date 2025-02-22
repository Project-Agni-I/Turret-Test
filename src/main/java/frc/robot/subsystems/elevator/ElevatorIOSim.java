package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorIOSim implements ElevatorIO {

	private double elevatorPosition = 0.0;
	private double elevatorVoltage = 0.0;

	public ElevatorIOSim() {

		elevatorPosition = ElevatorConstants.LEVEL_1_POSITION;
		elevatorVoltage = 0.0;
	}

	public void updateInputs(ElevatorIOInputs inputs) {

		inputs.position = elevatorPosition;
		inputs.voltage = elevatorVoltage;
	}

	@Override
	public void setElevatorPosition(double wantedPosition) {

		elevatorPosition = Math.min(Math.max(wantedPosition, 0.0), ElevatorConstants.LEVEL_1_POSITION);

		elevatorVoltage = 0.5 * elevatorPosition;
	}
}