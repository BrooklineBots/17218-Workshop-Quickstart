package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class StandaloneFieldCentricDrivetrain extends SubsystemBase {

  public static final String FRONT_LEFT_MOTOR_ID = "front_left_motor";
  public static final String FRONT_RIGHT_MOTOR_ID = "front_right_motor";
  public static final String BACK_LEFT_MOTOR_ID = "back_left_motor";
  public static final String BACK_RIGHT_MOTOR_ID = "back_right_motor";
  public static final String IMU_ID = "imu";

  private final DcMotor frontLeftMotor;
  private final DcMotor frontRightMotor;
  private final DcMotor backLeftMotor;
  private final DcMotor backRightMotor;
  private final IMU imu;

  public StandaloneFieldCentricDrivetrain(HardwareMap hwMap) {
    // Initialize motors from hardware map
    frontLeftMotor = hwMap.get(DcMotor.class, FRONT_LEFT_MOTOR_ID);
    frontRightMotor = hwMap.get(DcMotor.class, FRONT_RIGHT_MOTOR_ID);
    backLeftMotor = hwMap.get(DcMotor.class, BACK_LEFT_MOTOR_ID);
    backRightMotor = hwMap.get(DcMotor.class, BACK_RIGHT_MOTOR_ID);

    // Initialize IMU
    imu = hwMap.get(IMU.class, IMU_ID);

    // Adjust the orientation parameters to match your robot
    IMU.Parameters parameters = new IMU.Parameters(
        new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.UP,
            RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
    imu.initialize(parameters);

    // Reverse left motors so positive power drives forward.
    // (This depends on your specific gear/motor orientation, but reversing left is
    // common)
    frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    // Set to run without encoder because we use simple voltage control
    frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    // Zero power behavior
    frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
  }

  /**
   * Resets the IMU's yaw angle to 0. Useful for realigning the field-centric
   * drive.
   */
  public void resetYaw() {
    imu.resetYaw();
  }

  /**
   * Drives the robot using field-centric coordinates.
   *
   * @param forward positive is forward (away from the driver)
   * @param strafe  positive is right
   * @param rotate  positive is clockwise/right
   */
  public void driveFieldCentric(double forward, double strafe, double rotate) {
    // Get the robot's heading in radians
    double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

    // Rotate the movement direction counter to the bot's rotation
    double rotX = strafe * Math.cos(-botHeading) - forward * Math.sin(-botHeading);
    double rotY = strafe * Math.sin(-botHeading) + forward * Math.cos(-botHeading);

    // Calculate the maximum power out of the four calculated motor powers
    // to ensure we maintain the correct ratio if powers exceed 1.0
    double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rotate), 1.0);

    double frontLeftPower = (rotY + rotX + rotate) / denominator;
    double backLeftPower = (rotY - rotX + rotate) / denominator;
    double frontRightPower = (rotY - rotX - rotate) / denominator;
    double backRightPower = (rotY + rotX - rotate) / denominator;

    frontLeftMotor.setPower(frontLeftPower);
    backLeftMotor.setPower(backLeftPower);
    frontRightMotor.setPower(frontRightPower);
    backRightMotor.setPower(backRightPower);
  }

  /** Stops all motors on the drivetrain. */
  public void stopMotors() {
    frontLeftMotor.setPower(0);
    frontRightMotor.setPower(0);
    backLeftMotor.setPower(0);
    backRightMotor.setPower(0);
  }
}
