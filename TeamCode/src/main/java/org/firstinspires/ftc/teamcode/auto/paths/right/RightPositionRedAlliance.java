package org.firstinspires.ftc.teamcode.auto.paths.right;

import static org.firstinspires.ftc.teamcode.common.utils.DriveUtils.encoderDrive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name= "Right Position Red Alliances")
public class RightPositionRedAlliance extends RightPositionOpMode {
    /**
     * {@inheritDoc}
     */
    @Override
    protected char getDirection() {
        return 'R';
    }

    @Override
    protected char getAlliance() {
        return 'R';
    }

    @Override
    protected void extraStep() {
        encoderDrive(this, 0.5, -10, -10, 5);
        //TODO: nothing for now.
    }

    protected void moveToShippingHub(char direction) {
        level = getLevel();
        if (level == 1) {
            driveUtils.encoderClaw(this, 1.0, -1300, 7);

        } else if (level == 0) {
            driveUtils.encoderClaw(this, 1.0, -3900, 7);
        }
        if (level == 2 || level == 1) {
            encoderDrive(this, 0.5, 10, 10, 5);
            robot.turnLeft(this, 10, 0.1);
            encoderDrive(this,0.5,3,3,5);
            robot.turnLeft(this, 10, 0.1);
            encoderDrive(this,0.5,3.5,3.5,5);
            robot.turnLeft(this, 5, 0.1);
            encoderDrive(this, 0.5, 20, 20, 5);
        } else if (level == 0 || level == -1) {
            safeRoute();
        }


    }

    @Override
    protected void safeRoute() {
        robot.turnLeft(this, 30, 0.1);
        encoderDrive(this, 0.5, 10,10,5);
        robot.turnRight(this, 10, 0.1);
        encoderDrive(this, 0.5, 22,22,5);
    }

}
