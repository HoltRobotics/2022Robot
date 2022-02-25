// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends PIDSubsystem {

  private final WPI_TalonFX m_shooterMotor = new WPI_TalonFX(ShooterConstants.kShooterMotor);

  private static final SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(ShooterConstants.kS, ShooterConstants.kV);

  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Main");
  private final NetworkTableEntry m_rpm;
  
  /** Creates a new ShooterPID. */
  public Shooter() {
    super(
      // The PIDController used by the subsystem
      new PIDController(ShooterConstants.kP, ShooterConstants.kI, ShooterConstants.kD)
    );
    m_shooterMotor.setNeutralMode(NeutralMode.Brake);

    m_shooterMotor.setInverted(InvertType.InvertMotorOutput);
    
    m_shooterMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    
    m_rpm = m_tab.add("Shooter RPM", getRPM()).withPosition(4, 1).withSize(1, 1).withWidget(BuiltInWidgets.kTextView).getEntry();
    // m_tab.add("PID Controller", getController()).withPosition(4, 2).withSize(1, 2).withWidget(BuiltInWidgets.kPIDController);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
    runShooterVoltage(output + m_feedforward.calculate(setpoint));
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return getRPM();
  }

  /**
   * Sets the targer RPMs of the Shooter.
   * @param rpm The target RPM value.
   */
  public void setRPM(double rpm) {
    setSetpoint(rpm * ShooterConstants.kPIDAdjust);
  }

  /**
   * Runs the shooter at the supplied voltage level, even if the battery voltage is below 12V
   * @param voltage The voltage to output.
   */
  public void runShooterVoltage(double voltage) {
    m_shooterMotor.setVoltage(voltage);
  }

  /**
   * Runs the shooter in reverse in order to take in a ball through the shooter.
   */
  public void backShooter() {
    m_shooterMotor.set(-0.15);
  }

  /**
   * Returns the current RPM of the Shooter motor.
   * @return the RPM of the Shooter motor.
   */
  public double getRPM() {
    return m_shooterMotor.getSelectedSensorVelocity() * 600 / 2048;
  }

  /**
   * Returns the Shuffleboard tab to add stuff.
   * @return the Shuffleboard tab.
   */
  public ShuffleboardTab getTab() {
    return m_tab;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_rpm.setNumber(getRPM());
    super.periodic();
  }
}
