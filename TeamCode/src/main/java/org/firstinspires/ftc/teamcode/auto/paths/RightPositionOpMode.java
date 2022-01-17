package org.firstinspires.ftc.teamcode.auto.paths;

import static org.firstinspires.ftc.teamcode.common.utils.DriveUtils.encoderDrive;

/**
 * This class should be extended by all classes that are posititioned in the right side.
 */
public abstract class RightPositionOpMode extends BasePathAutoOpMode {
    /**
     * {@inheritDoc}
     */
    protected void moveToShippingElement(char direction) {
        if (level == 0) {
        } else if (level == 1) {
            driveUtils.encoderClaw(this, 0.5, -1300, 7);

        } else if (level == 2) {
            driveUtils.encoderClaw(this,0.5, -3900, 7);
        } else{
        }
        if (level == 0 || level == 1 ) {
            encoderDrive(this, 0.5, 10, 10, 5);
            robot.turnLeft(this,35, 0.1);
            encoderDrive(this, 0.5, 28, 28, 5);
        }


    }
    protected int getLevel() {
        return level != -1 ? Math.abs(2 - level) : -1;
    }
}
