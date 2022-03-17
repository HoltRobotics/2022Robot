// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auton;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.commands.Combo.FrontNFeed;
import frc.robot.commands.Drive.DriveBackDistance;
import frc.robot.commands.Drive.DriveForwardDistance;
import frc.robot.commands.Drive.TurnRightAngle;
import frc.robot.commands.Feeder.FeedBallsUp;
import frc.robot.commands.Shoot.SetShooterSetpoint;
import frc.robot.commands.Shoot.StartShooter;
import frc.robot.commands.Shoot.StopShooter;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class TwoBallNotLight extends SequentialCommandGroup {
  /** Creates a new TwoBallNotLight. */
  public TwoBallNotLight(Drivetrain drive, Intake intake, Feeder feeder, Shooter shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new ParallelRaceGroup(
        new DriveForwardDistance(Units.inchesToMeters(45), drive),
        new FrontNFeed(intake, feeder)
      ),
      new TurnRightAngle(180, drive),
      new SetShooterSetpoint(ShooterConstants.kSlowShootRPM, shooter),
      new StartShooter(shooter),
      new DriveForwardDistance(Units.inchesToMeters(45), drive),
      new FeedBallsUp(feeder).withTimeout(0.5),
      new WaitCommand(0.5),
      new FeedBallsUp(feeder).withTimeout(1),
      new StopShooter(shooter),
      new DriveBackDistance(1, drive)
    );
  }
}
