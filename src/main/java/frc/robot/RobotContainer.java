// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.Climb.RaiseArms;
import frc.robot.commands.Combo.FeedAndShoot;
import frc.robot.commands.Drive.CartesianDrive;
import frc.robot.commands.Drive.SlowDrive;
import frc.robot.commands.Drive.ToggleFieldDrive;
import frc.robot.commands.Feeder.FeedBallsUp;
import frc.robot.commands.Feeder.FeedFrontShooter;
import frc.robot.commands.Intake.FrontIntake;
import frc.robot.commands.Intake.SideIntake;
import frc.robot.commands.Shoot.BallGoBurrrrrr;
import frc.robot.commands.Shoot.StopShooter;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final Drivetrain m_drive = new Drivetrain();
  public final Intake m_intake = new Intake();
  public final Feeder m_feeder = new Feeder();
  public final Shooter m_shoot = new Shooter();
  public final Climb m_climb = new Climb();

//// Xbox Controller as driving controller; T16000M (big) Joystick as operator
public final XboxController m_driver = new XboxController(OIConstants.kDriverController);
public final Joystick m_operator = new Joystick(OIConstants.kOperatorController);
//// T16000M (big) Joystick as driving controller; Xbox Controller as operator
//public final Joystick m_driver = new Joystick(OIConstants.kOperatorController);
//public final XboxController m_operator = new XboxController(OIConstants.kDriverController);

/** The container for the robot. Contains subsystems, OI devices, and commands. */
public RobotContainer() {
  // Configure the button bindings
  configureButtonBindings();

  //// Xbox Controller as driving controller; T16000M (big) Joystick as operator
  m_drive.setDefaultCommand(new CartesianDrive(() -> -m_driver.getLeftY(), () -> m_driver.getLeftX(), () -> m_driver.getRightX() * 0.75, m_drive));
  
  //// T16000M (big) Joystick as driving controller; Xbox Controller as operator
  //m_drive.setDefaultCommand(new CartesianDrive(() -> -m_driver.getY(), () -> m_driver.getX(), () -> m_driver.getTwist(), m_drive));

}

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(m_driver, Button.kA.value).whenPressed(new ToggleFieldDrive(m_drive));
    new JoystickButton(m_driver, Button.kB.value).whenPressed(new StopShooter(m_shoot));
    new JoystickButton(m_driver, Button.kY.value).whenHeld(new SlowDrive(m_drive));
    new JoystickButton(m_driver, Button.kLeftBumper.value).whenHeld(new ParallelCommandGroup(new FrontIntake(0.5, m_intake), new FeedFrontShooter(m_feeder)));
    new JoystickButton(m_driver, Button.kRightBumper.value).whenHeld(new ParallelCommandGroup(new SideIntake(0.5, m_intake), new FeedFrontShooter(m_feeder)));
    new JoystickButton(m_operator, 1).whenHeld(new ParallelCommandGroup(new FrontIntake(0.5, m_intake), new FeedFrontShooter(m_feeder)));
    // new JoystickButton(m_operator, 1).whenHeld(new FrontIntake(0.5, m_intake));
    new JoystickButton(m_operator, 2).whenHeld(new ParallelCommandGroup(new SideIntake(0.5, m_intake), new FeedFrontShooter(m_feeder)));
    // new JoystickButton(m_operator, 2).whenHeld(new SideIntake(0.5, m_intake));
    new JoystickButton(m_operator, 3).whenHeld(new FeedBallsUp(m_feeder));
    new JoystickButton(m_operator, 4).whenHeld(new BallGoBurrrrrr(m_shoot));
    new JoystickButton(m_operator, 5).whenPressed(new FeedAndShoot(4000, m_shoot, m_feeder));
    new JoystickButton(m_operator, 6).whenHeld(new RaiseArms(m_climb));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new WaitCommand(15);
  }
}
