// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shoot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class PIDShoot extends CommandBase {
  private final Shooter m_shooter;
  private final double m_rpm;

  /**
   * Command that runs the shooter at a certain RPM.
   * @param shooter The shooter subsystem
   * @param rpm The RPM to run the shooter at
   */
  public PIDShoot(double rpm, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_rpm = rpm;
    m_shooter = shooter;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setRPM(m_rpm);
    m_shooter.enable();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.disable();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
