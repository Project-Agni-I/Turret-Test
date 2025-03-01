package frc.robot.subsystems.climb;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClimbIOSim implements ClimbIO {
	private double climbPosition;

	public ClimbIOSim() {

		climbPosition = 0.0;

	}

	@Override
	public void updateInputs(ClimbIOInputs inputs) {
		inputs.position = climbPosition;

	}

	@Override
	public void setClimbPosition(double position) {

		climbPosition = position;

	}
}
