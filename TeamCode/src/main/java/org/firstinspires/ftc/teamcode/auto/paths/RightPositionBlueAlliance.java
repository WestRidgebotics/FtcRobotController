package org.firstinspires.ftc.teamcode.auto.paths;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name= "Right Position Blue Alliances")
public class RightPositionBlueAlliance extends BasePathAutoOpMode {
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
        // TODO: we do as we learn.
    }
}


