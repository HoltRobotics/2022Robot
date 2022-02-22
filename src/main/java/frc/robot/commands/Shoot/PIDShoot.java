// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shoot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterPID;

public class PIDShoot extends CommandBase {
  private final ShooterPID m_shooter;
  private final int m_rpm;

  public PIDShoot(int rpm, ShooterPID shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_rpm = rpm;
    m_shooter = shooter;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.setRPM(m_rpm);
    m_shooter.startShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(m_shooter.getRPM()) >= m_rpm;
  }
}
