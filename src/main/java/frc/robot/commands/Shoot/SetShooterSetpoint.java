// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shoot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

public class SetShooterSetpoint extends InstantCommand {
  private final Double m_setPoint;
  private final Shooter m_shooter;

  public SetShooterSetpoint(Double setPoint, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_setPoint = setPoint;
    m_shooter = shooter;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setRPM(m_setPoint);
  }
}
