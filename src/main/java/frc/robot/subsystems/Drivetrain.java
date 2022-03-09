// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.networktables.NetworkTableEntry;
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

  private final Pigeon2 m_gyro = new Pigeon2(DriveConstants.kPigeonID);

  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");
  private final NetworkTableEntry m_toggle;
  private final NetworkTableEntry m_maxSpeed;

  public boolean toggleFieldDrive = false;
  public double tempSpeed;

  /**
   * Drivetrain Subsystem
   */
  public Drivetrain() {
    m_frontLeftMotor.setNeutralMode(NeutralMode.Coast);
    m_rearLeftMotor.setNeutralMode(NeutralMode.Coast);
    m_frontRightMotor.setNeutralMode(NeutralMode.Coast);
    m_rearRightMotor.setNeutralMode(NeutralMode.Coast);

    // m_frontLeftMotor.setNeutralMode(NeutralMode.Brake);
    // m_rearLeftMotor.setNeutralMode(NeutralMode.Brake);
    // m_frontRightMotor.setNeutralMode(NeutralMode.Brake);
    // m_rearRightMotor.setNeutralMode(NeutralMode.Brake);

    m_frontLeftMotor.setInverted(InvertType.None);
    m_rearLeftMotor.setInverted(InvertType.None);
    m_frontRightMotor.setInverted(InvertType.InvertMotorOutput);
    m_rearRightMotor.setInverted(InvertType.InvertMotorOutput);

    m_frontLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    m_rearLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    m_frontRightMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    m_rearRightMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    m_drive.setDeadband(0.1);

    m_orchestra.addInstrument(m_frontLeftMotor);
    m_orchestra.addInstrument(m_rearLeftMotor);
    m_orchestra.addInstrument(m_frontRightMotor);
    m_orchestra.addInstrument(m_rearRightMotor);

    m_orchestra.loadMusic("motherland.chrp");

    m_toggle = m_tab.add("Field Drive", toggleFieldDrive).withPosition(4, 0).getEntry();
    m_tab.add("Drivetrain", m_drive).withPosition(0, 0).withSize(4, 2);
    // m_tab.add("Gyro", m_gyro).withPosition(0, 2).withSize(2, 2).withWidget(BuiltInWidgets.kGyro);
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

  public void setNeutralMode(NeutralMode mode) {
    m_frontLeftMotor.setNeutralMode(mode);
    m_rearLeftMotor.setNeutralMode(mode);
    m_frontRightMotor.setNeutralMode(mode);
    m_rearRightMotor.setNeutralMode(mode);
  }

  /**
   * Drive method to drive the robot using voltages.
   * <p>Can only move forward and backwards.
   * @param voltage The voltage for the motors.
   */
  public void driveVoltage(double voltage) {
    m_frontLeftMotor.setVoltage(voltage);
    m_rearLeftMotor.setVoltage(voltage);
    m_frontRightMotor.setVoltage(voltage);
    m_rearRightMotor.setVoltage(voltage);
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
   * Gets the average distance of the two right wheels.
   * @return The distance in meters.
   */
  public double getRightDistance() {
    return ((-m_frontRightMotor.getSelectedSensorPosition() / DriveConstants.kEncoderCPR / DriveConstants.kGearRatio) * DriveConstants.kWheelCircumferenceMeters);
  }

  /**
   * Gets the average distance of the two left wheels.
   * @return The distance in meters.
   */
  public double getLeftDistance() {
    return ((-m_frontLeftMotor.getSelectedSensorPosition() / DriveConstants.kEncoderCPR / DriveConstants.kGearRatio) * DriveConstants.kWheelCircumferenceMeters);

  }

  /**
   * Gets the average distance of both sides of the robot.
   * @return The distance in meters.
   */
  public double getAverageDistance() {
    return (getRightDistance() + getLeftDistance()) / 2;
  }

  /**
   * Resets all the encoders to zero.
   */
  public void resetEncoders() {
    m_frontLeftMotor.setSelectedSensorPosition(0);
    m_rearLeftMotor.setSelectedSensorPosition(0);
    m_frontRightMotor.setSelectedSensorPosition(0);
    m_rearRightMotor.setSelectedSensorPosition(0);
  }

  /**
   * Returns the current angle value (in degrees, from -180 to 180) reported by the sensor.
   * @return The current angle value in degrees (-180 to 180).
   */
  public double getGyroAngle() {
    return m_gyro.getYaw();
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
   * Resets the gyro.
   */
  public void resetGyro() {
    m_gyro.setYaw(0);
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
