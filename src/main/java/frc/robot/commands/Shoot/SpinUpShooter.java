// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shoot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class SpinUpShooter extends PIDCommand {
  private final Shooter m_shooter;
  private static double m_motorOutput;
  private static final SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(ShooterConstants.kS, ShooterConstants.kV, ShooterConstants.kA);

  /**
   * Command to spin the shooter at the specified RPM.
   * @param targetRPM RPM to have the shooter spin at.
   * @param shooter Required Shooter Subsystem
   */
  public SpinUpShooter(double targetRPM, Shooter shooter) {
    super(
        // The controller that the command will use
        new PIDController(0, 0, 0),
        // This should return the measurement
        shooter::getRPM,
        // This should return the setpoint (can also be a constant)
        targetRPM,
        // This uses the output
        output -> {
          // Use the output here
          m_motorOutput = output + m_feedforward.calculate(targetRPM);
          shooter.runShooterVoltage(m_motorOutput);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    m_shooter = shooter;
    addRequirements(m_shooter);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.stopShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
