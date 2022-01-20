package org.firstinspires.ftc.teamcode.auto.paths.left;

import static org.firstinspires.ftc.teamcode.common.utils.DriveUtils.encoderDrive;

import org.firstinspires.ftc.teamcode.auto.paths.BasePathAutoOpMode;

/**
 * This class should be extended by all classes that are posititioned in the right side.
 */
public abstract class LeftPositionOpMode extends BasePathAutoOpMode {
    /**
     * {@inheritDoc}
     */
    protected void moveToShippingHub(char direction) {
        level = getLevel();

        if (level == 0 || level == -1 ) {
        } else if (level == 1) {
            driveUtils.encoderClaw(this, 0.5, -1300, 7);

        } else if (level == 2) {
            driveUtils.encoderClaw(this,0.5, -3900, 7);
        }
        if (level == 0 || level == 1 ) {
            encoderDrive(this, 0.5, 10, 10, 5);
            robot.turnRight(this,35, 0.1);
            encoderDrive(this, 0.5, 28, 28, 5);
        } else if (level == 2 || level == -1){
            safeRoute();
        }
    }

}
