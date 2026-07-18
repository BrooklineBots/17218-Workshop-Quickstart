package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.hardware.RevIMU;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Utils.Utils;

@TeleOp(name = "Field Drive")
public class FieldDrive extends CommandOpMode {

  private GamepadEx gamepad1Ex;
  private Telemetry telemetry;
  private Motor fl;
  private Motor fr;
  private Motor bl;
  private Motor br;
  private RevIMU imu;

  private MecanumDrive drive;

  public void initialize() {

    gamepad1Ex = new GamepadEx(gamepad1);

    fl = new Motor(hardwareMap, Constants.DriveConstants.FRONT_LEFT_MOTOR_ID);
    fr = new Motor(hardwareMap, Constants.DriveConstants.FRONT_RIGHT_MOTOR_ID);
    bl = new Motor(hardwareMap, Constants.DriveConstants.BACK_LEFT_MOTOR_ID);
    br = new Motor(hardwareMap, Constants.DriveConstants.BACK_RIGHT_MOTOR_ID);

    imu = new RevIMU(hardwareMap, Constants.DriveConstants.IMU_ID);

    final IMU.Parameters parameters =
        new IMU.Parameters(
            new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));

    imu.init();

    drive = new MecanumDrive(fl, fr, bl, br);
  }

  public void run() {
    if (!Utils.isWithinTolerance(0.0, gamepad1Ex.getLeftX(), 0.1)
        && !Utils.isWithinTolerance(0.0, gamepad1Ex.getLeftY(), 0.1)
        && !Utils.isWithinTolerance(0.0, gamepad1Ex.getRightX(), 0.1)) {
      drive.driveFieldCentric(
          gamepad1Ex.getLeftX(), gamepad1Ex.getLeftY(), gamepad1Ex.getRightX(), imu.getHeading());
    } else {
        drive.stop();
    }
  }
}
