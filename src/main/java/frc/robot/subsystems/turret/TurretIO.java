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
import org.littletonrobotics.junction.AutoLog;

public interface TurretIO {
	@AutoLog
	public static class TurretIOInputs {
		// Hardware inputs
		public boolean connected = false;
		public boolean encoderConnected = false;

		public Rotation2d absolutePosition = new Rotation2d();
		public Rotation2d position = new Rotation2d();
		public double velocityRadPerSec = 0.0;
		public double appliedVolts = 0.0;
		public double currentAmps = 0.0;
		public double temperatureCelsius = 0.0;

		public boolean atTarget = false;
		public double targetPositionRad = 0.0;

		// Keyboard inputs for iMac arrow keys
		public boolean leftArrowPressed = false; // Left arrow key (37)
		public boolean rightArrowPressed = false; // Right arrow key (39)
		public boolean upArrowPressed = false; // Up arrow key (38) - for home position
		public boolean downArrowPressed = false; // Down arrow key (40) - for stop

		// Keyboard key codes for iMac
		public int leftArrowKeyCode = 37; // Left arrow key
		public int rightArrowKeyCode = 39; // Right arrow key
		public int upArrowKeyCode = 38; // Up arrow key
		public int downArrowKeyCode = 40; // Down arrow key

		// Keyboard control state
		public double keyboardInputDegrees = 0.0;
		public boolean keyboardControlActive = false;
	}

	/** Updates the set of loggable inputs. */
	public default void updateInputs(TurretIOInputs inputs) {
	}

	/** Run the turret motor at the specified open loop value. */
	public default void setTurretOpenLoop(double output) {
	}

	/** Run the turret motor to the specified rotation. */
	public default void setTurretPosition(Rotation2d rotation) {
	}

	/** Run the turret motor at the specified velocity. */
	public default void setTurretVelocity(double velocityRadPerSec) {
	}

	/** Set the turret to brake mode. */
	public default void setBrakeMode(boolean enabled) {
	}

	/** Configure the turret PID controller. */
	public default void configurePID(double kP, double kI, double kD) {
	}

	/** Configure the turret feedforward controller. */
	public default void configureFeedforward(double kS, double kG, double kV, double kA) {
	}
}