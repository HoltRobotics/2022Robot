// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shoot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class WaitForShooter extends CommandBase {
  private final Shooter m_shooter;

  /**
   * Waits for the shooter to spin up within 1 percent of its setpoint.
   * @param shooter The Shooter subsystem
   */
  public WaitForShooter(Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(((m_shooter.getSetpoint() - 1000 - m_shooter.getRPM()) / m_shooter.getSetpoint() * 100)) <= 1) {
      return true;
    } else {
      return false;
    }
  }
}
