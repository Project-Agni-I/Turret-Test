package frc.robot.subsystems.intake;

public class IntakeIOSim implements IntakeIO {

	private double algaeMotorSpeed;
	private double coralMotorSpeed;
	private double combinationMotorSpeed;

	public void setAlgaeMotorSpeed(double speed) {
		algaeMotorSpeed = speed;
	}

	public void setCoralMotorSpeed(double speed) {
		coralMotorSpeed = speed;
	}

	public void setCombinationMotorSpeed(double speed) {
		combinationMotorSpeed = speed;
	}

	public double getAlgaeMotorSpeed() {
		return algaeMotorSpeed;
	}

	public double getCoralMotorSpeed() {
		return coralMotorSpeed;
	}

	public double getCombinationMotorSpeed() {
		return combinationMotorSpeed;
	}
}
