package frc.robot.subsystems.elevator;

import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicExpoVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.MathUtil;
import frc.robot.subsystems.elevator.ElevatorIO.ElevatorIOInputs;

public class ElevatorIOReal implements ElevatorIO {
	private final TalonFX elevatorMotorLEFT;
	private final TalonFX elevatorMotorRIGHT;
	private double targetPositionInRotations;
	private TalonFXConfiguration elevatorMotorsConfig;
	private MotionMagicExpoVoltage motionMagicVoltage;
	private MotionMagicConfigs motionMagicConfigs;

	public ElevatorIOReal() {
		elevatorMotorLEFT = new TalonFX(ElevatorConstants.MOTOR_ELEVATOR_LEFT);
		elevatorMotorRIGHT = new TalonFX(ElevatorConstants.MOTOR_ELEVATOR_RIGHT);
		elevatorMotorsConfig = new TalonFXConfiguration();

		elevatorMotorsConfig.Slot0.kP = ElevatorConstants.ELEVATOR_P;
		elevatorMotorsConfig.Slot0.kI = ElevatorConstants.ELEVATOR_I;
		elevatorMotorsConfig.Slot0.kD = ElevatorConstants.ELEVATOR_D;
		elevatorMotorsConfig.Slot0.GravityType = GravityTypeValue.Elevator_Static;
		elevatorMotorsConfig.Slot0.kG = ElevatorConstants.ELEVATOR_kG;
		elevatorMotorsConfig.Slot0.kA = ElevatorConstants.ELEVATOR_kA;
		elevatorMotorsConfig.Slot0.kV = ElevatorConstants.ELEVATOR_kV;
		elevatorMotorsConfig.Slot0.kS = ElevatorConstants.ELEVATOR_kS;

		elevatorMotorsConfig.CurrentLimits.StatorCurrentLimitEnable = true;
		elevatorMotorsConfig.CurrentLimits.StatorCurrentLimit = 100;

		elevatorMotorsConfig.Feedback.SensorToMechanismRatio = ElevatorConstants.ELEVATOR_GEAR_RATIO;

		elevatorMotorsConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

		motionMagicVoltage = new MotionMagicExpoVoltage(0);
		motionMagicVoltage.EnableFOC = true;

		motionMagicConfigs = elevatorMotorsConfig.MotionMagic;
		motionMagicConfigs.MotionMagicExpo_kA = 1 - 0.3;
		motionMagicConfigs.MotionMagicExpo_kV = ElevatorConstants.ELEVATOR_kV - 1.3;

		elevatorMotorsConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

		elevatorMotorLEFT.getConfigurator().apply(elevatorMotorsConfig);

		elevatorMotorsConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
		elevatorMotorRIGHT.getConfigurator().apply(elevatorMotorsConfig);

		elevatorMotorLEFT.setPosition(ElevatorConstants.ELEVATOR_HOME_POSITION);
		elevatorMotorRIGHT.setPosition(ElevatorConstants.ELEVATOR_HOME_POSITION);

		targetPositionInRotations = 0.0;
	}

	@Override
	public void updateInputs(ElevatorIOInputs inputs) {
		inputs.position = ((elevatorMotorLEFT.getPosition().getValueAsDouble() +
				elevatorMotorRIGHT.getPosition().getValueAsDouble()) / 2)
				* (Math.PI * ElevatorConstants.ELEVATOR_SPOOL_DIAMETER);
	}

	@Override
	public void setElevatorPosition(double wantedPosition) {
		wantedPosition = MathUtil.clamp(wantedPosition, 0, ElevatorConstants.LEVEL_4_POSITION + 1);
		wantedPosition /= (Math.PI * ElevatorConstants.ELEVATOR_SPOOL_DIAMETER);
		elevatorMotorRIGHT.setControl(motionMagicVoltage.withPosition(wantedPosition));
		elevatorMotorLEFT.setControl(motionMagicVoltage.withPosition(wantedPosition));
	}

	@Override
	public void runManualUp() {
		elevatorMotorLEFT.set(0.2);
		elevatorMotorRIGHT.set(0.2);
	}

	@Override
	public void stop() {
		elevatorMotorLEFT.set(0);
		elevatorMotorRIGHT.set(0);
	}

	@Override
	public void runManualDown() {
		elevatorMotorLEFT.set(-0.2);
		elevatorMotorRIGHT.set(-0.2);
	}
}
