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

  public boolean toggleFieldDrive = true;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_frontLeftMotor.setNeutralMode(NeutralMode.Brake);
    m_rearLeftMotor.setNeutralMode(NeutralMode.Brake);
    m_frontRightMotor.setNeutralMode(NeutralMode.Brake);
    m_rearRightMotor.setNeutralMode(NeutralMode.Brake);

    m_frontLeftMotor.setInverted(InvertType.None);
    m_rearLeftMotor.setInverted(InvertType.None);
    m_frontRightMotor.setInverted(InvertType.InvertMotorOutput);
    m_rearRightMotor.setInverted(InvertType.InvertMotorOutput);

    m_drive.setMaxOutput(DriveConstants.kMaxSpeed);

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
  }

  public void driveCartesian(double ySpeed, double xSpeed, double zRotation) {
    m_drive.driveCartesian(ySpeed, xSpeed, zRotation);
  }

  public void driveCartesian(double ySpeed, double xSpeed, double zRotation, double gyroAngle) {
    m_drive.driveCartesian(ySpeed, xSpeed, zRotation, gyroAngle);
  }

  public void setMaxSpeed(double maxSpeed) {
    m_drive.setMaxOutput(maxSpeed);
  }

  public double getGyroAngle() {
    return m_gyro.getAngle();
  }

  public void toggleFieldDrive() {
    toggleFieldDrive = !toggleFieldDrive;
    m_toggle.setBoolean(toggleFieldDrive);
  }

  public boolean getFieldDriveMode() {
    return toggleFieldDrive;
  }

  public void playMusic() {
    m_drive.close();
    m_orchestra.play();
  }

  public boolean isPlayingMusic() {
    return m_orchestra.isPlaying();
  }

  public void stopMusic() {
    m_orchestra.pause();
    m_drive = new MecanumDrive(m_frontLeftMotor, m_rearLeftMotor, m_frontRightMotor, m_rearRightMotor);
  }

  public void resetGyro() {
    m_gyro.reset();
  }

  public void slowDriveSpeed() {
    m_drive.setMaxOutput(DriveConstants.kSlowDriveSpeed);
  }

  public void normalDriveSpeed() {
    m_drive.setMaxOutput(1.0);
  }

  public void stopDrive() {
    m_drive.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setMaxSpeed(m_maxSpeed.getDouble(1.0));
  }
}
