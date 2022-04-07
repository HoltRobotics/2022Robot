// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbConstants;

public class Climb extends SubsystemBase {
  private final CANSparkMax m_arm = new CANSparkMax(ClimbConstants.kArmMotor, MotorType.kBrushless);
  private final CANSparkMax m_hooks = new CANSparkMax(ClimbConstants.kHookMotor, MotorType.kBrushless);

  private final RelativeEncoder m_armEncoder = m_arm.getEncoder();
  private final RelativeEncoder m_hookEncoder = m_hooks.getEncoder();

  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");
  private final NetworkTableEntry m_armLocation;
  private final NetworkTableEntry m_hookLocation;

  /**
   * Climb Subsystem. This is the subsystem that controls the climbing mechanism.
   */
  public Climb() {
    m_arm.setInverted(true);
    m_hooks.setInverted(true);

    m_arm.setIdleMode(IdleMode.kBrake);
    m_hooks.setIdleMode(IdleMode.kBrake);

    m_armLocation = m_tab.add("Arm Location", getArmPosition()).withPosition(6, 0).getEntry();
    m_hookLocation = m_tab.add("Hook Location", getHookPosition()).withPosition(6, 1).getEntry();
  }

  /**
   * Resets the Hook Encoders to zero.
   */
  public void resetHookEncoder(){
    m_hookEncoder.setPosition(0);
  }

  /**
   * Resets the Arm Encoders to zero.
   */
  public void resetArmEncoder() {
    m_armEncoder.setPosition(0);
  }

  /**
   * Raises the climbing arms.
   */
  public void raiseArms() {
    m_arm.set(ClimbConstants.kArmMotorSpeed);
  }


  /**
   * Lowers the climbing arms.
   */
  public void lowerArms() {
    m_arm.set(-ClimbConstants.kArmMotorSpeed);
  }

  /**
   * Slides the hooks back.
   */
  public void slideHooksBack() {
    m_hooks.set(ClimbConstants.kHookMotorSpeed);
  }

  /**
   * Slides the hooks forward.
   */
  public void slideHooksForward() {
    m_hooks.set(-ClimbConstants.kHookMotorSpeed);
  }

  /**
   * Returns the current position of the hooks.
   */
  public double getHookPosition() {
    return -m_hookEncoder.getPosition();
  }

  /**
   * Returns the current position of the arms.
   */
  public double getArmPosition() {
    return m_armEncoder.getPosition();
  }

  /**
   * Stops the climbing motor.
   */
  public void stopArms() {
    m_arm.stopMotor();
  }

  /**
   * Stops the hooks motor.
   */
  public void stopHooks() {
    m_hooks.stopMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_armLocation.setNumber(getArmPosition());
    m_hookLocation.setNumber(getHookPosition());
  }
}
