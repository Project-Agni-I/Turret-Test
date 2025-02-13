package frc.robot.subsystems.Pivot;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorConstants;
import frc.robot.Constants.PivotConstants;
import frc.robot.subsystems.Pivot.PivotIO.PivotIOInputs;

public class Pivot extends SubsystemBase {
	private final PivotIO io;
	private PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();
	private double targetPositionPivot;

	public Pivot(PivotIO io) {
		this.io = io;
		targetPositionPivot = 0;
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("pivot", inputs);

		io.setPivotPosition(targetPositionPivot);

		Logger.recordOutput("pivot/targetPos", targetPositionPivot);
		Logger.recordOutput("pivot/currentPos", inputs.position);
		Logger.recordOutput("pivot/voltage", inputs.voltage);
	}

}
