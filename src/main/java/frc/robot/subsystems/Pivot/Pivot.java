package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.pivot.*;

public class Pivot extends SubsystemBase {
	private final PivotIO io;
	private PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
	private double targetPositionPivot = PivotConstants.PIVOT_HOME_POSITION;

	public Pivot(PivotIO io) {
		this.io = io;
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("pivot", inputs);

		io.setPivotPosition(targetPositionPivot);

		Logger.recordOutput("pivot/currentPosition", inputs.position);
		Logger.recordOutput("pivot/targetPosition", targetPositionPivot);
	}

	public Command setTargetPos(double pos) {
		return Commands.runOnce(() -> targetPositionPivot = pos);
	}

}
