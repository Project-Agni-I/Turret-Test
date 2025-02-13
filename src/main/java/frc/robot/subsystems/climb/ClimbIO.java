package frc.robot.subsystems.climb;

public interface ClimbIO {
	void setClimbPosition(double position);

	double getClimbPosition();

	boolean isClimbAtTarget();
}
