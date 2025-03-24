package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {
	private final PivotIO io;
	private PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
	private PivotState state = PivotState.POSITION_1;
	private double targetPositionPivot = PivotConstants.PIVOT_HOME_POSITION;

	public Pivot(PivotIO io) {
		this.io = io;
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("pivot", inputs);

		switch (state) {
			case POSITION_1:
				io.setPivotPosition(PivotConstants.POSITION_1);
				break;
			case POSITION_2:
				io.setPivotPosition(PivotConstants.POSITION_2);
				break;
			case POSITION_3:
				io.setPivotPosition(PivotConstants.POSITION_3);
				break;
			case POSITION_4:
				io.setPivotPosition(PivotConstants.POSITION_4);
				break;
			case GROUND_INTAKE:
				io.setPivotPosition(PivotConstants.GROUND_INTAKE);
				break;
			case ALGAE_HOLD:
				io.setPivotPosition(PivotConstants.ALGAE_HOLD);
				break;
			case ALGAE_INTAKE:
				io.setPivotPosition(PivotConstants.ALGAE_INTAKE);
				break;
			case PROCESSOR:
				io.setPivotPosition(PivotConstants.PROCESSOR);
				break;
		}

		Logger.recordOutput("pivot/currentPosition", inputs.position);
		Logger.recordOutput("pivot/state", state);
		Logger.recordOutput("pivot/targetPosition", targetPositionPivot);
	}

	public enum PivotState {
		POSITION_1, POSITION_2, POSITION_3, POSITION_4, GROUND_INTAKE, ALGAE_HOLD, ALGAE_INTAKE, PROCESSOR
	}

	public Command setState(PivotState newState) {
		return Commands.runOnce(() -> this.state = newState);
	}

	public Command setTargetPos(double pos) {
		return Commands.runOnce(() -> targetPositionPivot = pos);
	}
}
