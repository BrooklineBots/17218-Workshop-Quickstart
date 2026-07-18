package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

@TeleOp(name = "Gamepad tester")
public class GamepadTest extends CommandOpMode {

    private GamepadEx gamepad1Ex;

    public void initialize(){
        gamepad1Ex = new GamepadEx(gamepad1);
    }
    public void run(){
        telemetry.addData("right stick x: ", gamepad1Ex.getRightX());
        telemetry.addData("right stick y:", gamepad1Ex.getRightY());
        telemetry.addData("b button pressed: ", gamepad1Ex.isDown(GamepadKeys.Button.B));
        telemetry.addData("difference: ", Math.abs(gamepad1Ex.getLeftY()-gamepad1Ex.getRightY()));
        telemetry.addData("sum of triggers: ", gamepad1Ex.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) + gamepad1Ex.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
    }
}
