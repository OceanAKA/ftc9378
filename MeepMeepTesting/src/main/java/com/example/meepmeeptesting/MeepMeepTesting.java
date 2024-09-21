package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 17.75)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35,-60.4, Math.toRadians(90)))
//                .lineToYLinearHeading(-25, Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-54.0,-54.0, Math.toRadians(225)), -90)
                .strafeTo(new Vector2d(-35,-35))
                .splineToLinearHeading(new Pose2d(-35, -25, Math.toRadians(180)), -90)
                // pick up
                .splineToLinearHeading(new Pose2d(-54.0,-54.0, Math.toRadians(225)), -90)
                .strafeTo(new Vector2d(-45,-35))

                //shoot
                .splineToLinearHeading(new Pose2d(-47, -25, Math.toRadians(180)), -90)
                // shoot
                .splineToLinearHeading(new Pose2d(-54.0,-54.0, Math.toRadians(225)), -90)
                //pick up
                .splineToLinearHeading(new Pose2d(-56, -25, Math.toRadians(180)), -90)
                // shoot
                .splineToLinearHeading(new Pose2d(-54.0,-54.0, Math.toRadians(225)), -90)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}