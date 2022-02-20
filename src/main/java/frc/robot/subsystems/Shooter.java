// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  
  private final WPI_TalonFX m_shooterMotor = new WPI_TalonFX(ShooterConstants.kShooterMotor);

  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");
  private final NetworkTableEntry m_rpm;
  private final NetworkTableEntry m_maxSpeed;

  private double m_speed;

  /**
   * Shooter Subsystem
   */
  public Shooter() {
    m_shooterMotor.setNeutralMode(NeutralMode.Brake);

    m_shooterMotor.setInverted(InvertType.InvertMotorOutput);

    m_shooterMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    m_maxSpeed = m_tab.add("Shooter Speed", 1.0).withPosition(2, 3).withSize(2, 1).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
    m_rpm = m_tab.add("Shooter RPM", getRPM()).withPosition(4, 1).getEntry();

    m_maxSpeed.setDouble(ShooterConstants.kShooterMotorSpeed);
  }

  public void startShooter() {
    m_shooterMotor.set(m_speed);
  }

  public void runShooterVoltage(double voltage) {
    m_shooterMotor.setVoltage(voltage);
  }

  public void stopShooter() {
    m_shooterMotor.stopMotor();;
  }

  public double getRPM() {
    return m_shooterMotor.getSelectedSensorVelocity() * 600 / 2048;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_rpm.setNumber(getRPM());
    m_speed = m_maxSpeed.getDouble(1.0);
  }
}
