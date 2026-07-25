/* ============================================================= *
 *                 Turtle Tracer — Auto-Generated                *
 *                                                               *
 *  Version: 2.2.1.                                              *
 *  Copyright (c) 2026 Matthew Allen                             *
 *                                                               *
 *  THIS FILE IS AUTO-GENERATED — DO NOT EDIT MANUALLY.          *
 *  Changes will be overwritten when regenerated.                *
 * ============================================================= */

package org.firstinspires.ftc.teamcode.Commands.AutoCommands;

import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.InvertedFTCCoordinates;
import com.pedropathing.ftc.PoseConverter;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;
import com.turtletracerlib.pathing.NamedCommands;
import java.io.IOException;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;

public class hailey extends SequentialCommandGroup {

  private final Follower follower;

  // Poses
  private Pose startPoint;
  private Pose point1;
  private Pose point1_line0_control1;
  private Pose point1_line0_control2;
  private Pose point1_line0_control3;
  private Pose point2;
  private Pose shoot;
  private Pose shoot_line2_control1;
  private Pose point4;
  private Pose point5;
  private Pose point6;
  private Pose point7;

  // Path chains
  private PathChain startPointTOpoint1;
  private PathChain point6TOpoint7;

  public hailey(final Drivetrain drive, HardwareMap hw, Telemetry telemetry)
    throws IOException {
    this.follower = drive.getFollower();

    // Load poses
    startPoint = new Pose(56.000, 9.300, Math.toRadians(0));
    point1 = new Pose(36.000, 36.000, Math.toRadians(0));
    point1_line0_control1 = new Pose(55.106, 19.260);
    point1_line0_control2 = new Pose(56.560, 20.779);
    point1_line0_control3 = new Pose(50.599, 33.473);
    point2 = new Pose(18.000, 43.000, Math.toRadians(0));
    shoot = new Pose(60.000, 84.000, Math.toRadians(0));
    shoot_line2_control1 = new Pose(53.037, 71.775);
    point4 = new Pose(29.000, 37.000, Math.toRadians(0));
    point5 = new Pose(25.500, 37.000, Math.toRadians(0));
    point6 = new Pose(20.000, 36.000, Math.toRadians(0));
    point7 = new Pose(44.000, 56.000, Math.toRadians(0));

    follower.setStartingPose(startPoint);

    buildPaths();

    addCommands(
      new FollowPathCommand(follower, startPointTOpoint1),
      new FollowPathCommand(follower, point6TOpoint7)
    );
  }

  public void buildPaths() {
    startPointTOpoint1 = follower
      .pathBuilder()
      .addPath(
        new BezierCurve(
          startPoint,
          new Pose(55.106, 19.260),
          new Pose(56.560, 20.779),
          new Pose(50.599, 33.473),
          point1
        )
      )
      .setTangentHeadingInterpolation()
      .addParametricCallback(0.962, NamedCommands.getCommand("intake1"))
      .addPath(new BezierLine(point1, point2))
      .setTangentHeadingInterpolation()
      .addPath(new BezierCurve(point2, new Pose(53.037, 71.775), shoot))
      .setHeadingInterpolation(
        HeadingInterpolator.facingPoint(new Pose(128.000, 128.000))
      )
      .addPoseCallback(
        new Pose(128.000, 128.000, Math.toRadians(0.000)),
        NamedCommands.getCommand("shoot"),
        0.500
      )
      .addParametricCallback(0.040, NamedCommands.getCommand("stopIntake1"))
      .addPath(new BezierLine(shoot, point4))
      .setTangentHeadingInterpolation()
      .addPath(new BezierLine(point4, point5))
      .setTangentHeadingInterpolation()
      .addPath(new BezierLine(point5, point6))
      .setTangentHeadingInterpolation()
      .build();

    point6TOpoint7 = follower
      .pathBuilder()
      .addPath(new BezierLine(point6, point7))
      .setTangentHeadingInterpolation()
      .build();
  }
}
