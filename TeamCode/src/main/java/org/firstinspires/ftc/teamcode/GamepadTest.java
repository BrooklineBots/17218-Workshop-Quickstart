package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Gamepad Test")
public class GamepadTest extends OpMode {

    @Override
    public void init() {
    }

    @Override
    public void loop() {
        // 1. Add telemetry to show the right stick of gamepad1.
        telemetry.addData("Right Stick X", gamepad1.right_stick_x);
        telemetry.addData("Right Stick Y", gamepad1.right_stick_y);

        // 2. Add telemetry to show whether the b button is pressed on gamepad1.
        telemetry.addData("B Button Pressed", gamepad1.b);

        // 3. Report to the user the difference between the left joystick y and the right joystick y on gamepad1.
        double diffY = gamepad1.left_stick_y - gamepad1.right_stick_y;
        telemetry.addData("Left Y - Right Y", diffY);

        // 4. Report to the user the sum of the left and right triggers on gamepad1.
        double sumTriggers = gamepad1.left_trigger + gamepad1.right_trigger;
        telemetry.addData("Trigger Sum", sumTriggers);
        
        telemetry.update();
    }
}
