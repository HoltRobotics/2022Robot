// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.Drive.CartesianDrive;
import frc.robot.commands.Drive.PlayMusic;
import frc.robot.commands.Drive.ToggleFieldDrive;
import frc.robot.commands.Feeder.FeedBallsUp;
import frc.robot.commands.Intake.FrontIntake;
import frc.robot.commands.Intake.SideIntake;
import frc.robot.commands.Shoot.BallGoBurrrrrr;
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

  public final XboxController m_driver = new XboxController(OIConstants.kDriverController);
  public final Joystick m_operator = new Joystick(OIConstants.kOperatorController);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    m_drive.setDefaultCommand(new CartesianDrive(() -> -m_driver.getLeftY(), () -> m_driver.getLeftX(), () -> m_driver.getRightX(), m_drive));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(m_driver, Button.kA.value).whenPressed(new ToggleFieldDrive(m_drive));
    new JoystickButton(m_driver, Button.kB.value).whenPressed(new PlayMusic(m_drive));
    new JoystickButton(m_operator, 1).whenHeld(new FrontIntake(1, m_intake));
    new JoystickButton(m_operator, 2).whenHeld(new SideIntake(1, m_intake));
    new JoystickButton(m_operator, 3).whenHeld(new FeedBallsUp(m_feeder));
    new JoystickButton(m_operator, 4).whenHeld(new BallGoBurrrrrr(m_shoot));
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
