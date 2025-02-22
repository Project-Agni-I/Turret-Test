package frc.robot.subsystems.climb;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climb extends SubsystemBase {
	private final ClimbIO io;
	private ClimbIOInputsAutoLogged inputs = new ClimbIOInputsAutoLogged();
	private static double position;

	public Climb(ClimbIO io) {
		this.io = io;
		position = 0.0;
	}

	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("climb", inputs);

		io.setClimbPosition(position);

		Logger.recordOutput("climb/targetPos", position);
	}

	public void setTargetPos(double pos) {
		position = pos;
	}

	public ClimbIOReal getClimbIOReal() {
		if (io instanceof ClimbIOReal) {
			return (ClimbIOReal) io;
		} else {
			return null;
		}
	}
}
