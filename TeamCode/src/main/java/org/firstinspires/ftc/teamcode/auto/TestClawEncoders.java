package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.auto.paths.BasePathAutoOpMode;
import org.firstinspires.ftc.teamcode.common.BaseNewOpMode;
import org.firstinspires.ftc.teamcode.config.HardwareNew;
@Disabled
@Autonomous(name= "Test Claw Encoders")
public class TestClawEncoders extends BaseNewOpMode {
    private final HardwareNew robot = new HardwareNew(true);


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.getArm().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();
        telemetry.addData("Current Position of Encoders", robot.getArm().getCurrentPosition());
        telemetry.update();
        sleep(60000);
        telemetry.addData("Current Position of Encoders", robot.getArm().getCurrentPosition());
        telemetry.update();

    }

    @Override
    public HardwareNew getRobot() {
        return robot;
    }
}
