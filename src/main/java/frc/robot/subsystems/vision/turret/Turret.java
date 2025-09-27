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

package frc.robot.subsystems.vision.turret;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Turret extends SubsystemBase {
	private final TurretIO io;
	private final TurretIO.TurretIOInputs inputs = new TurretIO.TurretIOInputs();
	private TurretState state = TurretState.HOME;
	private double targetPositionDegrees = TurretConstants.TURRET_HOME_POSITION;
	private double currentPositionDegrees = 0.0;

	private final Alert turretDisconnectedAlert;
	private final Alert turretEncoderDisconnectedAlert;

	public Turret(TurretIO io) {
		this.io = io;

		turretDisconnectedAlert = new Alert("Disconnected turret motor.", AlertType.kError);
		turretEncoderDisconnectedAlert = new Alert("Disconnected turret encoder.", AlertType.kError);
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		// Logger.processInputs("Turret", inputs);

		// Update current position
		currentPositionDegrees = inputs.position.getDegrees();

		// Handle keyboard input for iMac arrow keys
		handleKeyboardInput();

		// Handle different turret states
		switch (state) {
			case HOME:
				setTargetPosition(TurretConstants.TURRET_HOME_POSITION);
				break;
			case LEFT:
				setTargetPosition(TurretConstants.TURRET_FULL_LEFT);
				break;
			case RIGHT:
				setTargetPosition(TurretConstants.TURRET_FULL_RIGHT);
				break;
			case MANUAL:
				// Manual control - target position is set externally
				break;
			case KEYBOARD:
				// Keyboard control - handled in handleKeyboardInput()
				break;
			case STOPPED:
				io.setTurretOpenLoop(0.0);
				break;
		}

		// Update alerts
		turretDisconnectedAlert.set(!inputs.connected);
		turretEncoderDisconnectedAlert.set(!inputs.encoderConnected);

		// Log outputs for Advantage Scope
		Logger.recordOutput("Turret/CurrentPosition", currentPositionDegrees);
		Logger.recordOutput("Turret/TargetPosition", targetPositionDegrees);
		Logger.recordOutput("Turret/State", state);
		Logger.recordOutput("Turret/AtTarget", inputs.atTarget);
		Logger.recordOutput("Turret/Velocity", inputs.velocityRadPerSec);
		Logger.recordOutput("Turret/KeyboardActive", inputs.keyboardControlActive);
		Logger.recordOutput("Turret/KeyboardInputDegrees", inputs.keyboardInputDegrees);
		Logger.recordOutput("Turret/LeftArrowPressed", inputs.leftArrowPressed);
		Logger.recordOutput("Turret/RightArrowPressed", inputs.rightArrowPressed);
		Logger.recordOutput("Turret/UpArrowPressed", inputs.upArrowPressed);
		Logger.recordOutput("Turret/DownArrowPressed", inputs.downArrowPressed);
	}

	private void handleKeyboardInput() {
		// Handle iMac arrow key inputs
		if (inputs.keyboardControlActive) {
			state = TurretState.KEYBOARD;

			// Calculate new target position based on keyboard input
			double newTargetDegrees = targetPositionDegrees + inputs.keyboardInputDegrees;

			// Apply 180-degree limits
			newTargetDegrees = Math.max(TurretConstants.TURRET_MIN_ANGLE,
					Math.min(TurretConstants.TURRET_MAX_ANGLE, newTargetDegrees));

			setTargetPosition(newTargetDegrees);

			// Log keyboard input for Advantage Scope
			System.out.println("Turret Keyboard Control - Target: " + newTargetDegrees +
					"°, Current: " + currentPositionDegrees + "°, Input: " + inputs.keyboardInputDegrees + "°");
		}

		// Handle special key commands
		if (inputs.upArrowPressed) {
			// Up arrow - go to home position
			state = TurretState.HOME;
			setTargetPosition(TurretConstants.TURRET_HOME_POSITION);
			System.out.println("Turret: Up arrow pressed - going to home position");
		} else if (inputs.downArrowPressed) {
			// Down arrow - stop turret
			state = TurretState.STOPPED;
			io.setTurretOpenLoop(0.0);
			System.out.println("Turret: Down arrow pressed - stopping turret");
		}
	}

	private void setTargetPosition(double positionDegrees) {
		// Ensure position is within 180-degree limits
		positionDegrees = Math.max(TurretConstants.TURRET_MIN_ANGLE,
				Math.min(TurretConstants.TURRET_MAX_ANGLE, positionDegrees));

		targetPositionDegrees = positionDegrees;
		Rotation2d targetRotation = Rotation2d.fromDegrees(positionDegrees);
		io.setTurretPosition(targetRotation);
	}

	public enum TurretState {
		HOME, LEFT, RIGHT, MANUAL, KEYBOARD, STOPPED
	}

	/** Returns the current turret angle. */
	public Rotation2d getAngle() {
		return inputs.position;
	}

	/** Returns the current turret angle in degrees. */
	public double getAngleDegrees() {
		return currentPositionDegrees;
	}

	/** Returns the current turret velocity in radians per second. */
	public double getVelocityRadPerSec() {
		return inputs.velocityRadPerSec;
	}

	/** Returns whether the turret is at its target position. */
	public boolean isAtTarget() {
		return inputs.atTarget;
	}

	/** Returns whether the turret motor is connected. */
	public boolean isConnected() {
		return inputs.connected;
	}

	/** Returns whether the turret encoder is connected. */
	public boolean isEncoderConnected() {
		return inputs.encoderConnected;
	}

	/** Returns whether keyboard control is active. */
	public boolean isKeyboardControlActive() {
		return inputs.keyboardControlActive;
	}

	/** Command to set the turret state. */
	public Command setState(TurretState newState) {
		return Commands.runOnce(() -> this.state = newState);
	}

	/** Command to set the turret to a specific position in degrees. */
	public Command setPosition(double positionDegrees) {
		return Commands.runOnce(() -> {
			this.state = TurretState.MANUAL;
			this.targetPositionDegrees = positionDegrees;
		});
	}

	/** Command to set the turret to home position. */
	public Command goToHome() {
		return setState(TurretState.HOME);
	}

	/** Command to set the turret to left position. */
	public Command goToLeft() {
		return setState(TurretState.LEFT);
	}

	/** Command to set the turret to right position. */
	public Command goToRight() {
		return setState(TurretState.RIGHT);
	}

	/** Command to stop the turret. */
	public Command stop() {
		return setState(TurretState.STOPPED);
	}

	/** Command to enable keyboard control. */
	public Command enableKeyboardControl() {
		return setState(TurretState.KEYBOARD);
	}

	/** Command to run the turret at a specific velocity. */
	public Command runVelocity(double velocityRadPerSec) {
		return Commands.run(() -> io.setTurretVelocity(velocityRadPerSec), this);
	}

	/** Command to run the turret at a specific open loop output. */
	public Command runOpenLoop(double output) {
		return Commands.run(() -> io.setTurretOpenLoop(output), this);
	}

	/** Command to wait until the turret reaches its target. */
	public Command waitForTarget() {
		return Commands.waitUntil(this::isAtTarget);
	}

	/** Command to go to a position and wait until it's reached. */
	public Command goToPositionAndWait(double positionDegrees) {
		return setPosition(positionDegrees).andThen(waitForTarget());
	}
}