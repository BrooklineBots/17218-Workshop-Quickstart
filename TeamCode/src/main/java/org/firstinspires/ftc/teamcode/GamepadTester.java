package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Gamepad Tester")
public class GamepadTester extends CommandOpMode {

  private GamepadEx gamepad1Ex;
  private Telemetry telemetry;

  public void initialize() {
    gamepad1Ex = new GamepadEx(gamepad1);
  }

  /**
   * Add telemetry to show the right stick of gamepad1. Add telemetry to show whether the b button
   * is pressed on gamepad1. Report to the user the difference between the left joystick y and the
   * right joystick y on gamepad1. Report to the user the sum of the left and right triggers on
   * gamepad1.
   */
  public void run() {
    // TODO: Add telemetry to show the right stick of gamepad1.
    telemetry.addData("Right Stick X", gamepad1Ex.getRightX());
    telemetry.addData("Right Stick Y", gamepad1Ex.getRightY());

    // TODO: Add telemetry to show whether the b button is pressed on gamepad1.
    telemetry.addData("B Button Pressed", gamepad1Ex.getButton(GamepadKeys.Button.B));

    // TODO: Report to the user the difference between the left joystick y and the right joystick y
    // on gamepad1.
    telemetry.addData(
        "Left Stick y - Right stick y", Math.abs(gamepad1Ex.getRightY() - gamepad1Ex.getLeftY()));

    // TODO: Report to the user the sum of the left and right triggers on gamepad1.
    telemetry.addData(
        "sum of the left and right",
        gamepad1Ex.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)
            + gamepad1Ex.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
  }
}
