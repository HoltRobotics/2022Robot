// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FeederConstants;

public class Feeder extends SubsystemBase {
  private final WPI_VictorSPX m_beltFeederMotor = new WPI_VictorSPX(FeederConstants.kBeltFeederMotor);
  private final WPI_VictorSPX m_frontFeederMotor = new WPI_VictorSPX(FeederConstants.kFrontFeederMotor);

  /** Creates a new Feeder. */
  public Feeder() {
    m_beltFeederMotor.setNeutralMode(NeutralMode.Brake);
    m_frontFeederMotor.setNeutralMode(NeutralMode.Brake);

    m_beltFeederMotor.setInverted(InvertType.None);
    m_frontFeederMotor.setInverted(InvertType.None);
  }

  public void runFeeder() {
    m_beltFeederMotor.set(FeederConstants.kFeederMotorSpeed);
    m_frontFeederMotor.set(FeederConstants.kFeederMotorSpeed);
  }

  public void stopFeeder() {
    m_beltFeederMotor.stopMotor();
    m_frontFeederMotor.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
