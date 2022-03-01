// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.Climb.LowerArms;
import frc.robot.commands.Climb.RaiseArms;
import frc.robot.commands.Combo.FrontNFeed;
import frc.robot.commands.Combo.SideNFeed;
import frc.robot.commands.Drive.CartesianDrive;
import frc.robot.commands.Drive.DriveBackDistance;
import frc.robot.commands.Drive.DriveForwardDistance;
import frc.robot.commands.Drive.SlowDrive;
import frc.robot.commands.Drive.ToggleFieldDrive;
import frc.robot.commands.Feeder.FeedBallsDown;
import frc.robot.commands.Feeder.FeedBallsUp;
import frc.robot.commands.Shoot.BackwardsShooter;
import frc.robot.commands.Shoot.FastShoot;
import frc.robot.commands.Shoot.MidShoot;
import frc.robot.commands.Shoot.SlowShoot;
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

  // Controllers
  public final XboxController m_xboxDriver = new XboxController(OIConstants.kXboxDriverController);
  public final FlightStick m_flightDriver = new FlightStick(OIConstants.kFlightDriverController);
  public final Joystick m_operator = new Joystick(OIConstants.kOperatorController);

  // Cameras
  public final UsbCamera m_frontCamera = CameraServer.startAutomaticCapture(0);
  // public final UsbCamera m_sideCamera = CameraServer.startAutomaticCapture(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    m_drive.setDefaultCommand(new CartesianDrive(
      () -> -m_xboxDriver.getLeftY() + -m_flightDriver.getY(),
      () -> m_xboxDriver.getLeftX() + m_flightDriver.getX(),
      () -> m_xboxDriver.getRightX() + m_flightDriver.getZ(),
      m_drive)
    );

    // m_frontCamera.setResolution(176, 144);
    // m_sideCamera.setResolution(176, 144);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(m_xboxDriver, Button.kA.value).whenPressed(new ToggleFieldDrive(m_drive));
    new JoystickButton(m_xboxDriver, Button.kB.value).whenHeld(new SlowDrive(m_drive));
    new JoystickButton(m_xboxDriver, Button.kLeftBumper.value).whenHeld(new FrontNFeed(m_intake, m_feeder));
    new JoystickButton(m_xboxDriver, Button.kRightBumper.value).whenHeld(new SideNFeed(m_intake, m_feeder));


    new JoystickButton(m_flightDriver, FlightStick.Button.kTrigger.value).whenPressed(new ToggleFieldDrive(m_drive));
    new JoystickButton(m_flightDriver, FlightStick.Button.kLeftTopMiddle.value).whenHeld(new FrontNFeed(m_intake, m_feeder));
    new JoystickButton(m_flightDriver, FlightStick.Button.kLeftTopRight.value).whenHeld(new SideNFeed(m_intake, m_feeder));
    new JoystickButton(m_flightDriver, FlightStick.Button.kLeftTopLeft.value).whenHeld(new SlowDrive(m_drive));

    new JoystickButton(m_operator, 1).whenHeld(new FrontNFeed(m_intake, m_feeder));
    new JoystickButton(m_operator, 2).whenHeld(new SideNFeed(m_intake, m_feeder));
    new JoystickButton(m_operator, 3).whenHeld(new FeedBallsUp(m_feeder));
    new JoystickButton(m_operator, 4).whenHeld(new FeedBallsDown(m_feeder));
    new JoystickButton(m_operator, 5).whenHeld(new BackwardsShooter(m_shoot));
    new JoystickButton(m_operator, 6).whenHeld(new SlowShoot(m_shoot));
    new JoystickButton(m_operator, 7).whenHeld(new MidShoot(m_shoot));
    new JoystickButton(m_operator, 8).whenHeld(new FastShoot(m_shoot));
    new JoystickButton(m_operator, 11).whenHeld(new RaiseArms(m_climb));
    new JoystickButton(m_operator, 12).whenHeld(new LowerArms(m_climb));
    new JoystickButton(m_operator, 21).whenPressed(new StopShooter(m_shoot));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new SequentialCommandGroup(new DriveForwardDistance(1, m_drive), new DriveBackDistance(1, m_drive));
  }
}
