package frc.robot.subsystems.pivot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import frc.robot.Constants;
import frc.robot.subsystems.pivot.PivotConstants;

public class PivotIOSim implements PivotIO {
	private double currentPosInRotations;

	public PivotIOSim() {
		currentPosInRotations = PivotConstants.PIVOT_HOME_POSITION;
	}

	@Override
	public void updateInputs(PivotIOInputs inputs) {
		inputs.position = currentPosInRotations;
		inputs.voltage = 0;
	}

	@Override
	public void setPivotPosition(double position) {
		currentPosInRotations = position;
	}
}
