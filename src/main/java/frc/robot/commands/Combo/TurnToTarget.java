// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Combo;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class TurnToTarget extends PIDCommand {
  private final Drivetrain m_drive;
  private final Limelight m_light;

  // TODO: tune and set feedforward and pid
  private final static SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(0, 0);

  /** Creates a new TurnToTarget. */
  public TurnToTarget(Drivetrain drive, Limelight light) {
    super(
        // The controller that the command will use
        new PIDController(0, 0, 0),
        // This should return the measurement
        light::getTX,
        // This should return the setpoint (can also be a constant)
        () -> 0,
        // This uses the output
        output -> {
          // Use the output here
          drive.driveCartesian(0, 0, output + m_feedforward.calculate(light.getTX()));
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    m_drive = drive;
    m_light = light;
    addRequirements(m_drive, m_light);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_light.setLEDMode(3);
    super.initialize();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.stopDrive();
    m_light.setLEDMode(1);
    super.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !m_light.getTV();
  }
}
