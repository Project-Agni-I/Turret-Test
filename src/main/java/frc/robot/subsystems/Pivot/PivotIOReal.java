package frc.robot.subsystems.pivot;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.Constants;
import frc.robot.subsystems.pivot.PivotIO;
import frc.robot.subsystems.pivot.PivotConstants;

public class PivotIOReal implements PivotIO {
	private final TalonFX pivotMotor;
	private double targetPositionInRotations;
	private TalonFXConfiguration pivotMotorConfig;
	private MotionMagicExpoVoltage motionMagicVoltage;
	private MotionMagicConfigs motionMagicConfigs;

	public PivotIOReal() {
		pivotMotor = new TalonFX(PivotConstants.PIVOT_MOTOR_ID);
		pivotMotorConfig = new TalonFXConfiguration();

		pivotMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
		pivotMotorConfig.Slot0.kP = PivotConstants.PIVOT_P;
		pivotMotorConfig.Slot0.kI = PivotConstants.PIVOT_I;
		pivotMotorConfig.Slot0.kD = PivotConstants.PIVOT_D;
		pivotMotorConfig.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
		pivotMotorConfig.Slot0.kG = PivotConstants.PIVOT_kG;
		pivotMotorConfig.Slot0.kA = PivotConstants.PIVOT_kA;
		pivotMotorConfig.Slot0.kV = PivotConstants.PIVOT_kV;
		pivotMotorConfig.Slot0.kS = PivotConstants.PIVOT_kS;
		pivotMotorConfig.Feedback.SensorToMechanismRatio = PivotConstants.PIVOT_GEAR_RATIO;

		motionMagicVoltage = new MotionMagicExpoVoltage(0);
		motionMagicVoltage.EnableFOC = true;

		motionMagicConfigs = pivotMotorConfig.MotionMagic;
		motionMagicConfigs.MotionMagicExpo_kA = 1;
		motionMagicConfigs.MotionMagicExpo_kV = PivotConstants.PIVOT_kV;

		pivotMotor.getConfigurator().apply(pivotMotorConfig);

		pivotMotor.setPosition(PivotConstants.PIVOT_HOME_POSITION);

		targetPositionInRotations = 0.0;
	}

	@Override
	public void updateInputs(PivotIOInputs inputs) {
		inputs.position = pivotMotor.getPosition().getValueAsDouble();
		inputs.voltage = pivotMotor.getMotorVoltage().getValueAsDouble();
	}

	@Override
	public void setPivotPosition(double wantedPosition) {
		pivotMotor.setControl(motionMagicVoltage.withPosition(wantedPosition));
	}
}
