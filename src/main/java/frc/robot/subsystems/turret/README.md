# Turret Subsystem with iMac Keyboard Controls

A 180-degree rotation turret subsystem for FRC robots with precise angular positioning control and iMac keyboard input support.

## Overview

The Turret subsystem provides:
- **180-degree rotation range** (±90° from center)
- **Single motor control** using TalonFX
- **iMac keyboard controls** using arrow keys
- **Precise positioning** with PID feedback
- **Comprehensive logging** in Advantage Scope
- **Soft limits** to prevent over-rotation
- **Multiple control modes** (position, velocity, open loop)

## iMac Keyboard Controls

### Arrow Key Mapping
- **Left Arrow (Key Code 37)**: Turn turret left by 1°
- **Right Arrow (Key Code 39)**: Turn turret right by 1°
- **Up Arrow (Key Code 38)**: Go to home position (0°)
- **Down Arrow (Key Code 40)**: Stop turret movement

### Xbox Controller Simulation (for testing)
Since direct keyboard input isn't available in FRC, we use Xbox Controller 2 to simulate iMac arrow keys:
- **Left Bumper**: Left Arrow
- **Right Bumper**: Right Arrow
- **Y Button**: Up Arrow
- **A Button**: Down Arrow

## Hardware Configuration

### Motor Controller
- **Type**: TalonFX (Falcon 500)
- **CAN ID**: 20 (configurable in `TurretConstants.java`)
- **Gear Ratio**: 20:1 (configurable)
- **Max Velocity**: 180 degrees/second
- **Max Acceleration**: 360 degrees/second²

### Encoder
- **Type**: CANcoder (absolute encoder)
- **CAN ID**: 21 (configurable in `TurretConstants.java`)
- **Range**: ±90 degrees (180° total range)
- **Direction**: Counter-clockwise positive

## Files

- `TurretConstants.java` - Configuration constants and keyboard settings
- `TurretIO.java` - Interface with keyboard input logging
- `TurretIOReal.java` - Real hardware implementation with Xbox controller simulation
- `TurretIOSim.java` - Simulation implementation with keyboard controls
- `Turret.java` - Main subsystem with keyboard input handling
- `TurretRobotContainerExample.java` - Integration example for RobotContainer
- `README.md` - This documentation

## Usage

### Basic Keyboard Commands

```java
// The turret automatically responds to iMac arrow keys:
// Left Arrow: Turn left by 1°
// Right Arrow: Turn right by 1°
// Up Arrow: Go to home (0°)
// Down Arrow: Stop turret
```

### Programmatic Commands

```java
// Go to preset positions
turret.goToHome();    // 0°
turret.goToLeft();    // -90°
turret.goToRight();   // 90°

// Set custom position (within ±90° limits)
turret.setPosition(45.0);  // 45 degrees
turret.setPosition(-30.0); // -30 degrees

// Velocity control
turret.runVelocity(Math.PI / 2); // 90 degrees/second

// Open loop control
turret.runOpenLoop(0.5); // 50% power

// Stop turret
turret.stop();
```

### State Management

The turret operates in different states:
- **HOME**: Moves to home position (0°)
- **LEFT**: Moves to left position (-90°)
- **RIGHT**: Moves to right position (90°)
- **MANUAL**: Manual position control
- **KEYBOARD**: Keyboard control active
- **STOPPED**: No movement

## Configuration

Key constants in `TurretConstants.java`:

```java
// PID Constants
public static final double TURRET_P = 0.5;
public static final double TURRET_I = 0.0;
public static final double TURRET_D = 0.1;

// Feedforward Constants
public static final double TURRET_kS = 0.1;  // Static friction
public static final double TURRET_kG = 0.05; // Gravity compensation
public static final double TURRET_kV = 0.3;  // Velocity feedforward
public static final double TURRET_kA = 0.0;  // Acceleration feedforward

// Hardware Constants
public static final int TURRET_MOTOR_ID = 20;
public static final int TURRET_ENCODER_ID = 21;
public static final double TURRET_GEAR_RATIO = 20.0;

// Position Constants (180-degree range)
public static final double TURRET_HOME_POSITION = 0.0;
public static final double TURRET_LEFT_LIMIT = -90.0;   // 90 degrees left
public static final double TURRET_RIGHT_LIMIT = 90.0;   // 90 degrees right

// Soft Limits
public static final double TURRET_MIN_ANGLE = -90.0;
public static final double TURRET_MAX_ANGLE = 90.0;

// Keyboard control constants
public static final double TURRET_KEYBOARD_SPEED = 0.3;
public static final double TURRET_KEYBOARD_INCREMENT = 1.0; // Degrees per key press
```

## Integration with RobotContainer

### Basic Integration

```java
// In RobotContainer.java
private final Turret turret;

public RobotContainer() {
    // Create turret with appropriate IO implementation
    switch (Constants.currentMode) {
        case REAL:
            turret = new Turret(new TurretIOReal());
            break;
        case SIM:
            turret = new Turret(new TurretIOSim());
            break;
        default:
            turret = new Turret(new TurretIO() {});
            break;
    }
    
    // Enable keyboard control by default
    turret.setDefaultCommand(turret.enableKeyboardControl());
}

// In Robot.java teleopInit()
@Override
public void teleopInit() {
    turret.goToHome(); // Ensure safe starting position
}
```

### Advanced Integration

See `TurretRobotContainerExample.java` for complete integration example with:
- Multiple controller bindings
- Autonomous commands
- Emergency stop functionality
- Comprehensive button mapping

## Advantage Scope Logging

The subsystem provides extensive logging for debugging and analysis:

### Input Logs
- `Turret/CurrentPosition` - Current turret angle in degrees
- `Turret/TargetPosition` - Target position in degrees
- `Turret/State` - Current turret state
- `Turret/AtTarget` - Whether turret has reached target
- `Turret/Velocity` - Current velocity in rad/s

### Keyboard Input Logs
- `Turret/KeyboardActive` - Whether keyboard control is active
- `Turret/KeyboardInputDegrees` - Keyboard input in degrees
- `Turret/LeftArrowPressed` - Left arrow key state
- `Turret/RightArrowPressed` - Right arrow key state
- `Turret/UpArrowPressed` - Up arrow key state
- `Turret/DownArrowPressed` - Down arrow key state

### Console Output
The subsystem also prints keyboard events to console:
```
Turret Keyboard Control - Target: 45.0°, Current: 44.2°, Input: 1.0°
Left Arrow Pressed - Turning turret left
Right Arrow Pressed - Turning turret right
Up Arrow Pressed - Going to home position
Down Arrow Pressed - Stopping turret
```

## Safety Features

- **180-degree limits**: Prevents turret from rotating beyond ±90°
- **Soft limits**: Hardware-level position limits
- **Connection monitoring**: Alerts when motor or encoder is disconnected
- **Temperature monitoring**: Tracks motor temperature
- **Brake mode**: Holds position when stopped
- **Position tolerance**: 2° tolerance for position control
- **Emergency stop**: Multiple ways to stop turret immediately

## Troubleshooting

### Common Issues

1. **Turret not responding to keyboard**: 
   - Check Xbox Controller 2 connection
   - Verify button mappings in `TurretIOReal.java`
   - Ensure keyboard control is enabled

2. **Turret not moving**: 
   - Check motor connection and CAN ID
   - Verify encoder configuration
   - Check soft limits

3. **Incorrect position**: 
   - Verify encoder configuration and gear ratio
   - Check for mechanical binding
   - Calibrate encoder zero position

4. **Oscillating movement**: 
   - Adjust PID constants (reduce P, increase D)
   - Check for mechanical backlash

### Tuning PID

1. Start with P = 0.1, I = 0.0, D = 0.0
2. Increase P until response is fast but not oscillating
3. Add D to reduce overshoot and oscillation
4. Add I only if steady-state error persists
5. Fine-tune feedforward constants for better performance

### Keyboard Testing

To test keyboard controls:
1. Connect Xbox Controller 2
2. Use controller buttons to simulate arrow keys
3. Monitor console output for keyboard events
4. Check Advantage Scope for input logging
5. Verify turret responds correctly to each key

## Autonomous Commands

Example autonomous commands for turret:

```java
// Turret sweep
public Command turretSweepCommand() {
    return Commands.sequence(
        turret.goToLeft(),
        Commands.waitUntil(turret::isAtTarget),
        Commands.waitSeconds(0.5),
        turret.goToRight(),
        Commands.waitUntil(turret::isAtTarget),
        Commands.waitSeconds(0.5),
        turret.goToHome(),
        Commands.waitUntil(turret::isAtTarget)
    );
}

// Turret calibration
public Command turretCalibrateCommand() {
    return Commands.sequence(
        turret.goToHome(),
        Commands.waitUntil(turret::isAtTarget),
        turret.goToLeft(),
        Commands.waitUntil(turret::isAtTarget),
        turret.goToRight(),
        Commands.waitUntil(turret::isAtTarget),
        turret.goToHome(),
        Commands.waitUntil(turret::isAtTarget)
    );
}
```

## Performance Specifications

- **Rotation Range**: 180° (±90° from center)
- **Max Velocity**: 180°/second
- **Max Acceleration**: 360°/second²
- **Position Accuracy**: ±2°
- **Response Time**: <100ms
- **Control Modes**: Position, Velocity, Open Loop
- **Input Methods**: iMac Arrow Keys, Xbox Controller, Programmatic 