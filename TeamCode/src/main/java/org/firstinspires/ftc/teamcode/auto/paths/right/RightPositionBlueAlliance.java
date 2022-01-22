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
        encoderDrive(this, 0.5, 10, 10, 5);
        robot.turnRight(this, 90, 0.1);
        encoderDrive(this, 0.5, 26,26, 5);
        robot.getCarousel().setPower(1);
        sleep(4500);
        robot.getCarousel().setPower(0);
        robot.turnLeft(this, 90, 0.1);
        encoderDrive(this, 0.5, 55, 55, 8);
        robot.turnLeft(this, 75, 0.3);
        encoderDrive(this, 0.5, 37, 37, 7);
        //dropToShippingHub(0);
    }

    /**
     * Gets the level at which the load needs to be dropped at.
     * @return The level at which the load needs to be dropped at.
     */

    }



