package org.firstinspires.ftc.teamcode.Commands;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.LL;
import org.firstinspires.ftc.teamcode.Utils.Utils;
import com.seattlesolvers.solverslib.controller.PIDController;

public class LimelightAlignCommand extends CommandBase {
  private final Drivetrain drive;
  private final LL limelight;
  private final GamepadEx gamepad;

  private final double gamepadTolerance = 0.1;

  // PID constants for alignment // FIXME: you will likely need to tune these!
  private final double minPower = 0.05; // Minimum power to overcome friction
  private final double kP = 0.01;
  private final double kI = 0.0;
  private final double kD = 0.0;
  private final PIDController pidController = new PIDController(kP, kI, kD);

  public LimelightAlignCommand(Drivetrain drive, LL limelight, GamepadEx gamepad) {
    this.drive = drive;
    this.limelight = limelight;
    this.gamepad = gamepad;
    addRequirements(drive); // Overrides the default DriveCommand

    // We want the target x-offset (tx) to be 0
    pidController.setSetPoint(0);
    // Tolerance for tx error and tx derivative
    pidController.setTolerance(1.5, 5);
  }

  @Override
  public void initialize() {
    // In case the pipeline was object
    limelight.setAprilTagPipeline();
  }

  @Override
  public void execute() {
    double forward = -gamepad.getLeftY();
    double strafe = -gamepad.getLeftX();
    double rotate = 0.0;

    LLResult result = limelight.getLatestResult();

    if (result != null && result.isValid()) {
      double tx = result.getTx();

      rotate = pidController.calculate(tx);

      // Add minimum power to overcome friction when tracking
//      if (rotate > 0) {
//        rotate += minPower;
//      } else if (rotate < 0) {
//        rotate -= minPower;
//      }

      // Clamp to prevent spinning too fast
//      rotate = Math.max(-0.5, Math.min(0.5, rotate));
    }

    // Drive field-centric with manual translation but auto-rotation
    drive.driveFieldCentric(forward, strafe, rotate);
  }

  @Override
  public boolean isFinished() {
    return !Utils.isWithinTolerance(0, gamepad.getRightX(), gamepadTolerance)
        || !Utils.isWithinTolerance(0, limelight.getLatestResult().getTx(), 1.0);
  }

  @Override
  public void end(boolean interrupted) {
    drive.stopMotors();
  }
}
