package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.config.BaseOpMode;
import org.firstinspires.ftc.teamcode.config.DriveUtils;
import org.firstinspires.ftc.teamcode.config.Hardware2;

@Autonomous(name = "armtest")
public class armTest extends BaseOpMode {
    private static Hardware2 robot = new Hardware2(true);
    @Override
    public void runOpMode() throws InterruptedException {
        robot.initTeleOpIMU(hardwareMap);
        waitForStart();
        DriveUtils.encoderClaw(this, 0.7, 1300, 5);
        telemetry.addLine("Hi");
    }

    @Override
    public Hardware2 getRobot() {
        return null;
    }
}
