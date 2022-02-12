// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  private final WPI_VictorSPX m_beltFeederMotor = new WPI_VictorSPX(ShooterConstants.kBeltFeederMotor);
  private final WPI_VictorSPX m_frontFeederMotor = new WPI_VictorSPX(ShooterConstants.kFrontFeederMotor);
  private final WPI_TalonFX m_shooterMotor = new WPI_TalonFX(ShooterConstants.kShooterMotor);

  /** Creates a new Shooter. */
  public Shooter() {
    m_beltFeederMotor.setNeutralMode(NeutralMode.Brake);
    m_frontFeederMotor.setNeutralMode(NeutralMode.Brake);
    m_shooterMotor.setNeutralMode(NeutralMode.Coast);

    m_beltFeederMotor.setInverted(InvertType.None);
    m_frontFeederMotor.setInverted(InvertType.None);
    m_shooterMotor.setInverted(InvertType.None);
  }

  public void runShooter() {
    m_shooterMotor.set(1);
  }

  public void stopShooter() {
    m_shooterMotor.set(1);
  }

  public void runFeeder(double speed) {
    m_beltFeederMotor.set(speed);
    m_frontFeederMotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
