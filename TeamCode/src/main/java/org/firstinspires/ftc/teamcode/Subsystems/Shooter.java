package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class Shooter extends SubsystemBase {

  public static final String SHOOTER_MOTOR_ID = "shooter_motor";
  public static final String FEEDER_SERVO_ID = "feeder_servo";

  private final DcMotor shooterMotor;
  private final Servo feederServo;

  // Servo positions (you will need to tune these for your specific mechanical
  // setup)
  public static final double FEEDER_REST_POS = 0.0;
  public static final double FEEDER_SHOOT_POS = 0.5;

  public Shooter(HardwareMap hwMap) {
    shooterMotor = hwMap.get(DcMotor.class, SHOOTER_MOTOR_ID);
    feederServo = hwMap.get(Servo.class, FEEDER_SERVO_ID);

    // shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    // Flywheels use RUN_WITHOUT_ENCODER or RUN_USING_ENCODER (for velocity PID)
    shooterMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    // Flywheels FLOAT so they spin down naturally and don't strip gears
    shooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

    // Initialize the feeder to the resting position
    // Every time we start the robot we MUST make sure this is placed properly
    feederServo.setPosition(FEEDER_REST_POS);
  }

  /**
   * raw power
   *
   * @param power Power from 0.0 to 1.0 (or negative if reversed)
   */
  public void setShooterPower(double power) {
    if (Math.abs(power) <= 1.0) {
      shooterMotor.setPower(power);
    } else {
      shooterMotor.setPower(0.0);
    }
  }

  /** Turns the shooter flywheel to a default power. */
  public void spinUp() {
    setShooterPower(1.0);
  }

  /** Stops the shooter flywheel. */
  public void stopShooter() {
    setShooterPower(0.0); // This will still float for a few seconds.
  }

  /** Pushes a game element into the flywheel to shoot it. */
  public void feed() {
    feederServo.setPosition(FEEDER_SHOOT_POS);
  }

  /** Retracts the feeder mechanism so the next element can load. */
  public void retractFeeder() {
    feederServo.setPosition(FEEDER_REST_POS);
  }
}
