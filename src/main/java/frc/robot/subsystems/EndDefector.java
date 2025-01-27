package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AlgaeConstants;
import frc.robot.Constants.CombinationConstants;
import frc.robot.Constants.CoralConstants;
import frc.robot.Constants.MotorConstants;

public class EndDefector extends SubsystemBase {
	private static TalonFX algaeMotor;
	public static TalonFX coralMotor;
	public static TalonFX combinationMotor;

	public EndDefector() {
		algaeMotor = new TalonFX(MotorConstants.MOTOR_ALGAE_ID);
		coralMotor = new TalonFX(MotorConstants.MOTOR_CORAL1_ID);
		combinationMotor = new TalonFX(MotorConstants.MOTOR_COMBINATION_ID);
	}

	public static Command runCoralIntake() {
		return Commands.run(() -> {
			combinationMotor.set((CombinationConstants.COMBINATION_INTAKE_MOTOR_SPEED));
			coralMotor.set((CoralConstants.CORAL_INTAKE_MOTOR_SPEED));
		});
	}

	public static Command runCoralOuttake() {
		return Commands.run(() -> {
			combinationMotor.set((CombinationConstants.COMBINATION_OUTTAKE_MOTOR_SPEED));
			coralMotor.set((CoralConstants.CORAL_OUTTAKE_MOTOR_SPEED));
		});
	}

	public static Command stopCoralIntake() {
		return Commands.run(() -> {
			combinationMotor.set((CombinationConstants.COMBINATION_STOP_MOTOR_SPEED));
			coralMotor.set((CoralConstants.CORAL_STOP_MOTOR_SPEED));
		});
	}

	public static Command runAlgaeIntake() {
		return Commands.run(() -> {
			algaeMotor.set((AlgaeConstants.ALGAE_INTAKE_MOTOR_SPEED));
			combinationMotor.set((CombinationConstants.COMBINATION_INTAKE_MOTOR_SPEED));
		});
	}

	public static Command runAlgaeOuttake() {
		return Commands.run(() -> {
			algaeMotor.set((AlgaeConstants.ALGAE_OUTTAKE_MOTOR_SPEED));
			combinationMotor.set((CombinationConstants.COMBINATION_OUTTAKE_MOTOR_SPEED));
		});
	}

	public static Command stopAlgaeIntake() {
		return Commands.run(() -> {
			algaeMotor.set((AlgaeConstants.ALGAE_STOP_MOTOR_SPEED));
			combinationMotor.set((CombinationConstants.COMBINATION_STOP_MOTOR_SPEED));
		});
	}

}
