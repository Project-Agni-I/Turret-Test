package frc.robot.subsystems.elevator;

public interface ElevatorIO {
	void setElevatorPosition(double position);

	double getElevatorPosition();

	boolean isElevatorAtTarget();
}
