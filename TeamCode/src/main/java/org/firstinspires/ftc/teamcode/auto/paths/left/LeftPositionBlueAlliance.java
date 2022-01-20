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
        encoderDrive(this, 0.5, -10,-10,5);
        // TODO: we do as we learn.
    }

    @Override
    protected void safeRoute() {

    }

    /**
     * Gets the level at which the load needs to be dropped at.
     * @return The level at which the load needs to be dropped at.
     */

    }



