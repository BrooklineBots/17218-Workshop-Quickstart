package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

@TeleOp(name = "Gamepad Tester")


public class GamepadTester extends CommandOpMode {

    private GamepadEx gamepad1Ex;
    private Telemetry telemetry;

    public void initialize () {
        gamepad1Ex = new GamepadEx(gamepad1);
    }

    public void run (){
        telemetry.addData("Right Stick X", gamepad1Ex.getRightX());
        telemetry.addData("Right Stick Y", gamepad1Ex.getRightY());

        telemetry.addData ("B Button Pressed", gamepad1Ex.getButton(GamepadKeys.Button.B));
        telemetry.addData("Left Stick y - Right stick y", Math.abs(gamePad1Ex.getRighty() - gamePad1Ex.getLefty()));
        telemetry.addData("Sum of left and right triggers", gamepad1Ex.getTrigger(GamepadKeys.Trigger.LEFT)+ gamepad1Ex.getTrigger(GamepadKeys.Trigger.RIGHT));
    }
}