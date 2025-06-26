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

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.XboxController;

public class TurretIOSim implements TurretIO {
	private double positionRad = 0.0;
	private double velocityRadPerSec = 0.0;
	private double appliedVolts = 0.0;
	private double targetPositionRad = 0.0;
	private boolean brakeMode = true;

	// Keyboard input handling (using Xbox controller as proxy for keyboard)
	private final XboxController keyboardController = new XboxController(2); // Use controller 2 for keyboard simulation

	@Override
	public void updateInputs(TurretIOInputs inputs) {
		// Simulate motor response
		if (Math.abs(appliedVolts) > 0.01) {
			velocityRadPerSec = appliedVolts * TurretConstants.TURRET_kV;
			positionRad += velocityRadPerSec * 0.02; // 20ms loop time
		} else if (brakeMode) {
			velocityRadPerSec *= 0.8; // Simulate brake mode
		} else {
			velocityRadPerSec *= 0.95; // Simulate coast mode
		}

		// Apply soft limits for 180 degree range
		double maxAngleRad = Units.degreesToRadians(TurretConstants.TURRET_MAX_ANGLE);
		double minAngleRad = Units.degreesToRadians(TurretConstants.TURRET_MIN_ANGLE);

		if (positionRad > maxAngleRad) {
			positionRad = maxAngleRad;
			velocityRadPerSec = 0.0;
		} else if (positionRad < minAngleRad) {
			positionRad = minAngleRad;
			velocityRadPerSec = 0.0;
		}

		// Update hardware inputs
		inputs.connected = true;
		inputs.encoderConnected = true;
		inputs.absolutePosition = new Rotation2d(positionRad);
		inputs.position = new Rotation2d(positionRad);
		inputs.velocityRadPerSec = velocityRadPerSec;
		inputs.appliedVolts = appliedVolts;
		inputs.currentAmps = Math.abs(appliedVolts) * 2.0; // Simulate current draw
		inputs.temperatureCelsius = 25.0 + Math.abs(appliedVolts) * 5.0; // Simulate temperature

		inputs.atTarget = Math.abs(positionRad - targetPositionRad) < Units
				.degreesToRadians(TurretConstants.TURRET_POSITION_TOLERANCE);
		inputs.targetPositionRad = targetPositionRad;

		// Update keyboard inputs (using Xbox controller buttons as proxy for iMac arrow
		// keys)
		// Left Arrow (37) - Left Bumper
		inputs.leftArrowPressed = keyboardController.getLeftBumper();
		inputs.leftArrowKeyCode = 37;

		// Right Arrow (39) - Right Bumper
		inputs.rightArrowPressed = keyboardController.getRightBumper();
		inputs.rightArrowKeyCode = 39;

		// Up Arrow (38) - Y Button
		inputs.upArrowPressed = keyboardController.getYButton();
		inputs.upArrowKeyCode = 38;

		// Down Arrow (40) - A Button
		inputs.downArrowPressed = keyboardController.getAButton();
		inputs.downArrowKeyCode = 40;

		// Calculate keyboard input degrees based on button presses
		inputs.keyboardInputDegrees = 0.0;
		inputs.keyboardControlActive = false;

		if (inputs.leftArrowPressed) {
			inputs.keyboardInputDegrees = -TurretConstants.TURRET_KEYBOARD_INCREMENT;
			inputs.keyboardControlActive = true;
		} else if (inputs.rightArrowPressed) {
			inputs.keyboardInputDegrees = TurretConstants.TURRET_KEYBOARD_INCREMENT;
			inputs.keyboardControlActive = true;
		}

		// Log keyboard state for Advantage Scope
		if (inputs.keyboardControlActive) {
			System.out.println("Sim Keyboard Input - Left: " + inputs.leftArrowPressed +
					", Right: " + inputs.rightArrowPressed +
					", Up: " + inputs.upArrowPressed +
					", Down: " + inputs.downArrowPressed +
					", Degrees: " + inputs.keyboardInputDegrees +
					", Position: " + Units.radiansToDegrees(positionRad) + "°");
		}
	}

	@Override
	public void setTurretOpenLoop(double output) {
		appliedVolts = output * 12.0; // Scale to 12V
	}

	@Override
	public void setTurretPosition(Rotation2d rotation) {
		targetPositionRad = rotation.getRadians();
		// Simple P controller for simulation
		double error = targetPositionRad - positionRad;
		appliedVolts = error * TurretConstants.TURRET_P;

		// Apply voltage limits
		appliedVolts = Math.max(-12.0, Math.min(12.0, appliedVolts));
	}

	@Override
	public void setTurretVelocity(double velocityRadPerSec) {
		this.velocityRadPerSec = velocityRadPerSec;
		appliedVolts = velocityRadPerSec * TurretConstants.TURRET_kV;
	}

	@Override
	public void setBrakeMode(boolean enabled) {
		brakeMode = enabled;
	}

	@Override
	public void configurePID(double kP, double kI, double kD) {
		// PID configuration is handled in the position control method
	}

	@Override
	public void configureFeedforward(double kS, double kG, double kV, double kA) {
		// Feedforward configuration is handled in the velocity control method
	}
}