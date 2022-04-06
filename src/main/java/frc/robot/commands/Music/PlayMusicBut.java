// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Music;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Music;

public class PlayMusicBut extends CommandBase {
  private final Music m_music;
  private final Drivetrain m_drive;
  
  /**
   * Command that plays music.
   * @param music The music subsystem
   * @param drive The drivetrain subsystem
   */
  public PlayMusicBut(Music music, Drivetrain drive) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_music = music;
    m_drive = drive;
    addRequirements(m_music, m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_music.playMusic();
    m_drive.setDefaultCommand(new PlayMusicBut(m_music, m_drive));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_music.stopMusic();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
