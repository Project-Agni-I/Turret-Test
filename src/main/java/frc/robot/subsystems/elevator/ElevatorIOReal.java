package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.subsystems.elevator.ElevatorIO.ElevatorIOInputs;

public class ElevatorIOReal implements ElevatorIO {
	private final TalonFX elevatorMotorLEFT;
	private final TalonFX elevatorMotorRIGHT;
	private double targetPositionInRotations;
	private TalonFXConfiguration elevatorMotorLEFTMotorConfig;
	private TalonFXConfiguration elevatorMotorRIGHTMotorConfig;
	private MotionMagicExpoVoltage motionMagicVoltage;
	private MotionMagicConfigs motionMagicConfigs;

	public ElevatorIOReal() {
		elevatorMotorLEFT = new TalonFX(ElevatorConstants.MOTOR_ELEVATOR_LEFT);
		elevatorMotorRIGHT = new TalonFX(ElevatorConstants.MOTOR_ELEVATOR_RIGHT);
		elevatorMotorLEFTMotorConfig = new TalonFXConfiguration();
		elevatorMotorRIGHTMotorConfig = new TalonFXConfiguration();

		elevatorMotorLEFTMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
		elevatorMotorLEFTMotorConfig.Slot0.kP = ElevatorConstants.ELEVATOR_P;
		elevatorMotorLEFTMotorConfig.Slot0.kI = ElevatorConstants.ELEVATOR_I;
		elevatorMotorLEFTMotorConfig.Slot0.kD = ElevatorConstants.ELEVATOR_D;
		elevatorMotorLEFTMotorConfig.Slot0.GravityType = GravityTypeValue.Elevator_Static;
		elevatorMotorLEFTMotorConfig.Slot0.kG = ElevatorConstants.ELEVATOR_kG;
		elevatorMotorLEFTMotorConfig.Slot0.kA = ElevatorConstants.ELEVATOR_kA;
		elevatorMotorLEFTMotorConfig.Slot0.kV = ElevatorConstants.ELEVATOR_kV;
		elevatorMotorLEFTMotorConfig.Slot0.kS = ElevatorConstants.ELEVATOR_kS;
		elevatorMotorLEFTMotorConfig.Feedback.SensorToMechanismRatio = ElevatorConstants.ELEVATOR_GEAR_RATIO;

		elevatorMotorRIGHTMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
		elevatorMotorRIGHTMotorConfig.Slot0.kP = ElevatorConstants.ELEVATOR_P;
		elevatorMotorRIGHTMotorConfig.Slot0.kI = ElevatorConstants.ELEVATOR_I;
		elevatorMotorRIGHTMotorConfig.Slot0.kD = ElevatorConstants.ELEVATOR_D;
		elevatorMotorRIGHTMotorConfig.Slot0.GravityType = GravityTypeValue.Elevator_Static;
		elevatorMotorRIGHTMotorConfig.Slot0.kG = ElevatorConstants.ELEVATOR_kG;
		elevatorMotorRIGHTMotorConfig.Slot0.kA = ElevatorConstants.ELEVATOR_kA;
		elevatorMotorRIGHTMotorConfig.Slot0.kV = ElevatorConstants.ELEVATOR_kV;
		elevatorMotorRIGHTMotorConfig.Slot0.kS = ElevatorConstants.ELEVATOR_kS;
		elevatorMotorRIGHTMotorConfig.Feedback.SensorToMechanismRatio = ElevatorConstants.ELEVATOR_GEAR_RATIO;

		motionMagicVoltage = new MotionMagicExpoVoltage(0);
		motionMagicVoltage.EnableFOC = true;

		motionMagicConfigs = elevatorMotorLEFTMotorConfig.MotionMagic;
		motionMagicConfigs = elevatorMotorRIGHTMotorConfig.MotionMagic;
		motionMagicConfigs.MotionMagicExpo_kA = 1;
		motionMagicConfigs.MotionMagicExpo_kV = ElevatorConstants.ELEVATOR_kV;

		elevatorMotorLEFT.getConfigurator().apply(elevatorMotorLEFTMotorConfig);
		elevatorMotorRIGHT.getConfigurator().apply(elevatorMotorRIGHTMotorConfig);

		elevatorMotorLEFT.setPosition(ElevatorConstants.ELEVATOR_HOME_POSITION);
		elevatorMotorRIGHT.setPosition(ElevatorConstants.ELEVATOR_HOME_POSITION);

		targetPositionInRotations = 0.0;
	}

	public void updateInputs(ElevatorIOInputs inputs) {
		inputs.position = elevatorMotorLEFT.getPosition().getValueAsDouble();
		inputs.position = elevatorMotorRIGHT.getPosition().getValueAsDouble();

		inputs.voltage = elevatorMotorLEFT.getMotorVoltage().getValueAsDouble();
		inputs.voltage = elevatorMotorRIGHT.getMotorVoltage().getValueAsDouble();
	}

	public void setElevatorPosition(double wantedPosition) {
		elevatorMotorRIGHT.setControl(motionMagicVoltage.withPosition(wantedPosition));
		elevatorMotorLEFT.setControl(motionMagicVoltage.withPosition(wantedPosition));

	}
}
