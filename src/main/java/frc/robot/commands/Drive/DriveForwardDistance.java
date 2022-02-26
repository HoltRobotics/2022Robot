// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

public class DriveForwardDistance extends CommandBase {
  private final double m_distance;
  private final Drivetrain m_drive;

  /**
   * Simple Drive command.
   * <p>It drives forward at halfspeed for a set distance.
   * @param distance The distance to drive in meters.
   * @param drive The required subsystem.
   */
  public DriveForwardDistance(double distance, Drivetrain drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_distance = distance;
    m_drive = drive;
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.driveCartesian(DriveConstants.kAutonSpeed, 0, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.stopDrive();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(m_drive.getAverageDistance()) >= m_distance;
  }
}
