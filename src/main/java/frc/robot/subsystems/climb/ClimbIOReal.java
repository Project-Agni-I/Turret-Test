package frc.robot.subsystems.climb;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.Constants.MotorConstants;

public class ClimbIOReal extends SubsystemBase implements ClimbIO {

	public final TalonFX climbMotor;
	private double targetPositionInRotations;
	private TalonFXConfiguration climbMotorConfig;
	private MotionMagicExpoVoltage motionMagicVoltage;
	private MotionMagicConfigs motionMagicConfigs;

	public ClimbIOReal() {
		climbMotor = new TalonFX(MotorConstants.CLIMB_MOTOR_ID);
		climbMotorConfig = new TalonFXConfiguration();

		climbMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
		climbMotorConfig.Slot0.kP = ClimbConstants.CLIMB_P;
		climbMotorConfig.Slot0.kI = ClimbConstants.CLIMB_I;
		climbMotorConfig.Slot0.kD = ClimbConstants.CLIMB_D;
		climbMotorConfig.Slot0.kG = ClimbConstants.CLIMB_kG;
		climbMotorConfig.Slot0.kA = ClimbConstants.CLIMB_kA;
		climbMotorConfig.Slot0.kV = ClimbConstants.CLIMB_kV;
		climbMotorConfig.Slot0.kS = ClimbConstants.CLIMB_kS;

		motionMagicVoltage = new MotionMagicExpoVoltage(0);
		motionMagicVoltage.EnableFOC = true;

		motionMagicConfigs = climbMotorConfig.MotionMagic;
		motionMagicConfigs.MotionMagicExpo_kA = 1;
		motionMagicConfigs.MotionMagicExpo_kV = ClimbConstants.CLIMB_kV;

		climbMotor.getConfigurator().apply(climbMotorConfig);

		climbMotor.setPosition(ClimbConstants.CLIMB_HOME_POSITION);

		targetPositionInRotations = 0.0;
	}

	public void updateInputs(ClimbIOInputs inputs) {
		inputs.position = climbMotor.getPosition().getValueAsDouble();
		inputs.voltage = climbMotor.getMotorVoltage().getValueAsDouble();
	}

	public void setClimbPosition(double position) {
		climbMotor.setControl(motionMagicVoltage.withPosition(position));
	}

	public double getPosition() {
		return climbMotor.getPosition().getValueAsDouble();
	}
}
