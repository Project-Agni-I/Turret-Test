package frc.robot.subsystems.pivot;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorConstants;
import frc.robot.subsystems.pivot.PivotIOInputsAutoLogged;
import frc.robot.subsystems.pivot.PivotIO;
import frc.robot.subsystems.pivot.PivotIO.PivotIOInputs;
import frc.robot.subsystems.pivot.PivotConstants;

public class Pivot extends SubsystemBase {
	private final PivotIO io;
	private PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
	private double targetPositionPivot;

	public Pivot(PivotIO io) {
		this.io = io;
		targetPositionPivot = 0;
	}

	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("pivot", inputs);

		io.setPivotPosition(targetPositionPivot);

		Logger.recordOutput("pivot/targetPos", targetPositionPivot);

		SmartDashboard.putNumber("Pivot/Position", inputs.position);
		SmartDashboard.putNumber("Pivot/Voltage", inputs.voltage);

		Logger.recordOutput("pivot/currentPosition", inputs.position);
		Logger.recordOutput("pivot/targetPosition", targetPositionPivot);
	}

	public void setTargetPos(double pos) {
		targetPositionPivot = pos;
	}

	public double getPosition() {
		return inputs.position;
	}
}
