package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.intake.IntakeConstants;

public class IntakeIOSim implements IntakeIO {
	private double coralMotorSpeed;

	public IntakeIOSim() {
		coralMotorSpeed = 0.0;

		SmartDashboard.putBoolean("Coral In", false);
	}

	@Override
	public void updateInputs(IntakeIOInputs inputs) {
		inputs.coralMotorSpeed = coralMotorSpeed;

		inputs.coralSensed = SmartDashboard.getBoolean("Coral In", false);
	}

	@Override
	public void setSpeeds(double coralSpeed) {
		coralMotorSpeed = coralSpeed;
	}
}
