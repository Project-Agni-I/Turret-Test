package frc.robot.subsystems.ball;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.ball.BallIO;
import frc.robot.subsystems.ball.BallIOInputsAutoLogged;

public class Ball extends SubsystemBase {
	private final BallIO io;
	private BallIOInputsAutoLogged inputs = new BallIOInputsAutoLogged();
	private BallState state = BallState.STOPPED;

	public Ball(BallIO io) {
		this.io = io;
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("ball", inputs);

		Logger.recordOutput("ball/state", state);
		Logger.recordOutput("ball/leftMotorSpeed", inputs.leftMotorSpeed);
		Logger.recordOutput("ball/rightMotorSpeed", inputs.rightMotorSpeed);
	}

	public enum BallState {
		INTAKING, OUTTAKING, STOPPED
	}

	public Command setState(BallState newState) {
		return Commands.runOnce(() -> this.state = newState);
	}

	public void setStateNonCommand(BallState newState) {
		this.state = newState;
	}

	public Command intake() {
		return Commands.runEnd(
				() -> {
					io.intake();
					state = BallState.INTAKING;
				},
				() -> {
					io.stop();
					state = BallState.STOPPED;
				});
	}

	public Command outtake() {
		return Commands.runEnd(
				() -> {
					io.outtake();
					state = BallState.OUTTAKING;
				},
				() -> {
					io.stop();
					state = BallState.STOPPED;
				});
	}

	public Command stop() {
		return Commands.runOnce(() -> {
			io.stop();
			state = BallState.STOPPED;
		});
	}

	public Command shoot() {
		return Commands.runEnd(
				() -> {
					io.shoot();
					state = BallState.OUTTAKING;
				},
				() -> {
					io.stop();
					state = BallState.STOPPED;
				});
	}

	public BallState getState() {
		return state;
	}
}
