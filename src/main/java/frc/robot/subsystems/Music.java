// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Music extends SubsystemBase {
  private final WPI_TalonFX m_five = new WPI_TalonFX(ShooterConstants.kShooterMotor);

  private final Orchestra m_orchestra = new Orchestra();
  /** Creates a new Music. */
  public Music() {
    m_orchestra.addInstrument(m_five);

    m_orchestra.loadMusic("motherland.chrp");
  }

  /**
   * Plays the music.
   */
  public void playMusic() {
    m_orchestra.play();
  }

  /**
   * Stops the music.
   */
  public void stopMusic() {
    m_orchestra.stop();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
