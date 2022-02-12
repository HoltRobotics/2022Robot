// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class CartesianDrive extends CommandBase {
  public final DoubleSupplier m_ySpeed;
  public final DoubleSupplier m_xSpeed;
  public final DoubleSupplier m_zRotation;
  public final Drivetrain m_drive;

  /** Creates a new CartesianDrive. */
  public CartesianDrive(DoubleSupplier ySpeed, DoubleSupplier xSpeed, DoubleSupplier zRotation, Drivetrain drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_ySpeed = ySpeed;
    m_xSpeed = xSpeed;
    m_zRotation = zRotation;
    m_drive = drive;
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(m_drive.getFieldDriveMode()) {
      m_drive.driveCartesian(m_ySpeed.getAsDouble(), m_xSpeed.getAsDouble(), m_zRotation.getAsDouble());
    } else {
      m_drive.driveCartesian(m_ySpeed.getAsDouble(), m_xSpeed.getAsDouble(), m_zRotation.getAsDouble(), m_drive.getGyroAngle());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.stopDrive();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
