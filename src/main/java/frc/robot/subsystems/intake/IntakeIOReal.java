package frc.robot.subsystems.intake;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import frc.robot.Constants.MotorConstants;

public class IntakeIOReal implements IntakeIO {

	// private final SparkMax algaeMotor;
	private final SparkMax coralMotor;
	private final SparkMax topRollerMotor;
	private final SparkMaxConfig config;

	private LinearFilter currentFilter = LinearFilter.movingAverage(10);

	private DigitalInput laserBreak;

	public IntakeIOReal() {
		// algaeMotor = new SparkMax(IntakeConstants.ALGAE_MOTOR_ID,
		// MotorType.kBrushless);
		coralMotor = new SparkMax(IntakeConstants.CORAL_MOTOR_ID, MotorType.kBrushless);
		topRollerMotor = new SparkMax(IntakeConstants.TOP_ROLLER_MOTOR_ID, MotorType.kBrushless);
		config = new SparkMaxConfig();

		laserBreak = new DigitalInput(IntakeConstants.CORAL_LASER_BREAK_PORT);

		config.smartCurrentLimit(30);
		config.idleMode(IdleMode.kBrake);
	}

	@Override
	public void updateInputs(IntakeIOInputs inputs) {
		// inputs.algaeMotorSpeed = algaeMotor.get();
		inputs.coralMotorSpeed = coralMotor.get();
		inputs.topRollerMotorSpeed = topRollerMotor.get();

		inputs.coralSensed = !laserBreak.get();
		// inputs.algaeCurrent = currentFilter.calculate(algaeMotor.getOutputCurrent());
	}

	@Override
	public void setSpeeds(double coralSpeed, double algaeSpeed, double topRollerSpeed) {
		coralMotor.set(coralSpeed);
		// algaeMotor.set(algaeSpeed);
		topRollerMotor.set(topRollerSpeed);
	}

}
