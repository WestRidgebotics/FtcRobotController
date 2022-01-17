package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.common.BaseNewOpMode;
import org.firstinspires.ftc.teamcode.common.utils.DriveUtils;
import org.firstinspires.ftc.teamcode.config.HardwareNew;

@Autonomous(name= "Other Test Claw Encoders")
public class OtherTestClawEncoders extends BaseNewOpMode {
    private final HardwareNew robot = new HardwareNew(true);
    private final DriveUtils driveUtils = new DriveUtils();


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.getArm().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();
        driveUtils.encoderClaw(this, 0.5, -1300, 7);


    }

    @Override
    public HardwareNew getRobot() {
        return robot;
    }
}
