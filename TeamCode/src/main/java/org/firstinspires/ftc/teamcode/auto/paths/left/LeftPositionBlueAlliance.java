package org.firstinspires.ftc.teamcode.auto.paths.left;


import static org.firstinspires.ftc.teamcode.common.utils.DriveUtils.encoderDrive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name= "Left Position Blue Alliances")
public class LeftPositionBlueAlliance extends LeftPositionOpMode {
    /**
     * {@inheritDoc}
     */
    @Override
    protected char getDirection() {
        return 'L';
    }

    @Override
    protected char getAlliance() {
        return 'B';
    }

    @Override
    protected void extraStep() {
        encoderDrive(this, 0.5, -10, -10, 5);
        // TODO: we do as we learn.
    }

    protected void moveToShippingHub(char direction) {
        level = getLevel();
        if (level == 1) {
            driveUtils.encoderClaw(this, 0.5, -1300, 7);

        } else if (level == 0) {
            driveUtils.encoderClaw(this, 0.5, -3900, 7);
        }
        if (level == 2 || level == 1) {
            encoderDrive(this, 0.5, 10, 10, 5);
            robot.turnRight(this, 10, 0.1);
            encoderDrive(this, 0.5, 3, 3, 5);
            robot.turnRight(this, 10, 0.1);
            encoderDrive(this, 0.5, 3.5, 3.5, 5);
            robot.turnRight(this, 5, 0.1);
            encoderDrive(this, 0.5, 20, 20, 5);
        } else if (level == 0 || level == -1) {
            safeRoute();
        }


        /**
         * Gets the level at which the load needs to be dropped at.
         * @return The level at which the load needs to be dropped at.
         */

    }

    @Override
    protected void safeRoute() {
        // We will drop the load at the default 0 level so don't lift the claw.
        robot.turnRight(this, 30, 0.1);
        encoderDrive(this, 0.5, 10,10,5);
        robot.turnLeft(this, 10, 0.1);
        encoderDrive(this, 0.5, 22,22,5);
    }
}



