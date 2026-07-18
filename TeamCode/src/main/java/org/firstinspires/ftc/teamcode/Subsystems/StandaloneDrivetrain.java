package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

public class StandaloneDrivetrain extends SubsystemBase {

    public static final String FRONT_LEFT_MOTOR_ID = "front_left_motor";
    public static final String FRONT_RIGHT_MOTOR_ID = "front_right_motor";
    public static final String BACK_LEFT_MOTOR_ID = "back_left_motor";
    public static final String BACK_RIGHT_MOTOR_ID = "back_right_motor";

    private final DcMotor frontLeftMotor;
    private final DcMotor frontRightMotor;
    private final DcMotor backLeftMotor;
    private final DcMotor backRightMotor;

    public StandaloneDrivetrain(HardwareMap hwMap) {
        // Initialize motors from hardware map
        frontLeftMotor = hwMap.get(DcMotor.class, FRONT_LEFT_MOTOR_ID);
        frontRightMotor = hwMap.get(DcMotor.class, FRONT_RIGHT_MOTOR_ID);
        backLeftMotor = hwMap.get(DcMotor.class, BACK_LEFT_MOTOR_ID);
        backRightMotor = hwMap.get(DcMotor.class, BACK_RIGHT_MOTOR_ID);

        // Reverse left motors so positive power drives forward.
        // (This depends on your specific gear/motor orientation, but reversing left is
        // common)
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        // frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        // backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set to run without encoder for because we use simple voltage control
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
     * @param forward positive is forward
     * @param strafe  positive is right
     * @param rotate  positive is clockwise/right
     */
    public void driveRobotCentric(double forward, double strafe, double rotate) {
        // Calculate the maximum power out of the four calculated motor powers
        // to ensure we maintain the correct ratio if powers exceed 1.0
        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1.0);

        double frontLeftPower = (forward + strafe + rotate) / denominator;
        double backLeftPower = (forward - strafe + rotate) / denominator;
        double frontRightPower = (forward - strafe - rotate) / denominator;
        double backRightPower = (forward + strafe - rotate) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }

    /**
     * Stops all motors on the drivetrain.
     */
    public void stopMotors() {
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
    }
}
