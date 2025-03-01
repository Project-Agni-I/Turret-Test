package frc.robot.commands;

import java.lang.reflect.Field;
import java.util.Map;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.estimator.PoseEstimator3d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.drive.*;
import frc.robot.util.FieldConstants;

public class AutoAlign {

	public AutoAlign() {
	}

	public Command getAutoCommand(Pose2d pose) {

		// Create the constraints to use while pathfinding
		PathConstraints constraints = new PathConstraints(
				2.0, 2.0,
				Units.degreesToRadians(360), Units.degreesToRadians(360));

		// Since AutoBuilder is configured, we can use it to build pathfinding commands
		Command pathfindingCommand = AutoBuilder.pathfindToPose(
				pose,
				constraints,
				0.0// Goal end velocity in meters/sec
		);
		return pathfindingCommand;
	}

}