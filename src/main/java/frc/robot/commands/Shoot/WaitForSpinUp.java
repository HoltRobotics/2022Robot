// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shoot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterPID;

public class WaitForSpinUp extends CommandBase {
  private final double m_rpm;
  private final ShooterPID m_shooter;
  
  /**
   * Command that will start the shooter and waits for it to pass the specified RPM.
   * Shooter will overshoot the specified RPM. Will only wait for it to pass the specified RPM.
   * @param rpm Target RPM for the shooter.
   * @param shooter Required Shooter Subsystem
   * 
   * @deprecated No longer needed. Use {@link PIDShoot} to set RPM and enable. Then {@link StopShooter} to disable.
   */
  public WaitForSpinUp(double rpm, ShooterPID shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_rpm = rpm;
    m_shooter = shooter;
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(m_shooter.getRPM()) >= m_rpm;
  }
}
