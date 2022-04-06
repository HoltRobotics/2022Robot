// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Limelight;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Limelight;

public class LEDOff extends InstantCommand {
  private final Limelight m_light;

  /**
   * Command to set the limelight LEDs to off.
   * @param light The limelight subsystem
   */
  public LEDOff(Limelight light) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_light = light;
    addRequirements(m_light);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_light.setLEDMode(1);
  }
}
