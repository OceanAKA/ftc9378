package org.firstinspires.ftc.teamcode.subsystem;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Intake {
    private DcMotor intake;

    public void init(HardwareMap hwMap) {
        intake = hwMap.get(DcMotor.class, Constants.Intake.intake);
    }

    /*public class IntakeSample implements Action {
        public boolean run(@NonNull TelemetryPacket packet) {
            intake.setPower(0.6);
        }
    }*/
    public void setIntake(double power) {
        intake.setPower(power);
    }
}
