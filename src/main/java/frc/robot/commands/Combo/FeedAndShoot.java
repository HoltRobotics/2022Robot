// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Combo;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Feeder.FeedBallsUp;
import frc.robot.commands.Shoot.PIDShoot;
import frc.robot.commands.Shoot.StopShooter;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.ShooterPID;

public class FeedAndShoot extends SequentialCommandGroup {
  /**
   * Command Group that runs the shooter, waits for it to get up to the specified speed, then feeds the balls to the feeder, then stops everything.
   * @param rpm Taget RPM of the shooter.
   * @param shooter Required Shooter Subsystem
   * @param feeder Required Feeder Subsystem
   */
  public FeedAndShoot(int rpm, ShooterPID shooter, Feeder feeder) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new PIDShoot(rpm, shooter),
      new FeedBallsUp(feeder).withTimeout(1),
      new StopShooter(shooter)
    );
  }
}
