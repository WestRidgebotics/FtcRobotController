package org.firstinspires.ftc.teamcode.auto;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class findPosition extends OpenCvPipeline {
    Telemetry telemetry;
    enum Position {
        LEFT,
        RIGHT,
        FORWARD,
        NONE
    }
    int hSum = 0;
    int sSum = 0;
    int vSum = 0;
    static int hAvg = 0;
    static int sAvg = 0;
    static int vAvg = 0;

    private int width; // width of the image
    static Position location = Position.NONE;

    /**
     *
     * @param width The width of the image (check your camera)
     */
    public findPosition(int width) {
        this.width = width;
    }


    @Override
    public Mat processFrame(Mat input) {

        // "Mat" stands for matrix, which is basically the image that the detector will process
        // the input matrix is the image coming from the camera
        // the function will return a matrix to be drawn on your phone's screen

        // The detector detects regular stones. The camera fits two stones.
        // If it finds one regular stone then the other must be the skystone.
        // If both are regular stones, it returns NONE to tell the robot to keep looking

        // Make a working copy of the input matrix in HSV
        Rect rectangle = new Rect(20, 100,100, 100);
        Mat mat = new Mat();
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        if (mat.empty()) {
            location = Position.NONE;
            return input;
        }



        Mat subRegion = mat.submat(rectangle);
        for (int i = 0; i<subRegion.cols(); i++)
        {
            for (int j = 0; j<subRegion.rows(); j++)
            {
                hSum += subRegion.get(j, i)[0];
                sSum += subRegion.get(j, i)[1];
                vSum += subRegion.get(j, i)[2];
            }
        }

        hAvg = hSum/(subRegion.cols() * subRegion.rows());
        sAvg = sSum/(subRegion.cols() * subRegion.rows());
        vAvg = vSum/(subRegion.cols() * subRegion.rows());
        //#FA91FF Pink, #18E500 Green, #FF6100 Orange

        if (isColor(hSum, sSum, vSum, 280, 20, 80, 320, 50, 120))
            location = Position.LEFT;
        if (isColor(hSum, sSum, vSum, 100, 80, 70, 140, 50, 110))
            location = Position.FORWARD;
        if (isColor(hSum, sSum, vSum, 15, 0, 0, 50, 120, 120))
            location = Position.RIGHT;

        Imgproc.rectangle(mat, rectangle, new Scalar(20, 100, 100));

        return mat; // return the mat with rectangles drawn
    }

    public boolean isColor(int hSum, int sSum, int vSum, int hMin, int sMin, int vMin, int hMax, int sMax, int vMax) {
        if (hSum > hMin && hSum < hMax && sSum > sMin && sSum < sMax && vSum > vMax && vSum < vMin)
        {
            return true;
        }
        return false;
    }

    public Position getLocation() {
        return this.location;
    }
}