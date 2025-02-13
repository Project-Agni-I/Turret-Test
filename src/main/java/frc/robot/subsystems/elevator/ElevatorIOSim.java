package frc.robot.subsystems.elevator;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import frc.robot.Constants;

public class ElevatorIOSim implements ElevatorIO {

	private final EncoderSim ElevatorEncoderSim;

	public ElevatorIOSim() {
		ElevatorEncoderSim = new EncoderSim(new Encoder(0, 1));
	}

	public void setElevatorPosition(double position) {
		ElevatorEncoderSim.setDistance(position);
	}

	public double getElevatorPosition() {
		return ElevatorEncoderSim.getDistance();
	}

	public boolean isElevatorAtTarget() {
		return (Math.abs(ElevatorEncoderSim.getDistance()
				- Constants.ElevatorPosition.LEVEL_1_POSITION) < Constants.ElevatorPosition.ELEVATOR_POSITION_TOLERANCE)
				||
				(Math.abs(ElevatorEncoderSim.getDistance()
						- Constants.ElevatorPosition.LEVEL_2_POSITION) < Constants.ElevatorPosition.ELEVATOR_POSITION_TOLERANCE)
				||
				(Math.abs(ElevatorEncoderSim.getDistance()
						- Constants.ElevatorPosition.LEVEL_3_POSITION) < Constants.ElevatorPosition.ELEVATOR_POSITION_TOLERANCE)
				||
				(Math.abs(ElevatorEncoderSim.getDistance()
						- Constants.ElevatorPosition.LEVEL_4_POSITION) < Constants.ElevatorPosition.ELEVATOR_POSITION_TOLERANCE);
	}

}
