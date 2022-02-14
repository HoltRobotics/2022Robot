// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  
  private final WPI_TalonFX m_shooterMotor = new WPI_TalonFX(ShooterConstants.kShooterMotor);

  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");
  private final NetworkTableEntry m_rpm;

  /** Creates a new Shooter. */
  public Shooter() {
    m_shooterMotor.setNeutralMode(NeutralMode.Coast);

    m_shooterMotor.setInverted(InvertType.None);

    m_shooterMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    m_rpm = m_tab.add("Shooter RPM", getRPM()).withPosition(4, 1).getEntry();
  }

  public void runShooter() {
    m_shooterMotor.set(1);
  }

  public void stopShooter() {
    m_shooterMotor.set(1);
  }

  public double getRPM() {
    return m_shooterMotor.getSelectedSensorVelocity() * 600 / 2048;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_rpm.setNumber(getRPM());
  }
}
