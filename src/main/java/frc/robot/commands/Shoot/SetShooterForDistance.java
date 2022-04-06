// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shoot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class SetShooterForDistance extends CommandBase {
  private final Shooter m_shooter;
  private final Limelight m_light;

  /**
   * Command that sets the shooter for a certain distance.
   * @param shooter The shooter subsystem
   * @param light The limelight subsystem
   */
  public SetShooterForDistance(Shooter shooter, Limelight light) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    m_light = light;
    addRequirements(m_shooter, m_light);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_light.setLEDMode(3);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_shooter.setRPM(m_shooter.distanceToRPM(m_light.getDistance()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_light.setLEDMode(1);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
