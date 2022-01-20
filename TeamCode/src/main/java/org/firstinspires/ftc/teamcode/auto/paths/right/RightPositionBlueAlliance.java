package org.firstinspires.ftc.teamcode.auto.paths.right;


import static org.firstinspires.ftc.teamcode.common.utils.DriveUtils.encoderDrive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name= "Right Position Blue Alliances")
public class RightPositionBlueAlliance extends RightPositionOpMode {
    /**
     * {@inheritDoc}
     */
    @Override
    protected char getDirection() {
        return 'R';
    }

    @Override
    protected char getAlliance() {
        return 'B';
    }

    @Override
    protected void extraStep() {
        encoderDrive(this, 0.5, -10,-10,5);
        // TODO: we do as we learn.
    }

    @Override
    protected void safeRoute() {
        encoderDrive(this, 0.5, 10, 10, 5);
        robot.turnRight(this, 90, 0.1);
        encoderDrive(this, 0.5, 22,22, 5);
        robot.turnLeft(this, 90, 0.1);
        encoderDrive(this, 0.5, 24, 24, 5);
    }

    /**
     * Gets the level at which the load needs to be dropped at.
     * @return The level at which the load needs to be dropped at.
     */

    }



