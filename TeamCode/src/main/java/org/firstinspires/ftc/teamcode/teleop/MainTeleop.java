package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.subsystem.Intake;
import org.firstinspires.ftc.teamcode.subsystem.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystem.PIDF_Arm;

@TeleOp(name = "Main Teleop Mode")
public class MainTeleop extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive drive = new MecanumDrive();
    private PIDF_Arm pidArm = new PIDF_Arm();
    private Intake intake = new Intake();

    @Override
    public void init() {
        drive.init(hardwareMap);
        pidArm.init(hardwareMap);
        intake.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        drive.setControl(gamepad1);

        pidArm.setControl(gamepad2);

        intake.setControl(gamepad1);

        telemetry.addData("Status", "Enabled");
        telemetry.addData("Time Remaining", Constants.Time.teleopTime - this.runtime.seconds());
        telemetry.addData("Upper Arm Position", pidArm.getUpperPos());
        telemetry.addData("Lower Arm Position", pidArm.getLowerPos());
        telemetry.update();
    }

    @Override
    public void stop() {
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}
