// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Combo;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Feeder.FeedFront;
import frc.robot.commands.Intake.SideIntake;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;

public class SideNFeed extends ParallelCommandGroup {
  /**
   * Command that runs the side intake and the front feeder.
   * @param intake The intake subsystem
   * @param feeder The feeder subsystem
   */
  public SideNFeed(Intake intake, Feeder feeder) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SideIntake(intake),
      new FeedFront(feeder)
    );
  }
}
