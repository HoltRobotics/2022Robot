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
import frc.robot.commands.Drive.DriveBackDistance;
import frc.robot.commands.Drive.DriveForwardDistance;
import frc.robot.commands.Drive.TurnLeftAngle;
import frc.robot.commands.Drive.TurnRightAngle;
import frc.robot.commands.Feeder.BeltsDown;
import frc.robot.commands.Shoot.BackwardsShooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class ThreeBallRightSide extends SequentialCommandGroup {
  
  /**
   * Autonomous command for the right side of the field that drives forward, picks up a ball, turns, drives forward, then shoots both balls.
   * @param drive The drivetrain subsystem
   * @param intake The intake subsystem
   * @param feeder The feeder subsystem
   * @param shooter The shooter subsystem
   * @param light The limelight subsystem
   */
  public ThreeBallRightSide(Drivetrain drive, Intake intake, Feeder feeder, Shooter shooter, Limelight light) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelRaceGroup(
        new DriveForwardDistance(Units.inchesToMeters(46), DriveConstants.kAutonSpeed, drive),
        new BothNFeed(intake, feeder)
      ),
      new BothNFeed(intake, feeder).withTimeout(0.5),
      new DriveBackDistance(0.5, DriveConstants.kAutonSpeed, drive),
      new BothNFeed(intake, feeder).withTimeout(1),
      new TurnRightAngle(150, drive),
      new ParallelDeadlineGroup(new WaitCommand(0.2), 
        new BeltsDown(feeder),
        new BackwardsShooter(shooter)
      ),
      new TurnAndShoot(drive, light, feeder, shooter).withTimeout(3), //TODO: find right timeout
      new TurnLeftAngle(180, drive), //TODO: find right angle
      new ParallelRaceGroup(
        new DriveForwardDistance(Units.inchesToMeters(46), DriveConstants.kAutonSpeed, drive), //TODO: find right distance
        new BothNFeed(intake, feeder)
      ),
      new TurnRightAngle(30, drive), //TODO: find right angle
      new TurnAndShoot(drive, light, feeder, shooter)
    );
  }
}
