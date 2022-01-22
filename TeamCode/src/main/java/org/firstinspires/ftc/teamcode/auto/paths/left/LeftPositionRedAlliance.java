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
//        if (level == 0 || level == -1) {
//            encoderDrive(this, 0.5, -20, -20, 4);
//            robot.turnRight(this, 50, 0.2);
//            encoderDrive(this, 0.5, 50, 50, 8);
//            robot.turnLeft(this, 30, 0.5);
//            encoderDrive(this, 0.5, 160, 160, 10);
//        } else{
//            encoderDrive(this, 0.5, -10, -10, 5);
//        }
        encoderDrive(this, 0.5, -10, -10, 5);
    }

    @Override
    protected void safeRoute() {
        encoderDrive(this, 0.5, 5, 5, 5);
        robot.turnRight(this, 45, 0.3);
        encoderDrive(this, 0.5, 15, 15, 5);
        robot.turnLeft(this, 18.5, 0.3);
        encoderDrive(this, 0.5, 17,17,5);



        //dropToShippingHub(0);
    }

    /**
     * Gets the level at which the load needs to be dropped at.
     * @return The level at which the load needs to be dropped at.
     */

    }



