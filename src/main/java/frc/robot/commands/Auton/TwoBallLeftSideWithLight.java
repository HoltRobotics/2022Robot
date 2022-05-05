// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auton;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.Combo.BothNFeed;
import frc.robot.commands.Combo.TurnAndShoot;
import frc.robot.commands.Drive.DriveForwardDistance;
import frc.robot.commands.Drive.TurnRightAngle;
import frc.robot.commands.Feeder.FeedBallsDown;
import frc.robot.commands.Shoot.BackwardsShooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class TwoBallLeftSideWithLight extends SequentialCommandGroup {
  
  /**
   * Autonomous command for the left side of the field that drives forward, picks up a ball, turns, drives forward, then shoots both balls.
   * @param drive The drivetrain subsystem
   * @param intake The intake subsystem
   * @param feeder The feeder subsystem
   * @param shooter The shooter subsystem
   * @param light The Limelight subsystem
   */
  public TwoBallLeftSideWithLight(Drivetrain drive, Intake intake, Feeder feeder, Shooter shooter, Limelight light) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelRaceGroup(
        new DriveForwardDistance(Units.inchesToMeters(65), DriveConstants.kAutonSpeed, drive),
        new BothNFeed(intake, feeder)
      ),
      new BothNFeed(intake, feeder).withTimeout(1),
      new TurnRightAngle(160, drive),
      new ParallelDeadlineGroup(new WaitCommand(0.1), 
        new FeedBallsDown(feeder),
        new BackwardsShooter(shooter)
      ),
      // new DriveForwardDistance(Units.inchesToMeters(24), DriveConstants.kAutonSpeed, drive),
      new TurnAndShoot(drive, light, feeder, shooter)
    );
  }
}
