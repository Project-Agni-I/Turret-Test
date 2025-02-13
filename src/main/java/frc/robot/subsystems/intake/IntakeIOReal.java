package frc.robot.subsystems.intake;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Constants.MotorConstants;

public class IntakeIOReal implements IntakeIO {

	private final TalonFX algaeMotor;
	private final TalonFX coralMotor;
	private final TalonFX combinationMotor;
	private double targetPositionInRotationsAlgae;
	private double targetPositionInRotationsCoral;
	private double targetPositionInRotationsCombination;

	public IntakeIOReal() {
		algaeMotor = new TalonFX(MotorConstants.MOTOR_ALGAE_ID);
		coralMotor = new TalonFX(MotorConstants.MOTOR_CORAL1_ID);
		combinationMotor = new TalonFX(MotorConstants.MOTOR_COMBINATION_ID);
		targetPositionInRotationsAlgae = 0.0;
		targetPositionInRotationsCoral = 0.0;
		targetPositionInRotationsCombination = 0.0;
	}

	public static double convertDegreesToRotations(double degrees) {
		return degrees / 360.0;
	}

	public void setAlgaeMotorPosition(double wantedPosition) {
		targetPositionInRotationsAlgae = convertDegreesToRotations(wantedPosition);
		PositionVoltage positionVoltageAlgae = new PositionVoltage(targetPositionInRotationsAlgae);
		algaeMotor.setControl(positionVoltageAlgae.withPosition(wantedPosition));
	}

	public void setCoralMotorPosition(double wantedPosition) {
		targetPositionInRotationsCoral = convertDegreesToRotations(wantedPosition);
		PositionVoltage positionVoltageCoral = new PositionVoltage(targetPositionInRotationsCoral);
		coralMotor.setControl(positionVoltageCoral.withPosition(wantedPosition));
	}

	public void setCombinationMotorPosition(double wantedPosition) {
		targetPositionInRotationsCombination = convertDegreesToRotations(wantedPosition);
		PositionVoltage positionVoltageCombination = new PositionVoltage(targetPositionInRotationsCombination);
		combinationMotor.setControl(positionVoltageCombination.withPosition(wantedPosition));
	}

	public void setAlgaeMotorSpeed(double speed) {
		PositionVoltage positionVoltageAlgae = new PositionVoltage(speed);
		algaeMotor.setControl(positionVoltageAlgae);
	}

	public void setCoralMotorSpeed(double speed) {
		PositionVoltage positionVoltageCoral = new PositionVoltage(speed);
		coralMotor.setControl(positionVoltageCoral);
	}

	public void setCombinationMotorSpeed(double speed) {
		PositionVoltage positionVoltageCombination = new PositionVoltage(speed);
		combinationMotor.setControl(positionVoltageCombination);
	}
}
