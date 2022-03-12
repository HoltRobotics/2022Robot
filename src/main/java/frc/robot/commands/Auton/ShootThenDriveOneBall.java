// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auton;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.Drive.DriveBackDistance;
import frc.robot.commands.Drive.SetBrakeMode;
import frc.robot.commands.Drive.SetCoastMode;
import frc.robot.commands.Drive.StrafeRightDistance;
import frc.robot.commands.Feeder.FeedBallsUp;
import frc.robot.commands.Shoot.SetShooterSetpoint;
import frc.robot.commands.Shoot.StartShooter;
import frc.robot.commands.Shoot.StopShooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

public class ShootThenDriveOneBall extends SequentialCommandGroup {
  // private final Shooter m_shooter;
  // private final Drivetrain m_drive;

  /** Creates a new ShootThenDrive. */
  public ShootThenDriveOneBall(Shooter shooter, Drivetrain drive, Feeder feeder) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SetBrakeMode(drive),
      new SetShooterSetpoint(ShooterConstants.kSlowShootRPM, shooter),
      new StartShooter(shooter),
      new DriveBackDistance(1.5, drive),
      new FeedBallsUp(feeder).withTimeout(1),
      new StopShooter(shooter),
      new DriveBackDistance(1, drive),
      new SetCoastMode(drive)
    );
  }
}
