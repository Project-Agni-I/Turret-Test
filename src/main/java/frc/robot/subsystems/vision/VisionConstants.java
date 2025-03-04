package frc.robot.subsystems.vision;

import java.util.List;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public final class VisionConstants {

	public static record CameraParams(
			String name,
			Transform3d transforms) {
	};

	public static final String[] cameraNames = {
			"FL",
			"FR",
			// "BL",
			// "BR"
	};

	public static final Transform3d[] vehicleToCameras = {
			new Transform3d(new Translation3d(Units.inchesToMeters(-10.5), Units.inchesToMeters(11.801),
					Units.inchesToMeters(12.75)), new Rotation3d(0, 0, Units.degreesToRadians(360 - 45))), // Fl
			new Transform3d(new Translation3d(Units.inchesToMeters(10.5), Units.inchesToMeters(11.801),
					Units.inchesToMeters(12.75)), new Rotation3d(0, 0, Units.degreesToRadians(45))), // FR
			// Add additional cameras here
	};

	public static final List<CameraParams> cameras = List.of(
			new CameraParams(cameraNames[0], vehicleToCameras[0]),
			new CameraParams(cameraNames[1], vehicleToCameras[1]));

	// Field Layout for visual localization and map generation
	public static AprilTagFieldLayout aprilTagLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);
}
