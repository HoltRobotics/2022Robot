// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
  private final WPI_TalonFX m_frontLeftMotor = new WPI_TalonFX(DriveConstants.kFrontLeftMotor);
  private final WPI_TalonFX m_rearLeftMotor = new WPI_TalonFX(DriveConstants.kRearLeftMotor);
  private final WPI_TalonFX m_frontRightMotor = new WPI_TalonFX(DriveConstants.kFrontRightMotor);
  private final WPI_TalonFX m_rearRightMotor = new WPI_TalonFX(DriveConstants.kFrontRightMotor);

  private final MecanumDrive m_drive = new MecanumDrive(m_frontLeftMotor, m_rearLeftMotor, m_frontRightMotor, m_rearRightMotor);

  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  private boolean toggleFieldDrive = false;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_frontLeftMotor.setNeutralMode(NeutralMode.Brake);
    m_rearLeftMotor.setNeutralMode(NeutralMode.Brake);
    m_frontRightMotor.setNeutralMode(NeutralMode.Brake);
    m_rearRightMotor.setNeutralMode(NeutralMode.Brake);

    m_frontLeftMotor.setInverted(InvertType.None);
    m_rearLeftMotor.setInverted(InvertType.None);
    m_frontRightMotor.setInverted(InvertType.None);
    m_rearRightMotor.setInverted(InvertType.None);

    m_drive.setMaxOutput(DriveConstants.kMaxSpeed);
  }

  public void driveCartesian(double ySpeed, double xSpeed, double zRotation) {
    m_drive.driveCartesian(ySpeed, xSpeed, zRotation);
  }

  public void driveCartesian(double ySpeed, double xSpeed, double zRotation, double gyroAngle) {
    m_drive.driveCartesian(ySpeed, xSpeed, zRotation, gyroAngle);
  }

  public double getGyroAngle() {
    return m_gyro.getAngle();
  }

  public void toggleFieldDrive() {
    toggleFieldDrive = !toggleFieldDrive;
  }

  public boolean getFieldDriveMode() {
    return toggleFieldDrive;
  }

  public void resetGyro() {
    m_gyro.reset();
  }

  public void stopDrive() {
    m_drive.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
