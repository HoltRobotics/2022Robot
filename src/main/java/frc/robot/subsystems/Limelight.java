// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

  private final NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");

  private final NetworkTableEntry tv = m_table.getEntry("tv"); // Whether the limelight has any valid targets (0 or 1)
  private final NetworkTableEntry tx = m_table.getEntry("tx"); // Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27 degrees | LL2: -29.8 to 29.8 degrees)
  private final NetworkTableEntry ty = m_table.getEntry("ty"); // Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to 24.85 degrees)

  private final NetworkTableEntry m_tv;
  private final NetworkTableEntry m_tx;
  private final NetworkTableEntry m_ty;

  /** Creates a new Limelight. */
  public Limelight() {
    m_tv = m_tab.add("Has Target", tv).withPosition(5, 0).withSize(1, 1).withWidget(BuiltInWidgets.kBooleanBox).getEntry();
    m_tx = m_tab.add("Horizontal Offset", tx).withPosition(5, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
    m_ty = m_tab.add("Vertical Offset", ty).withPosition(5, 2).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
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

  public boolean getTV() {
    return ty.getFlags() == 1;
  }

  public double getTX() {
    return tx.getDouble(0.0);
  }

  public double getTY() {
    return ty.getDouble(0.0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_tv.setBoolean(tv.getFlags() == 1);
    m_tx.setDouble(tx.getDouble(0.0));
    m_ty.setDouble(ty.getDouble(0.0));
  }
}
