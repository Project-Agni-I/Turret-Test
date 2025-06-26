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

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.subsystems.turret.TurretIO;
import frc.robot.subsystems.turret.TurretIOReal;
import frc.robot.subsystems.turret.TurretIOSim;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.turret.TurretConstants;

/**
 * Example integration of Turret subsystem into RobotContainer.
 * This shows how to add turret with keyboard controls for iMac arrow keys.
 * 
 * iMac Keyboard Mapping:
 * - Left Arrow (37): Turn turret left
 * - Right Arrow (39): Turn turret right
 * - Up Arrow (38): Go to home position
 * - Down Arrow (40): Stop turret
 * 
 * Xbox Controller Mapping (for testing):
 * - Left Bumper: Left Arrow
 * - Right Bumper: Right Arrow
 * - Y Button: Up Arrow
 * - A Button: Down Arrow
 */
public class TurretRobotContainerExample {

	// Turret subsystem
	private final Turret turret;

	// Controllers
	private final CommandXboxController driverController = new CommandXboxController(0);
	private final CommandXboxController operatorController = new CommandXboxController(1);
	private final CommandXboxController keyboardController = new CommandXboxController(2); // For keyboard simulation

	public TurretRobotContainerExample() {
		// Create turret with appropriate IO implementation based on mode
		switch (Constants.currentMode) {
			case REAL:
				turret = new Turret(new TurretIOReal());
				break;
			case SIM:
				turret = new Turret(new TurretIOSim());
				break;
			default:
				turret = new Turret(new TurretIO() {
				}); // Empty implementation
				break;
		}

		configureTurretButtonBindings();
	}

	private void configureTurretButtonBindings() {
		// Enable keyboard control by default
		turret.setDefaultCommand(turret.enableKeyboardControl());

		// Driver controller bindings for turret control
		driverController.leftBumper().whileTrue(
				Commands.run(() -> turret.setPosition(turret.getAngleDegrees() - 5.0), turret));

		driverController.rightBumper().whileTrue(
				Commands.run(() -> turret.setPosition(turret.getAngleDegrees() + 5.0), turret));

		driverController.y().onTrue(turret.goToHome());
		driverController.a().onTrue(turret.stop());

		// Operator controller bindings for preset positions
		operatorController.povUp().onTrue(turret.goToHome());
		operatorController.povLeft().onTrue(turret.goToLeft());
		operatorController.povRight().onTrue(turret.goToRight());
		operatorController.povDown().onTrue(turret.stop());

		// Keyboard controller bindings (simulates iMac arrow keys)
		keyboardController.leftBumper().whileTrue(
				Commands.run(() -> {
					// Simulate left arrow key press
					System.out.println("Left Arrow Pressed - Turning turret left");
					turret.setPosition(turret.getAngleDegrees() - TurretConstants.TURRET_KEYBOARD_INCREMENT);
				}, turret));

		keyboardController.rightBumper().whileTrue(
				Commands.run(() -> {
					// Simulate right arrow key press
					System.out.println("Right Arrow Pressed - Turning turret right");
					turret.setPosition(turret.getAngleDegrees() + TurretConstants.TURRET_KEYBOARD_INCREMENT);
				}, turret));

		keyboardController.y().onTrue(
				Commands.runOnce(() -> {
					// Simulate up arrow key press
					System.out.println("Up Arrow Pressed - Going to home position");
					turret.goToHome();
				}));

		keyboardController.a().onTrue(
				Commands.runOnce(() -> {
					// Simulate down arrow key press
					System.out.println("Down Arrow Pressed - Stopping turret");
					turret.stop();
				}));

		// Additional operator controls
		operatorController.x().onTrue(turret.goToPositionAndWait(45.0)); // Go to 45 degrees
		operatorController.b().onTrue(turret.goToPositionAndWait(-45.0)); // Go to -45 degrees

		// Emergency stop
		operatorController.start().onTrue(turret.stop());
		driverController.start().onTrue(turret.stop());
	}

	/**
	 * Get the turret subsystem for use in other parts of the robot.
	 */
	public Turret getTurret() {
		return turret;
	}

	/**
	 * Example of how to add turret commands to autonomous chooser.
	 */
	public void addTurretAutoCommands(/* AutoChooser autoChooser */) {
		// Example autonomous commands for turret
		// autoChooser.addOption("Turret Sweep", turretSweepCommand());
		// autoChooser.addOption("Turret Calibrate", turretCalibrateCommand());
		// autoChooser.addOption("Turret Test", turretTestCommand());
	}

	/**
	 * Example turret sweep command for autonomous.
	 */
	public Command turretSweepCommand() {
		return Commands.sequence(
				turret.goToLeft(),
				Commands.waitUntil(turret::isAtTarget),
				Commands.waitSeconds(0.5),
				turret.goToRight(),
				Commands.waitUntil(turret::isAtTarget),
				Commands.waitSeconds(0.5),
				turret.goToHome(),
				Commands.waitUntil(turret::isAtTarget));
	}

	/**
	 * Example turret calibration command.
	 */
	public Command turretCalibrateCommand() {
		return Commands.sequence(
				turret.goToHome(),
				Commands.waitUntil(turret::isAtTarget),
				Commands.waitSeconds(1.0),
				turret.goToLeft(),
				Commands.waitUntil(turret::isAtTarget),
				Commands.waitSeconds(1.0),
				turret.goToRight(),
				Commands.waitUntil(turret::isAtTarget),
				Commands.waitSeconds(1.0),
				turret.goToHome(),
				Commands.waitUntil(turret::isAtTarget));
	}

	/**
	 * Example turret test command.
	 */
	public Command turretTestCommand() {
		return Commands.sequence(
				Commands.runOnce(() -> System.out.println("Starting turret test")),
				turret.runVelocity(Math.PI / 4), // 45 degrees/second
				Commands.waitSeconds(2.0),
				turret.stop(),
				Commands.waitSeconds(0.5),
				turret.runOpenLoop(0.3), // 30% power
				Commands.waitSeconds(1.0),
				turret.stop(),
				Commands.runOnce(() -> System.out.println("Turret test complete")));
	}

	/**
	 * Teleop initialization for turret.
	 */
	public void teleopInit() {
		// Ensure turret is in a safe state at start of teleop
		turret.goToHome();
		System.out.println("Turret initialized - ready for keyboard control");
		System.out.println("Use iMac arrow keys (or Xbox controller 2) to control turret:");
		System.out.println("  Left Arrow: Turn left");
		System.out.println("  Right Arrow: Turn right");
		System.out.println("  Up Arrow: Go to home");
		System.out.println("  Down Arrow: Stop turret");
	}
}