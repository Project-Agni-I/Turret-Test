package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.Logger;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
	private IntakeIO io;
	private IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

	private IntakeState state = IntakeState.IDLE;

	public Intake(IntakeIO io) {
		this.io = io;
	}

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("intake", inputs);
		SmartDashboard.putBoolean("Intake", inputs.coralSensed);

		switch (state) {
			case IDLE:
				io.setSpeeds(0.0);
				break;
			case INTAKE_CORAL:
				if (!inputs.coralSensed) {
					io.setSpeeds(-0.25);
				} else {
					io.setSpeeds(0.0);
				}
				break;
			case EJECT_CORAL:
				io.setSpeeds(-0.9);
				break;
			case OUTTAKE_CORAL:
				io.setSpeeds(0.9);
				break;
		}

		Logger.recordOutput("intake/state", state);
		Logger.recordOutput("intake/coralMotorSpeed", inputs.coralMotorSpeed);
	}

	public enum IntakeState {
		IDLE, INTAKE_CORAL, EJECT_CORAL, OUTTAKE_CORAL
	}

	public Command setState(IntakeState m_state) {
		return Commands.runOnce(() -> this.state = m_state);
	}

	public Command runManual() {
		return Commands.runEnd(() -> io.setSpeeds(-.9), () -> io.setSpeeds(0));
	}

}
