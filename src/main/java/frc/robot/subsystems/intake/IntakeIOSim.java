package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.intake.IntakeConstants;

public class IntakeIOSim implements IntakeIO {
	// private double algaeMotorSpeed;
	private double coralMotorSpeed;
	private double topRollerMotorSpeed;

	public IntakeIOSim() {
		// algaeMotorSpeed = 0.0;
		coralMotorSpeed = 0.0;
		topRollerMotorSpeed = 0.0;

		SmartDashboard.putBoolean("Coral In", false);
	}

	public void updateInputs(IntakeIOInputs inputs) {
		// inputs.algaeMotorSpeed = algaeMotorSpeed;
		inputs.coralMotorSpeed = coralMotorSpeed;
		inputs.topRollerMotorSpeed = topRollerMotorSpeed;

		inputs.coralSensed = SmartDashboard.getBoolean("Coral In", false);
		// inputs.algaeCurrent = 0;
	}

	public void setSpeeds(double coralSpeed, double algaeSpeed, double topRollerSpeed) {
		// algaeMotorSpeed = algaeSpeed;
		coralMotorSpeed = coralSpeed;
		topRollerMotorSpeed = topRollerSpeed;
	}
}
