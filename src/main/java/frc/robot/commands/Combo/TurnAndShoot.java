// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Combo;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.TurnToTargetPID;
import frc.robot.commands.Feeder.FeedBallsUp;
import frc.robot.commands.Shoot.AutoRPM;
import frc.robot.commands.Shoot.StopShooter;
import frc.robot.commands.Shoot.WaitForShooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class TurnAndShoot extends ParallelCommandGroup {
  /** Creates a new TurnAndShoot. */
  public TurnAndShoot(Drivetrain drive, Limelight light, Feeder feeder, Shooter shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new TurnToTargetPID(() -> 0, () -> 0, drive, light),
      new AutoRPM(shooter, light),
      new SequentialCommandGroup(
        new WaitForShooter(shooter),
        new FeedBallsUp(feeder).withTimeout(2),
        new StopShooter(shooter)
      )
    );
  }
}
