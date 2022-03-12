// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

  private final NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");

  private final NetworkTableEntry tv = m_table.getEntry("tv"); // Whether the limelight has any valid targets (0 or 1)
  private final NetworkTableEntry tx = m_table.getEntry("tx"); // Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees | LL2: -29.8 to 29.8 degrees)
  private final NetworkTableEntry ty = m_table.getEntry("ty"); // Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to 24.85 degrees)

  /** Creates a new Limelight. */
  public Limelight() {
    logToShuffleboard();
  }

  public void logToShuffleboard() {
    boolean target = tv.getFlags() == 1;
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);

    m_tab.add("Limelight Target", target);
    m_tab.add("Limelight X", x);
    m_tab.add("Limelight Y", y);
  }

  public void updateShuffleBoard() {
    tv.setBoolean(tv.getFlags() == 1);
    tx.setNumber(tx.getDouble(0.0));
    ty.setNumber(ty.getDouble(0.0));
  }

  /**
   * Sets the Limelight's LED state.
   * <p> 0 - current pipeline mode
   * <p> 1 - force off
   * <p> 2 - force blink
   * <p> 3 - force on
   * @param mode the state for the LEDs
   */
  public void setLEDMode(int mode) {
    m_table.getEntry("ledMode").setNumber(mode);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateShuffleBoard();
  }
}
