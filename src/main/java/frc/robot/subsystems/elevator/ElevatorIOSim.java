package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorIOSim implements ElevatorIO {

	private double elevatorPosition = 0.0;

	public ElevatorIOSim() {
		elevatorPosition = ElevatorConstants.LEVEL_1_POSITION;
	}

	@Override
	public void updateInputs(ElevatorIOInputs inputs) {
		inputs.position = elevatorPosition;
	}

	@Override
	public void setElevatorPosition(double wantedPosition) {
		elevatorPosition = Math.max(wantedPosition, 0.0);
	}

	@Override
	public void runManualUp() {
		elevatorPosition += (2 / 12.375);
	}

	@Override
	public void stop() {

	}

	@Override
	public void runManualDown() {
		elevatorPosition -= (2 / 12.375);
	}
}