package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.PIDF_Arm;

import com.acmerobotics.roadrunner.ParallelAction;

import kotlin.contracts.Returns;

@Config
@Autonomous(name = "Test", group = "Autonomous")
public class Test extends LinearOpMode {

    public static class intake {

        public static class Lift implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    PIDF_Arm.armToIntakePos();
                    initialized = true;
                    return false;
                }
                return false;
            }
        }

        public static Action intakePos() {
            return new Lift();
        }
    }


    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-35, -60.4, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Action trajectory;


        TrajectoryActionBuilder trajectory1 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(35.0, -27.0, Math.toRadians(0)), Math.toRadians(90))
                //pickup
                .stopAndAdd(intake.intakePos())
                .splineToLinearHeading(new Pose2d(54.0, -54.0, Math.toRadians(-45)), Math.toRadians(50))
                //drop
                .splineToLinearHeading(new Pose2d(44, -27, Math.toRadians(0)), Math.toRadians(50))
                //PICKUP
                .splineToLinearHeading(new Pose2d(54.0, -54.0, Math.toRadians(-45)), Math.toRadians(50))
                //DROP
                .splineToLinearHeading(new Pose2d(54.0, -54.0, Math.toRadians(0)), Math.toRadians(50))
                //pickup
                .splineToLinearHeading(new Pose2d(54.0, -27.0, Math.toRadians(0)), Math.toRadians(50))
                //pickup
                .splineToLinearHeading(new Pose2d(54.0, -54.0, Math.toRadians(-45)), Math.toRadians(50));
        //drop



        waitForStart();


        if (isStopRequested()) return;

        trajectory = trajectory1.build();

        Actions.runBlocking(
                new SequentialAction(
                        trajectory
                )
        );
    }
}