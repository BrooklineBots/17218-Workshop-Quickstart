package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Intake extends SubsystemBase {

  public static final String INTAKE_MOTOR_ID = "intake_motor";

  private final DcMotor intakeMotor;

  public Intake(HardwareMap hwMap) {
    intakeMotor = hwMap.get(DcMotor.class, INTAKE_MOTOR_ID);

    // intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    // Set motor modes
    intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); // FLOAT or BRAKE
  }

  /**
   * Sets the raw power of the intake motor.
   *
   * @param power The power level (from -1.0 to 1.0)
   */
  public void setPower(double power) {
    intakeMotor.setPower(power);
  }

  public void intake() {
    intake(1.0); // Can be any default value.
  }

  public void intake(double power) {
    if (Math.abs(power) <= 1.0) {
      setPower(power);
    } else {
      setPower(0.0);
    }
  }

  /** Turns the intake on in reverse to eject elements. */
  public void outtake() {
    intake(-1.0); // Adjust power as needed
  }

  /** Stops the intake motor. */
  public void stop() {
    setPower(0.0); // Or intake(0.0);
  }
}
