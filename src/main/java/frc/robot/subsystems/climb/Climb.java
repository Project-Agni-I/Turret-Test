package frc.robot.subsystems.climb;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.pivot.PivotConstants;

public class Climb extends SubsystemBase {
	private final ClimbIO io;
	private ClimbIOInputsAutoLogged inputs = new ClimbIOInputsAutoLogged();
	private double position = ClimbConstants.CLIMB_HOME_POSITION;

	public Climb(ClimbIO io) {
		this.io = io;
		position = 0.0;
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("climb", inputs);

		// io.setClimbPosition(position);

		Logger.recordOutput("climb/targetPos", position);
	}

	public Command setTargetPos(double pos) {
		return Commands.run(() -> position = pos);
	}

	public double getPosition() {
		return inputs.position;
	}

	public Command runUp() {
		return Commands.runEnd(() -> io.runManualUp(), () -> io.stop());
	}

	public Command runDown() {
		return Commands.runEnd(() -> io.runManualDown(), () -> io.stop());
	}

	public Command stop() {
		return Commands.runEnd(() -> io.stop(), () -> io.stop());
	}
}
