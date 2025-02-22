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
import frc.robot.Constants.AlgaeConstants;
import frc.robot.Constants.CombinationConstants;
import frc.robot.Constants.CoralConstants;
import frc.robot.Constants.MotorConstants;

public class Intake extends SubsystemBase {
	private IntakeIO io;
	private IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();

	private IntakeState state = IntakeState.IDLE;

	private final Timer timer = new Timer();
	private Debouncer currentDebouncer = new Debouncer(0.5, DebounceType.kRising);
	// private boolean algaeIntakeStarted;
	private boolean hasAlgae = false;

	// public Intake(IntakeIO io) {
	// this.io = io;
	// algaeIntakeStarted = false;
	// timer.reset();
	// timer.start();
	// }

	@Override
	public void periodic() {
		io.updateInputs(inputs);
		Logger.processInputs("intake", inputs);
		// SmartDashboard.putNumber("Amps", inputs.algaeCurrent);
		// SmartDashboard.putBoolean("Current",
		// (currentDebouncer.calculate(inputs.algaeCurrent > 45)));
		// SmartDashboard.putBoolean("Algae", hasAlgae);

		switch (state) {
			case IDLE:
				// io.setSpeeds(0, 0, 0);
				// algaeIntakeStarted = false;
				// hasAlgae = false;
				// break;
			case INTAKE_CORAL:
				if (!inputs.coralSensed) {
					io.setSpeeds(0.9, 0, 0.9);
				} else {
					io.setSpeeds(0, 0, 0);
				}
				break;
			case INTAKE_ALGAE:
				// if (!currentDebouncer.calculate(inputs.algaeCurrent > 30)) {
				// io.setSpeeds(0, 0.9, 0.9);
				// //algaeIntakeStarted = false;
				// hasAlgae = true;
				// } else {
				// io.setSpeeds(0, 0, 0);
				// }
				break;
			case EJECT_CORAL:
				io.setSpeeds(0, -0.9, 0);
				break;
			// case EJECT_ALGAE:
			// io.setSpeeds(0, -0.9, -0.9);
			// break;
			case OUTTAKE_CORAL:
				io.setSpeeds(0, -0.9, -0.9);
				break;
		}

		Logger.recordOutput("intake/state", state);
		// Logger.recordOutput("intake/algaeMotorSpeed", inputs.algaeMotorSpeed != 0);
		Logger.recordOutput("intake/coralMotorSpeed", inputs.coralMotorSpeed != 0);
		Logger.recordOutput("intake/topRollerMotorSpeed", inputs.topRollerMotorSpeed != 0);
	}

	public enum IntakeState {
		IDLE, INTAKE_CORAL, INTAKE_ALGAE, EJECT_CORAL, EJECT_ALGAE, OUTTAKE_CORAL
	}

	public Command setState(IntakeState m_state) {
		return Commands.run(() -> this.state = m_state);
	}
}
