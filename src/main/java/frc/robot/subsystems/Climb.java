// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbConstants;

public class Climb extends SubsystemBase {
  private final CANSparkMax m_arm = new CANSparkMax(ClimbConstants.kArmMotor, MotorType.kBrushless);
  private final CANSparkMax m_hooks = new CANSparkMax(ClimbConstants.kHookMotor, MotorType.kBrushless);

  /**
   * Climb Subsystem
   */
  public Climb() {
    m_arm.setInverted(false);
    m_hooks.setInverted(false);

    m_arm.setIdleMode(IdleMode.kBrake);
    m_hooks.setIdleMode(IdleMode.kBrake);
  }

  public void runArms() {
    m_arm.set(ClimbConstants.kArmMotorSpeed);
  }

  public void slideHooks() {
    m_hooks.set(ClimbConstants.kHookMotorSpeed);
  }

  public void stopArms() {
    m_arm.stopMotor();
  }

  public void stopHooks() {
    m_hooks.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
