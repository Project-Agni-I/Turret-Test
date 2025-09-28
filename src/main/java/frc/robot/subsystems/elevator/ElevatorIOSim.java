package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ElevatorIOSim implements ElevatorIO {

	private double elevatorPosition = 0.0;
	private double manualSpeed = 0.0;
	private double targetPosition = 0.0;
	private boolean isMovingToTarget = false;

	public ElevatorIOSim() {
		elevatorPosition = ElevatorConstants.LEVEL_1_POSITION;
	}

	@Override
	public void updateInputs(ElevatorIOInputs inputs) {
		// Apply continuous manual movement
		if (manualSpeed != 0.0) {
			double oldPosition = elevatorPosition;
			elevatorPosition += manualSpeed * 0.1; // Slower movement (0.1 instead of 1.0)
			elevatorPosition = Math.max(elevatorPosition, 0.0); // Don't go below 0
			elevatorPosition = Math.min(elevatorPosition, 28.0); // Don't go above 28
			System.out.println("Manual movement: speed=" + manualSpeed + ", oldPos=" + oldPosition + ", newPos="
					+ elevatorPosition);
		}

		// Apply gradual movement to target position
		if (isMovingToTarget) {
			double difference = targetPosition - elevatorPosition;
			if (Math.abs(difference) > 0.1) {
				// Move towards target at 0.2 units per update
				double moveAmount = Math.signum(difference) * Math.min(0.2, Math.abs(difference));
				elevatorPosition += moveAmount;
				elevatorPosition = Math.max(elevatorPosition, 0.0);
				elevatorPosition = Math.min(elevatorPosition, 28.0);
				System.out.println("Moving to target: current=" + elevatorPosition + ", target=" + targetPosition);
			} else {
				// Close enough to target
				elevatorPosition = targetPosition;
				isMovingToTarget = false;
				System.out.println("Reached target position: " + targetPosition);
			}
		}

		inputs.position = elevatorPosition;
	}

	@Override
	public void setElevatorPosition(double wantedPosition) {
		manualSpeed = 0.0; // Stop manual movement
		targetPosition = Math.max(wantedPosition, 0.0);
		targetPosition = Math.min(targetPosition, 28.0); // Cap at 28
		isMovingToTarget = true; // Start gradual movement
		System.out.println("setElevatorPosition called with: " + wantedPosition + ", target: " + targetPosition);
	}

	@Override
	public void runManualUp() {
		manualSpeed = 1.0; // Set speed for continuous up movement
		System.out.println("runManualUp() called - setting speed to 1.0");
	}

	@Override
	public void stop() {
		manualSpeed = 0.0;
		System.out.println("stop() called - setting speed to 0.0, position stays at: " + elevatorPosition);
	}

	@Override
	public void runManualDown() {
		manualSpeed = -1.0; // Set speed for continuous down movement
		System.out.println("runManualDown() called - setting speed to -1.0");
	}
}