package org.firstinspires.ftc.teamcode.auto.paths;

import static org.firstinspires.ftc.teamcode.common.utils.DriveUtils.logData;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.common.BaseNewOpMode;
import org.firstinspires.ftc.teamcode.common.utils.DriveUtils;
import org.firstinspires.ftc.teamcode.config.HardwareNew;


import java.util.List;

public abstract class BasePathAutoOpMode extends BaseNewOpMode {
    static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";

    static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };

    final int[] rectangleLeft = {
            0,

    };

    static final String VUFORIA_KEY =
            "AdiwP4P/////AAABmQu7AHs7VU6RvvL1paz4jKJQNUjgh+DDD4c9C/tVmRuOqRAF+Y69eVtyI8NGEPEw3gaojzAgaz+Kl8ArVV5fj/m0e8kemLG2MgXZ6OqSfgrHqGUKPoYuh+EUkhC+6pjfBhUxMcuWm+BcqUGIMma5rqNzrIHgX9kZP4UUritotTJlWqJziCrsURaVEilJlD+wK2i/wKaNkzlftWGY9/j3hfEdu8CUTsn+1H7gTd5WZwtVsvBLNVW2tuIy4LfKqS3L+B+h2iCkeI05PVYtKLQAVFWKCRpmYEVY6lFf7evJDgZON98e/Vr4f9Do+zQqnuw1UUttcEr3sXg0I2cMzDpJzrNS1AB6D/1uCwtDKfXVvlsI";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    protected VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    protected TFObjectDetector tfod;
    protected final HardwareNew robot = new HardwareNew(true);
    protected char direction;
    protected int level;
    protected ElapsedTime runtime = new ElapsedTime();
    protected DriveUtils driveUtils = new DriveUtils();


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.getArm().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.getLeftClaw().setPosition(0.3);
        robot.getRightClaw().setPosition(0.3);
        initVuforia();
        initTfod();
        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();

            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(1.25, 16.0/9.0);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        telemetry.addData("level: ", level);
                        i++;
                    }
                    telemetry.update();

                    /**
                     * Algorithm
                     * 1. We will max wait for 10 seconds and if we don't find nay signals, we will
                     * return -1 to auto driving that uses fallback option to drive to a particular level.
                     * 2. If we get to know that duck was scanned, we can try to use that information.
                     * 3. Best case is when both markers and duck can be recognized.
                     */
                    // Have we seen duck as yet?
                    Recognition duck = null;
                    for (Recognition r : updatedRecognitions) {
                        if (r.getLabel().equalsIgnoreCase("Duck")) {
                            telemetry.addData("identified duck", r.getLeft());
                            duck = r;
                            break;
                        }
                    }

                    analyzeRecognitions(duck, updatedRecognitions);
                    // if the level was set then we can get out of this.
                    if (getLevel() >= 0) {
                        telemetry.addData("level: ", level);
                        telemetry.update();
                        break;
                    }

                    backupFromModeling(duck);
                    // if the level was set then we can get out of this.
                    if (getLevel() >= 0) {
                        telemetry.addData("level: ", level);
                        telemetry.update();
                        break;
                    }

                    // if duck is still not seen and we are beyond 5 seconds then use fallback.
                    if (getLevel() == -1 && runtime.seconds() >= 10) {
                        telemetry.addData("Could not find within 10 sec so coming out ", level);
                        telemetry.update();
                        setLevel(-1);
                        break;
                    }
                }

            }

        }
        logData(this, "Finally selected level is ", String.valueOf(getLevel()));
        // add an option setlevel for testing.
        //setLevel(-1);
        doAutoDriving();
    }

    private void analyzeRecognitions(Recognition duck, List<Recognition> recognitions) {
        // If nothing works then we fall back to level = -1.
        int level = -1;
        if (recognitions.size() >= 3 && duck != null) {
            // we have atleast 3 objects to continue.
            int duckPos = 0;
            for (Recognition r : recognitions) {
                if (r != duck) {
                    if (isLeft(r, duck)) {
                        duckPos++;
                    }
                }
            }
            level = duckPos;
        }
        setLevel(level);
    }

    private void backupFromModeling(Recognition duck) {
        int level = -1;
        if (duck == null) {
            setLevel(-1);
            return;
        }
        float duckLeft = duck.getLeft();
        float duckRight = duck.getRight();
        // if duck is here then use that coordinate.
        // else use the hardcoded coordinate from our tests.

        if (duckRight <= 600 && duckLeft >= 430) {
            level = 0;
        } else if (duckRight <= 410 && duckLeft >= 250) {
            level = 1;
        } else if (duckRight <= 290 && duckLeft >= 50) {
            level = 2;
        }

        setLevel(level);
    }

    // This method returns true if r1 is left to r2.
    private boolean isLeft(Recognition r1, Recognition r2) {
        float left1 = r1.getLeft();
        float left2 = r2.getLeft();

        // TODO check the cordinates and figure out left.
        if (left1 < left2)
        {
            return true;
        }
        return false;
    }


    /**
     * Gets the level at which the load needs to be dropped at.
     * @return The level at which the load needs to be dropped at.
     */
    protected int getLevel() {
        return level;
    }


    /**
     * Returns the direction to which the robot should move.
     * @return The direction.
     */
    protected abstract char getDirection();

    /**
     * Returns the alliance as initial.
     * @return Alliance label.
     */
    protected abstract char getAlliance();


    /**
     * Sets the level. Avoid changing in the child classes for surprises.
     * @param level the level.
     */
    protected void setLevel(int level) {
        this.level = level;
    }

    protected void doAutoDriving() {
        moveToShippingHub(getDirection());
        dropToShippingHub(getLevel());
        extraStep();
    }

    protected void dropToShippingHub(int level) {
        robot.getRightClaw().setPosition(1.0);
        robot.getLeftClaw().setPosition(1.0);
        sleep(500);
    }

    /**
     * moves the arm to desired postition
     * then moves to the shipping hub
     * @param direction the direction that the robot resides on the mat at the beginning of the match
     */
    protected abstract void moveToShippingHub(char direction);

    protected abstract void extraStep();

    protected abstract void safeRoute();


    /**
     * {@inheritDoc}
     */
    public HardwareNew getRobot() {
        return robot;
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }

}
