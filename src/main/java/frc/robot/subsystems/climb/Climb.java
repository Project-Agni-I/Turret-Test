package frc.robot.subsystems.climb;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.MotorConstants;
import org.littletonrobotics.junction.Logger;

public class Climb extends SubsystemBase {
	private static TalonFX climbMotor;
	private static Encoder climbEncoder;
	private static PIDController climbPIDController;
	private static PositionVoltage position;

	private double targetPosition;
	private ArmFeedforward feedforward;

	public Climb(ClimbIO climbIO) {
		climbMotor = new TalonFX(MotorConstants.CLIMB_MOTOR_ID);
		climbEncoder = new Encoder(0, 1);
		climbPIDController = new PIDController(0.1, 0.0, 0.0);
		position = new PositionVoltage(0);

		feedforward = new ArmFeedforward(
				Constants.ClimbConstants.CLIMB_kA,
				Constants.ClimbConstants.CLIMB_kG,
				Constants.ClimbConstants.CLIMB_kS,
				Constants.ClimbConstants.CLIMB_kV);

		Logger.start();
		Logger.recordOutput("Climb/TargetPosition", targetPosition);
	}

	public static Command moveClimb(double pos) {
		return Commands.runOnce(() -> {
			Logger.recordOutput("Climb/MoveToPosition", pos);
			climbMotor.setControl(position.withPosition(pos));
			Logger.recordOutput("Climb/EncoderPosition", climbEncoder.getDistance());
		});
	}

	@Override
	public void periodic() {
		Logger.recordOutput("Climb/CurrentPosition", climbEncoder.getDistance());
		Logger.recordOutput("Climb/Speed", climbEncoder.getRate());
	}

	public void moveToTargetPosition() {
		moveClimb(targetPosition).schedule();
	}
}
