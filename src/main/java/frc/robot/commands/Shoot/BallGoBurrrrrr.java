// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shoot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterPID;

public class BallGoBurrrrrr extends CommandBase {
  private final ShooterPID m_shoot;

  /**
   * Command to run the shooter forwards.
   * @param m_shoot Required Shootere Subsystem
   * 
   * @deprecated No longer needed. Use {@link PIDShoot} to set RPM and enable. Then {@link StopShooter} to disable.
   */
  public BallGoBurrrrrr(ShooterPID shoot) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shoot = shoot;
    addRequirements(m_shoot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shoot.startShooter();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shoot.stopShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
