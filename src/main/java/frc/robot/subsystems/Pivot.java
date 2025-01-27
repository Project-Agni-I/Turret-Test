package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.PivotConstants;

public class Pivot extends SubsystemBase {
	private static TalonFX pivotMotor;
	private static PositionVoltage position;
	private double targetPositionPivot;
	private PIDController pivotPIDController;

	public Pivot() {
		pivotMotor = new TalonFX(MotorConstants.MOTOR_PIVOT_ID);
		position = new PositionVoltage(PivotConstants.PIVOT_VOLTAGE);
		targetPositionPivot = PivotConstants.PIVOT_ANGLE_POS_3;
		pivotPIDController = new PIDController(0.1, 0.0, 0.0);

	}

	public static double convertDegreesToRotations(double degrees) {
		return (degrees / 360.0);
	}

	public double convertRotationsToDegrees(double rotations) {
		return (rotations * 360.0);
	}

	public static Command movePivot(double pos) {
		return Commands.runOnce(() -> {
			pivotMotor.setControl(position.withPosition(convertDegreesToRotations(pos)));
		});

	}

}
