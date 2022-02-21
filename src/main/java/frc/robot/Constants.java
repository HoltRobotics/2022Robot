// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        public static final int kFrontLeftMotor = 1;
        public static final int kRearLeftMotor = 2;
        public static final int kFrontRightMotor = 3;
        public static final int kRearRightMotor = 4;

        public static final double kDefaultSpeed = 1.0;
        public static final double kSlowDriveSpeed = 0.5;
        public static final double kTurningSlowDown = 0.75;
    }

    public static final class IntakeConstants {
        public static final int kSideIntakeMotor = 5;
        public static final int kFrontIntakeMotor = 6;
        public static final double kIntakeMotorSpeed = 0.5;
    }

    public static final class FeederConstants {
        public static final int kBeltFeederMotor = 7;
        public static final int kFrontFeederMotor = 8;
        public static final double kFeederMotorSpeed = 1.0;
    }

    public static final class ShooterConstants {
        public static final int kShooterMotor = 9;
        public static final double kShooterMotorSpeed = 0.75;
        //TODO: calculate theses for the robot
        public static final double kS = 0.4;
        public static final double kV = 0.1;
        public static final double kP = 0.0;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
    }

    public static final class ClimbConstants {
        public static final int kArmMotor = 10;
        public static final int kHookMotor = 11;
        public static final double kArmMotorSpeed = 0.5;
        public static final double kHookMotorSpeed = 1.0;
    }

    public static final class OIConstants {
        public static final int kXboxDriverController = 0;
        public static final int kFlightDriverController = 1;
        public static final int kOperatorController = 2;
    }
}
