package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class liftTest extends LinearOpMode {
    Hardware2 robot = new Hardware2();
    @Override
    public void runOpMode() throws InterruptedException {
        robot.initTeleOpIMU(hardwareMap);
        while (opModeIsActive())
        {
            robot.verticalLiftMotor.setPower(0.4);
        }
    }
}
