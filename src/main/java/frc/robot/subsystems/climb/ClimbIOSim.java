package frc.robot.subsystems.climb;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClimbIOSim implements ClimbIO {
	private double climbPosition;

	public ClimbIOSim() {

		climbPosition = 0.0;

		SmartDashboard.putNumber("Climb/Position", climbPosition);
	}

	public void updateInputs(ClimbIOInputs inputs) {
		inputs.climbPosition = climbPosition;

		SmartDashboard.putNumber("Climb/Position", climbPosition);
	}

	public void setClimbPosition(double position) {

		climbPosition = position;

	}

	public class ClimbIOInputs {

		public double climbPosition;

	}
}
