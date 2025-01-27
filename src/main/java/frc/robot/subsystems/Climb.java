package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorPosition;
import frc.robot.Constants.MotorConstants;

public class Climb extends SubsystemBase {
	private static TalonFX climbMotor;
	private static TalonFXConfiguration climbMotorConfig;
	private Encoder climbEncoder;
	private PIDController climbPIDController;
	private static PositionVoltage position;

	private double targetPosition;

	public Climb() {
		climbMotor = new TalonFX(MotorConstants.CLIMB_MOTOR_ID);
		climbEncoder = new Encoder(0, 1);
		climbPIDController = new PIDController(0.1, 0.0, 0.0);
		climbMotorConfig = new TalonFXConfiguration();
		targetPosition = Constants.ClimbConstants.CLIMB_POS;
		position = new PositionVoltage(0);

	}

	private ArmFeedforward feedforward = new ArmFeedforward(
			Constants.ClimbConstants.CLIMB_kA,
			Constants.ClimbConstants.CLIMB_kG,
			Constants.ClimbConstants.CLIMB_kS,
			Constants.ClimbConstants.CLIMB_kV);

	public static Command moveclimb(double pos) {
		return Commands.runOnce(() -> {
			climbMotor.setControl(position.withPosition(pos));
		});

	}

}
