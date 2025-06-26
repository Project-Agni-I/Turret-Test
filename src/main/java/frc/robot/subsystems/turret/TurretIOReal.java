// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.subsystems.turret;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import org.littletonrobotics.junction.Logger;

public class TurretIOReal implements TurretIO {
	private final TalonFX turretMotor;
	private final CANcoder turretEncoder;
	private final PositionVoltage positionRequest = new PositionVoltage(0);
	private final VelocityVoltage velocityRequest = new VelocityVoltage(0);

	public TurretIOReal() {
		turretMotor = new TalonFX(TurretConstants.TURRET_MOTOR_ID);
		turretEncoder = new CANcoder(TurretConstants.TURRET_ENCODER_ID);

		// Configure turret motor
		TalonFXConfiguration turretConfig = new TalonFXConfiguration();
		turretConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
		turretConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

		// Configure PID
		turretConfig.Slot0.kP = TurretConstants.TURRET_P;
		turretConfig.Slot0.kI = TurretConstants.TURRET_I;
		turretConfig.Slot0.kD = TurretConstants.TURRET_D;

		// Configure feedforward
		turretConfig.Slot0.kS = TurretConstants.TURRET_kS;
		turretConfig.Slot0.kG = TurretConstants.TURRET_kG;
		turretConfig.Slot0.kV = TurretConstants.TURRET_kV;
		turretConfig.Slot0.kA = TurretConstants.TURRET_kA;

		// Configure soft limits
		turretConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
		turretConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = Units
				.degreesToRotations(TurretConstants.TURRET_MAX_ANGLE);
		turretConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
		turretConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = Units
				.degreesToRotations(TurretConstants.TURRET_MIN_ANGLE);

		turretMotor.getConfigurator().apply(turretConfig);

		// Configure turret encoder
		CANcoderConfiguration encoderConfig = new CANcoderConfiguration();
		encoderConfig.MagnetSensor.SensorDirection = SensorDirectionValue.CounterClockwise_Positive;

		turretEncoder.getConfigurator().apply(encoderConfig);

		// Set the turret encoder as the feedback device for the motor
		turretMotor.setPosition(turretEncoder.getAbsolutePosition().getValueAsDouble());
	}

	@Override
	public void updateInputs(TurretIOInputs inputs) {
		inputs.connected = turretMotor.getDeviceTemp().getValueAsDouble() != 0.0;
		inputs.encoderConnected = turretEncoder.getAbsolutePosition().getValueAsDouble() != 0.0;

		inputs.absolutePosition = Rotation2d.fromRotations(turretEncoder.getAbsolutePosition().getValueAsDouble());
		inputs.position = Rotation2d.fromRotations(turretMotor.getPosition().getValueAsDouble());
		inputs.velocityRadPerSec = Units.rotationsToRadians(turretMotor.getVelocity().getValueAsDouble());
		inputs.appliedVolts = turretMotor.getMotorVoltage().getValueAsDouble();
		inputs.currentAmps = turretMotor.getStatorCurrent().getValueAsDouble();
		inputs.temperatureCelsius = turretMotor.getDeviceTemp().getValueAsDouble();

		inputs.atTarget = Math.abs(inputs.position.getRadians() - inputs.targetPositionRad) < Units
				.degreesToRadians(TurretConstants.TURRET_POSITION_TOLERANCE);
		inputs.targetPositionRad = positionRequest.Position;
	}

	@Override
	public void setTurretOpenLoop(double output) {
		turretMotor.set(output);
	}

	@Override
	public void setTurretPosition(Rotation2d rotation) {
		double positionRotations = Units.radiansToRotations(rotation.getRadians());
		positionRequest.Position = positionRotations;
		turretMotor.setControl(positionRequest);
	}

	@Override
	public void setTurretVelocity(double velocityRadPerSec) {
		double velocityRotations = Units.radiansToRotations(velocityRadPerSec);
		velocityRequest.Velocity = velocityRotations;
		turretMotor.setControl(velocityRequest);
	}

	@Override
	public void setBrakeMode(boolean enabled) {
		turretMotor.setNeutralMode(enabled ? NeutralModeValue.Brake : NeutralModeValue.Coast);
	}

	@Override
	public void configurePID(double kP, double kI, double kD) {
		TalonFXConfiguration config = new TalonFXConfiguration();
		config.Slot0.kP = kP;
		config.Slot0.kI = kI;
		config.Slot0.kD = kD;
		turretMotor.getConfigurator().apply(config);
	}

	@Override
	public void configureFeedforward(double kS, double kG, double kV, double kA) {
		TalonFXConfiguration config = new TalonFXConfiguration();
		config.Slot0.kS = kS;
		config.Slot0.kG = kG;
		config.Slot0.kV = kV;
		config.Slot0.kA = kA;
		turretMotor.getConfigurator().apply(config);
	}
}