package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PivotIOSim implements PivotIO {
	private double pivotMotorSpeed;
	private double pivotPosition;

	public PivotIOSim() {
		pivotMotorSpeed = 0.0;
		pivotPosition = 0.0;

		SmartDashboard.putNumber("Pivot/Position", pivotPosition);
	}

	public void updateInputs(PivotIOInputs inputs) {
		inputs.pivotMotorSpeed = pivotMotorSpeed;
		inputs.pivotPosition = pivotPosition;

		SmartDashboard.putNumber("Pivot/Position", pivotPosition);
	}

	public void setPivotPosition(double position) {
		pivotPosition = position;

		pivotMotorSpeed = Math.signum(position) * 0.5;

	}

	public static class PivotIOInputs {
		public double pivotMotorSpeed;
		public double pivotPosition;
	}
}
