// Copyright (c) 2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package frc.robot.util;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

/**
 * Contains various field dimensions and useful reference points. All units are
 * in meters and poses
 * have a blue alliance origin.
 */
public class FieldConstants {

	public static final Pose2d targetPose1red = new Pose2d(14.409, 3.860, new Rotation2d(Units.degreesToRadians(180)));

	public static final Pose2d targetPose2red = new Pose2d(14.409, 4.160, new Rotation2d(Units.degreesToRadians(180)));

	public static final Pose2d targetPose3red = new Pose2d(13.900, 5.127,
			new Rotation2d(Units.degreesToRadians(-120.379)));

	public static final Pose2d targetPose4red = new Pose2d(13.621, 5.286,
			new Rotation2d(Units.degreesToRadians(-120.379)));

	public static final Pose2d targetPose5red = new Pose2d(12.494, 5.316,
			new Rotation2d(Units.degreesToRadians(-58.815)));

	public static final Pose2d targetPose6red = new Pose2d(12.225, 5.167,
			new Rotation2d(Units.degreesToRadians(-58.815)));

	public static final Pose2d targetPose7red = new Pose2d(11.607, 4.170, new Rotation2d(Units.degreesToRadians(0)));

	public static final Pose2d targetPose8red = new Pose2d(11.627, 3.850, new Rotation2d(Units.degreesToRadians(0)));

	public static final Pose2d targetPose9red = new Pose2d(12.145, 2.923,
			new Rotation2d(Units.degreesToRadians(53.902)));

	public static final Pose2d targetPose10red = new Pose2d(12.474, 2.754,
			new Rotation2d(Units.degreesToRadians(53.902)));
	public static final Pose2d targetPose11red = new Pose2d(13.621, 2.744,
			new Rotation2d(Units.degreesToRadians(123.690)));

	public static final Pose2d targetPose12red = new Pose2d(13.980, 2.853,
			new Rotation2d(Units.degreesToRadians(123.690)));

	public static final Pose2d targetPose1blue = new Pose2d(3.071, 4.170, new Rotation2d(Units.degreesToRadians(0)));

	public static final Pose2d targetPose2blue = new Pose2d(3.021, 3.870,
			new Rotation2d(Units.degreesToRadians(0.000)));

	public static final Pose2d targetPose3blue = new Pose2d(3.590, 2.853,
			new Rotation2d(Units.degreesToRadians(54.728)));

	public static final Pose2d targetPose4blue = new Pose2d(3.849, 2.714,
			new Rotation2d(Units.degreesToRadians(54.728)));

	public static final Pose2d targetPose5blue = new Pose2d(5.046, 2.734,
			new Rotation2d(Units.degreesToRadians(120.763)));

	public static final Pose2d targetPose6blue = new Pose2d(5.345, 2.853,
			new Rotation2d(Units.degreesToRadians(120.763)));

	public static final Pose2d targetPose7blue = new Pose2d(5.943, 3.860, new Rotation2d(Units.degreesToRadians(180)));

	public static final Pose2d targetPose8blue = new Pose2d(5.923, 4.180, new Rotation2d(Units.degreesToRadians(180)));

	public static final Pose2d targetPose9blue = new Pose2d(5.395, 5.247,
			new Rotation2d(Units.degreesToRadians(-121.891)));

	public static final Pose2d targetPose10blue = new Pose2d(5.105, 5.386,
			new Rotation2d(Units.degreesToRadians(-121.891)));
	public static final Pose2d targetPose11blue = new Pose2d(4.000, 5.486,
			new Rotation2d(Units.degreesToRadians(-65.979)));

	public static final Pose2d targetPose12blue = new Pose2d(3.670, 5.266,
			new Rotation2d(Units.degreesToRadians(-65.979)));
}
