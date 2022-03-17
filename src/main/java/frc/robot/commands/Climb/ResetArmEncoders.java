// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Climb;

public class ResetArmEncoders extends InstantCommand {
  private final Climb m_climb;

  public ResetArmEncoders(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
    addRequirements(m_climb);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_climb.resetArmEncoder();
  }
}
