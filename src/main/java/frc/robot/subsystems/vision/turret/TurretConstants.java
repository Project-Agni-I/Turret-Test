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

import edu.wpi.first.math.util.Units;

public final class TurretConstants {
	// PID Constants
	public static final double TURRET_P = 0.5;
	public static final double TURRET_I = 0.0;
	public static final double TURRET_D = 0.1;

	// Feedforward Constants
	public static final double TURRET_kS = 0.1;
	public static final double TURRET_kG = 0.05;
	public static final double TURRET_kV = 0.3;
	public static final double TURRET_kA = 0.0;

	// Hardware Constants
	public static final int TURRET_MOTOR_ID = 20;
	public static final int TURRET_ENCODER_ID = 21;
	public static final double TURRET_GEAR_RATIO = 20.0; // 20:1 gear ratio
	public static final double TURRET_MAX_VELOCITY_RAD_PER_SEC = Units.degreesToRadians(180.0); // 180 degrees per
																								// second
	public static final double TURRET_MAX_ACCELERATION_RAD_PER_SEC_SQ = Units.degreesToRadians(360.0); // 360 degrees
																										// per second
																										// squared

	// Position Constants (in degrees) - 180 degree range centered at 0
	public static final double TURRET_HOME_POSITION = 0.0;
	public static final double TURRET_LEFT_LIMIT = -90.0; // 90 degrees left from center
	public static final double TURRET_RIGHT_LIMIT = 90.0; // 90 degrees right from center
	public static final double TURRET_FULL_LEFT = -90.0; // Maximum left position
	public static final double TURRET_FULL_RIGHT = 90.0; // Maximum right position

	// Soft Limits (in degrees) - Total 180 degree range
	public static final double TURRET_MIN_ANGLE = -90.0; // 90 degrees left
	public static final double TURRET_MAX_ANGLE = 90.0; // 90 degrees right

	// Tolerance for position control (in degrees)
	public static final double TURRET_POSITION_TOLERANCE = 2.0;

	// Keyboard control constants
	public static final double TURRET_KEYBOARD_SPEED = 0.3; // Speed multiplier for keyboard control
	public static final double TURRET_KEYBOARD_INCREMENT = 1.0; // Degrees per key press
}