package org.firstinspires.ftc.teamcode.auto.paths;

import static org.firstinspires.ftc.teamcode.common.utils.DriveUtils.encoderDrive;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.common.BaseNewOpMode;
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


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
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
            tfod.setZoom(1, 16.0/9.0);
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();

        waitForStart();

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
                        i++;
                    }
                    telemetry.update();
                }

                // Set the direction.
                setLevel(1);

                doAutoDriving();
            }
        }
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
        moveToShippingElement(getDirection());
        dropToShippingElement(getLevel());
        extraStep();
    }

    protected void dropToShippingElement(int level) {
        if (level == 1) {
            // drop at the bottom most
            // claw.release()
        } else if (level == 2) {
            // drop at the middle one.
        } else if (level == 3) {
            // drop at the top one.
        } else {
        }
    }

    protected void moveToShippingElement(char direction) {
        if (direction == 'L') {
            encoderDrive(this, 0.5, 3, 3, 5);
            robot.turnRight(this,45, 0.1);
            encoderDrive(this, 0.5, 21, 21, 5);
        } else if (direction == 'R'){
            encoderDrive(this, 0.5, 3, 3, 5);
            robot.turnLeft(this,45, 0.1);
            encoderDrive(this, 0.5, 21, 21, 5);
        }
    }

    protected abstract void extraStep();


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
