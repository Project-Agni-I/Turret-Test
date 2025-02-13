package frc.robot.subsystems.climb;

import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Constants;

public class ClimbIOSim implements ClimbIO {

	private final EncoderSim climbEncoderSim;

	public ClimbIOSim() {
		climbEncoderSim = new EncoderSim(new Encoder(0, 1));
	}

	public void setClimbPosition(double position) {
		climbEncoderSim.setDistance(position);
	}

	public double getClimbPosition() {
		return climbEncoderSim.getDistance();
	}

	public boolean isClimbAtTarget() {
		return Math.abs(climbEncoderSim.getDistance()
				- Constants.ClimbConstants.CLIMB_POS) < Constants.ClimbConstants.POSITION_TOLERANCE;
	}
}
