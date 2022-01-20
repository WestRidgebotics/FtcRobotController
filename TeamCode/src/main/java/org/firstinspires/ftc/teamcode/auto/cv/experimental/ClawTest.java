package org.firstinspires.ftc.teamcode.auto.cv.experimental;

import org.firstinspires.ftc.teamcode.common.BaseNewOpMode;
import org.firstinspires.ftc.teamcode.config.HardwareNew;

public class ClawTest extends BaseNewOpMode {
    private HardwareNew robot = new HardwareNew();
    @Override
    public HardwareNew getRobot() {
        return null;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();
        robot.getArm().setPower(0.2);
        sleep(1000);
        robot.getArm().setPower(0);
    }
}
