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
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.PIDF_Arm;

@Config
@Autonomous(name = "Test", group = "Autonomous")
public class Test extends LinearOpMode {

    private static final PIDF_Arm pidfArm = new PIDF_Arm();

    public static class intake {
        public static class Lift implements Action {
            private boolean initialized = false;
            private Gamepad gamepad1;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    PIDF_Arm.armToIntakePos();
                    initialized = true;
                    packet.put("Action", "Lifting Arm");
                } else {
                    pidfArm.setControl(gamepad1);
                    packet.put("Action", "Arm in Motion");
                }

                if (Math.abs(pidfArm.getUpperPos() - Constants.Arm.INTAKE[0]) < 10 &&
                        Math.abs(pidfArm.getLowerPos() - Constants.Arm.INTAKE[1]) < 10) {
                    packet.put("Action", "Arm at Position");
                    return true;
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
        Pose2d initialPose = new Pose2d(35, -60.4, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Action trajectory;

        pidfArm.init(hardwareMap);

        TrajectoryActionBuilder trajectory1 = drive.actionBuilder(initialPose)
                .setTangent(Math.toRadians(180))
                .lineToX(45)
                .lineToX(35)
                .setTangent(Math.toRadians(90))
                .lineToYLinearHeading(-13, Math.toRadians(0))
                .strafeToLinearHeading(new Vector2d(45, -13), Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-57)
                .lineToY(-13)
                .strafeToConstantHeading(new Vector2d(55, -13))
                .setTangent(Math.toRadians(90))
                .lineToY(-57)
                .lineToY(-13)
                .strafeToConstantHeading(new Vector2d(61, -13))
                .setTangent(Math.toRadians(90))
                .lineToY(-57);

        waitForStart();

        if (isStopRequested()) return;

        trajectory = trajectory1.build();

        Actions.runBlocking(new SequentialAction(
                trajectory,
                intake.intakePos()
        ));

        while (opModeIsActive() && !intake.intakePos().run(new TelemetryPacket())) {
            telemetry.update();
        }
    }
}
