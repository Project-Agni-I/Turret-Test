package frc.robot.subsystems.intake;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class IntakeIOReal implements IntakeIO {
	private final SparkMax coralMotor;
	private final SparkMaxConfig config;

	private static DigitalInput laserBreak;

	public IntakeIOReal() {
		coralMotor = new SparkMax(IntakeConstants.CORAL_MOTOR_ID, MotorType.kBrushless);
		config = new SparkMaxConfig();

		laserBreak = new DigitalInput(IntakeConstants.CORAL_LASER_BREAK_PORT);

		config.smartCurrentLimit(30);
		config.idleMode(IdleMode.kBrake);
		coralMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
	}

	@Override
	public void updateInputs(IntakeIOInputs inputs) {
		inputs.coralMotorSpeed = coralMotor.get();

		inputs.coralSensed = !laserBreak.get();
	}

	@Override
	public void setSpeeds(double coralSpeed) {
		coralMotor.set(coralSpeed);
	}

	public static boolean getLaserBreak() {
		return !laserBreak.get();
	}
}
