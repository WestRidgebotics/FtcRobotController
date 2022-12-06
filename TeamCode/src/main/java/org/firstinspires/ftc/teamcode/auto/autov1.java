//package org.firstinspires.ftc.teamcode.auto;
//
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//
//import org.firstinspires.ftc.teamcode.config.BaseOpMode;
//import org.firstinspires.ftc.teamcode.config.DriveUtils;
//import org.firstinspires.ftc.teamcode.config.Hardware2;
//
///**
// * @author KarthikPeri
// */
//@Autonomous(name="autov1")
//public class autov1 extends BaseOpMode {
//    private static Hardware2 robot = new Hardware2(false);
//
//    /*
//    KIMCHI RAMEN
//     */
//
//    public void runOpMode() throws InterruptedException {
//        robot.initTeleOpIMU(hardwareMap);
//        int position = -1;
//        waitForStart();
//
//        if (position == 0) {
//            DriveUtils.encoderDrive(this, 0.5, -24, -24, 7);
//            // Drives forward to go into the middle parking area
//            //DriveUtils.encoderDrive(this, 0.5, 10, 10, 7);
//        } else if (position == 1) {
//            // Move Left
//            DriveUtils.encoderStrafe(this,0.4,27,5);
//            // Strafes left
//            DriveUtils.encoderDrive(this, 0.4, -12, -12, 5);
//            // Drives forward to go into the left parking area
//        } else if (position == -1) {
//            // Move Right
//            DriveUtils.encoderStrafe(this, 0.4, -27, 5);
//            //Strafes Right
//            DriveUtils.encoderDrive(this, 0.4, -12, -12, 5 );
//            // Drives forward to go into the left parking area
//        }
//
//        }
//
//    public Hardware2 getRobot() {
//        return robot;
//    }
//}
package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.config.BaseOpMode;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous(name="Auto: SkyStone Detector")
public class autov1 extends BaseOpMode {
    // Handle hardware stuff...

    int width = 320;
    int height = 240;
    // store as variable here so we can access the location
    findPosition detector = new findPosition(width);
    OpenCvCamera phoneCam;

    public void runOpMode() {
        // robot logic...

        // https://github.com/OpenFTC/EasyOpenCV/blob/master/examples/src/main/java/org/openftc/easyopencv/examples/InternalCameraExample.java
        // Initialize the back-facing camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        // Connect to the camera
        phoneCam.openCameraDevice();
        // Use the SkystoneDetector pipeline
        // processFrame() will be called to process the frame
        phoneCam.setPipeline(detector);
        // Remember to change the camera rotation
        phoneCam.startStreaming(width, height, OpenCvCameraRotation.SIDEWAYS_LEFT);

        //...

        findPosition.Position location = detector.getLocation();
        telemetry.addLine("can't see");
        telemetry.addData("havg : ", findPosition.hAvg);
        telemetry.addData("savg : ", findPosition.sAvg);
        telemetry.addData("vavg : ", findPosition.vAvg);

        waitForStart();
        while (opModeIsActive()) {
            if (location == findPosition.Position.NONE) {

            } else {
                // Grab the skystone
            }
        }

        // more robot logic...
    }

    @Override
    public org.firstinspires.ftc.teamcode.config.Hardware2 getRobot() {
        return null;
    }
}
