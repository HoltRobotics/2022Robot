// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.Combo.TurnAndShoot;
import frc.robot.commands.Drive.DriveBackDistance;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class OneBallWithLight extends SequentialCommandGroup {
  /**
   * Simple autonomous command that drives forward and shoots one ball.
   * @param shooter The shooter subsystem
   * @param drive The drivetrain subsystem
   * @param feeder The feeder subsystem
   * @param light The limelight subsystem
   */
  public OneBallWithLight(Shooter shooter, Drivetrain drive, Feeder feeder, Limelight light) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new DriveBackDistance(1.5, DriveConstants.kAutonSpeed, drive),
      new TurnAndShoot(drive, light, feeder, shooter).withTimeout(5),
      new DriveBackDistance(1, DriveConstants.kAutonSpeed, drive)
    );
  }
}
