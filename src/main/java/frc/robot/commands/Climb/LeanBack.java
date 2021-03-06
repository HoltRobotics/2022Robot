// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climb;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climb;

public class LeanBack extends CommandBase {
  private final Climb m_climb;

  /**
   * Command that leans the robot back.
   * @param climb The climb subsystem
   */
  public LeanBack(Climb climb) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climb = climb;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climb.slideHooksBack();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climb.stopHooks();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(m_climb.getHookPosition() <= 0) {
      return true;
    } else {
      return false;
    }
  }
}
