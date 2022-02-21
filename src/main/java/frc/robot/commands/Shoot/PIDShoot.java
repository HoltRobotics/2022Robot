// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shoot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ShooterPID;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PIDShoot extends InstantCommand {
  private final ShooterPID m_shoot;
  private final int m_rpm;

  public PIDShoot(int rpm, ShooterPID shoot) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_rpm = rpm;
    m_shoot = shoot;
    addRequirements(shoot);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shoot.setRPM(m_rpm);
    m_shoot.startShooter();
  }
}
