/*
 Made by Aryan Sinha,
 FTC team 202101101
  */

package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.config.BaseOpMode;
import org.firstinspires.ftc.teamcode.config.Hardware2;


/**
 * This class handles the manual driving.
 * @author aryansinha
 * also in a less prominent fashion
 * @soon-to-be-author author karthikperi
 */
@TeleOp(name="Basic: Fat OpMode", group="Linear Opmode")
public class teleop extends BaseOpMode {
    private final Hardware2 robot = new Hardware2(false);

    /**
     * {@inheritDoc}
     */

    public void runOpMode() throws InterruptedException {
        robot.initTeleOpIMU(hardwareMap);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        final ElapsedTime runtime = new ElapsedTime();
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            double y = gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            robot.getLeftDrive().setPower(frontLeftPower);
            robot.getBackLeftDrive().setPower(backLeftPower);
            robot.getRightDrive().setPower(frontRightPower);
            robot.getBackRightDrive().setPower(backRightPower);

            if (gamepad2.left_bumper) {
                robot.getLeftClaw().setPosition(-1);
                robot.getRightClaw().setPosition(1);
            }
            else if (gamepad2.right_bumper) {
                robot.getLeftClaw().setPosition(0.3);
                robot.getRightClaw().setPosition(-0.3);
            }

            if (gamepad2.left_trigger > 0) {
                robot.getArm().setPower(1.0);
            } else if (gamepad2.right_trigger == 0) {
                robot.getArm().setPower(0);
            }
        }
    }

    /**
     * {@inheritDoc}
     * @return
     */
    public Hardware2 getRobot() {
        return robot;
    }
}