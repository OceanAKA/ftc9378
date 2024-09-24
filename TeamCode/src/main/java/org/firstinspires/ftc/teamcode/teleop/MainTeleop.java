package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystem.Arm;
import org.firstinspires.ftc.teamcode.subsystem.MecanumDrive;

@TeleOp(name = "Main Teleop Mode")
public class MainTeleop extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private MecanumDrive drive = new MecanumDrive();
    private Arm arm = new Arm();

    public void init() {
        drive.init(hardwareMap);
        arm.init(hardwareMap);
    }

    public void start() {
        runtime.reset();
    }

    public void loop() {
        drive.setControl(gamepad1, false);
        arm.setControl(gamepad1);

        telemetry.addData("Status","Enabled");
        telemetry.addData("Time Remaining",Constants.Time.teleopTime-this.runtime.seconds());
        telemetry.update();
    }

    public void stop() {
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }

}
