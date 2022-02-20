// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
  private final WPI_TalonFX m_frontLeftMotor = new WPI_TalonFX(DriveConstants.kFrontLeftMotor);
  private final WPI_TalonFX m_rearLeftMotor = new WPI_TalonFX(DriveConstants.kRearLeftMotor);
  private final WPI_TalonFX m_frontRightMotor = new WPI_TalonFX(DriveConstants.kFrontRightMotor);
  private final WPI_TalonFX m_rearRightMotor = new WPI_TalonFX(DriveConstants.kRearRightMotor);

  private MecanumDrive m_drive = new MecanumDrive(m_frontLeftMotor, m_rearLeftMotor, m_frontRightMotor, m_rearRightMotor);

  private final Orchestra m_orchestra = new Orchestra();

  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");
  private final NetworkTableEntry m_toggle;
  private final NetworkTableEntry m_maxSpeed;

  public boolean toggleFieldDrive = false;
  public double tempSpeed;

  /**
   * Drivetrain Subsystem
   */
  public Drivetrain() {
    m_frontLeftMotor.setNeutralMode(NeutralMode.Brake);
    m_rearLeftMotor.setNeutralMode(NeutralMode.Brake);
    m_frontRightMotor.setNeutralMode(NeutralMode.Brake);
    m_rearRightMotor.setNeutralMode(NeutralMode.Brake);

    m_frontLeftMotor.setInverted(InvertType.None);
    m_rearLeftMotor.setInverted(InvertType.None);
    m_frontRightMotor.setInverted(InvertType.InvertMotorOutput);
    m_rearRightMotor.setInverted(InvertType.InvertMotorOutput);

    m_drive.setDeadband(0.1);

    m_orchestra.addInstrument(m_frontLeftMotor);
    m_orchestra.addInstrument(m_rearLeftMotor);
    m_orchestra.addInstrument(m_frontRightMotor);
    m_orchestra.addInstrument(m_rearRightMotor);

    m_orchestra.loadMusic("motherland.chrp");

    m_toggle = m_tab.add("Field Drive", toggleFieldDrive).withPosition(4, 0).getEntry();
    m_tab.add("Drivetrain", m_drive).withPosition(0, 0).withSize(4, 2);
    m_tab.add("Gyro", m_gyro).withPosition(0, 2).withSize(2, 2).withWidget(BuiltInWidgets.kGyro);
    m_maxSpeed = m_tab.add("Max Speed", 1.0).withPosition(2, 2).withSize(2, 1).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();

    m_maxSpeed.setDouble(DriveConstants.kDefaultSpeed);
  }

  /**
   * Drive method for Mecanum platform.
   * <p>Angles are measured clockwise from the positive X axis. The robot's speed is independent from its angle or rotation rate.
   * @param ySpeed The robot's speed along the Y axis [-1.0..1.0]. Forward is positive.
   * @param xSpeed The robot's speed along the X axis [-1.0..1.0]. Right is positive.
   * @param zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is positive.
   */
  public void driveCartesian(double ySpeed, double xSpeed, double zRotation) {
    m_drive.driveCartesian(ySpeed, xSpeed, zRotation * DriveConstants.kTurningSlowDown);
  }

  /**
   * Drive method for Mecanum platform.
   * <p>Angles are measured clockwise from the positive X axis. The robot's speed is independent from its angle or rotation rate.
   * @param ySpeed The robot's speed along the Y axis [-1.0..1.0]. Forward is positive.
   * @param xSpeed The robot's speed along the X axis [-1.0..1.0]. Right is positive.
   * @param zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is positive.
   * @param gyroAngle The current angle reading from the gyro in degrees around the Z axis. Use this to implement field-oriented controls.
   */
  public void driveCartesian(double ySpeed, double xSpeed, double zRotation, double gyroAngle) {
    m_drive.driveCartesian(ySpeed, xSpeed, zRotation * DriveConstants.kTurningSlowDown, gyroAngle);
  }

  /**
   * Configure the scaling factor for using drive method.
   * <p>Default value is 1.0.
   * @param maxSpeed Multiplied with the output percentage computed by the drive functions.
   */
  public void setMaxSpeed(double maxSpeed) {
    m_drive.setMaxOutput(maxSpeed);
  }

  /**
   * Returns the current angle value (in degrees, from -180 to 180) reported by the sensor.
   * @return The current angle value in degrees (-180 to 180).
   */
  public double getGyroAngle() {
    return m_gyro.getRoll();
  }

  /**
   * Toggles between field-oriented contols and not.
   * <p>Updates on the ShuffleBoard.
   */
  public void toggleFieldDrive() {
    toggleFieldDrive = !toggleFieldDrive;
    m_toggle.setBoolean(toggleFieldDrive);
  }

  /**
   * Returns the current driving mode.
   * 
   * <p>true = field-oriented controls
   * <p>false = NOT field-oriented controls
   * @return the current driving mode.
   */
  public boolean getFieldDriveMode() {
    return toggleFieldDrive;
  }

  /**
   * Plays the music file that's loaded. If the player is paused, this will resume. This will also resume a song if the orchestra was interrupted.
   */
  public void playMusic() {
    m_orchestra.play();
  }

  /**
   * Returns whether the current track is actively playing or not.
   * @return True if playing, false otherwise
   */
  public boolean isPlayingMusic() {
    return m_orchestra.isPlaying();
  }

  /**
   * Stops the music file that's loaded. This resets the current position in the track to the start.
   */
  public void stopMusic() {
    m_orchestra.stop();
  }

  /**
   * Reset the Yaw gyro.
   */
  public void resetGyro() {
    m_gyro.reset();
  }

  /**
   * Slows down the robot drive by the Slow Drive Speed Constant.
   * Will return to the original speed before method was called.
   */
  public void slowDriveSpeed() {
    tempSpeed = m_maxSpeed.getDouble(1.0);
    m_maxSpeed.setDouble(tempSpeed * DriveConstants.kSlowDriveSpeed);
  }

  /**
   * Returns the drive speed to the original speed.
   */
  public void normalDriveSpeed() {
    m_maxSpeed.setDouble(tempSpeed);
  }

  /**
   * Stops the drive motors.
   */
  public void stopDrive() {
    m_drive.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setMaxSpeed(m_maxSpeed.getDouble(1.0));
  }
}
