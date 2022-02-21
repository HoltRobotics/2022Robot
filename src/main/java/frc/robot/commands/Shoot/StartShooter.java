// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shoot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ShooterPID;

public class StartShooter extends InstantCommand {
  private final ShooterPID m_shooter;

  /**
   * Command that only starts the shooter. Will NOT stop it after ending.
   * @param shooter Required Shooter Subsystem.
   */
  public StartShooter(ShooterPID shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_shooter = shooter;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.startShooter();
  }
}
