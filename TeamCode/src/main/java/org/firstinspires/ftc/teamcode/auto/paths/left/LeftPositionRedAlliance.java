package org.firstinspires.ftc.teamcode.auto.paths.left;


import static org.firstinspires.ftc.teamcode.common.utils.DriveUtils.encoderDrive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.auto.paths.left.LeftPositionOpMode;


@Autonomous(name= "Left Position Red Alliances")
public class LeftPositionRedAlliance extends LeftPositionOpMode {
    /**
     * {@inheritDoc}
     */
    @Override
    protected char getDirection() {
        return 'L';
    }

    @Override
    protected char getAlliance() {
        return 'R';
    }

    @Override
    protected void extraStep() {
        encoderDrive(this, 0.5, -10,-10,5);
        // TODO: we do as we learn.
    }

    @Override
    protected void safeRoute() {
        //TODO Delete this
        encoderDrive(this, 0.5, 20,20,5);
    }

    /**
     * Gets the level at which the load needs to be dropped at.
     * @return The level at which the load needs to be dropped at.
     */

    }



