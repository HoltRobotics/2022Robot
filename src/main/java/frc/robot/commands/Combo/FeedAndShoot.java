// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Combo;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Feeder.FeedBallsUp;
import frc.robot.commands.Shoot.StartShooter;
import frc.robot.commands.Shoot.StopShooter;
import frc.robot.commands.Shoot.WaitForSpinUp;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class FeedAndShoot extends SequentialCommandGroup {
  /** Creates a new FeedAndShoot. */
  public FeedAndShoot(double rpm, Shooter shooter, Feeder feeder) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new StartShooter(shooter),
      new WaitForSpinUp(rpm, shooter),
      new FeedBallsUp(feeder),
      new StopShooter(shooter)
    );
  }
}
