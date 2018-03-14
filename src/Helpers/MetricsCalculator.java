package Helpers;

import Models.Metrics;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * Created by AaronR on 2/18/18.
 * for ?
 */
public class MetricsCalculator {


    public static void getVarianceOfLaplacian(Mat mat, double percentX, double percentY, double radiusPercent , Metrics metrics){

            try {
                int[] boxBounds = getBoxBounds(percentX, percentY, radiusPercent, mat.width(), mat.height());
                int centerX = boxBounds[0];
                int centerY = boxBounds[1];
                int radius = boxBounds[2];

                Mat submat = mat.submat(centerY - radius, centerY + radius, centerX - radius, centerX + radius);

                Imgproc.cvtColor(submat, submat, Imgproc.COLOR_BGR2GRAY);

                // this mean2 is the brightness metric
                Scalar mean2 = Core.mean(submat);

                Core.MinMaxLocResult minMax =  Core.minMaxLoc(submat);
                double mContrast = (minMax.maxVal - minMax.minVal) / (minMax.maxVal + minMax.minVal);

                metrics.setContrast(mContrast);

                metrics.setBrightness((mean2.val[0] / 255.0) * 100);
                calcLaplaceVarAndStd(submat,metrics);

            }catch(Exception af){
                System.out.println(af.getLocalizedMessage());
            }
    }

    //
    private static void calcLaplaceVarAndStd(Mat submat, Metrics metrics){
        // Adding Blur to reduce noise
        // dest, src, kernel size,
        //Imgproc.GaussianBlur(submat, submat, new Size(3,3), 0);
        Imgproc.medianBlur(submat, submat, 3);
        Mat laplaceMat = new Mat();

        // submat is the source to be filtered by laplace, laplaceMat is the destination
        // depth represents the number of colors in the image: RGB so 3
        // can also include kernel for Sobal filter https://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/laplace_operator/laplace_operator.html
        Imgproc.Laplacian(submat, laplaceMat, 3);

        // These are essentially 1x1 Mats that will contain their respective information
        MatOfDouble mean = new MatOfDouble();
        MatOfDouble standardDev = new MatOfDouble();

        // Given a Mat, this calculates the mean? and Standard Deviation and sets the mean and standardDev variables
        // i'm not sure what this mean as at it can be negative between -1 and 1
        Core.meanStdDev(laplaceMat, mean, standardDev);

        // Laplace
        double laplaceBasedEdgeStrengthMetric = Math.pow(standardDev.get(0, 0)[0], 2);

        metrics.setStandardDeviation(standardDev.get(0, 0)[0]);
        metrics.setEdgeStrength(laplaceBasedEdgeStrengthMetric);

    }

    private  static int[] getBoxBounds(double xPercent, double yPercent, double radiusPercent, int width, int height){

        int[] boxBounds = new int[3];
        // Radius in pixels: The value passed is a percentage with respect to width
        double radius = width * radiusPercent;
        boxBounds[0] = (int) Math.round(xPercent * width);
        boxBounds[1] = (int) Math.round(yPercent * height);
        boxBounds[2] = (int) Math.round(radius);

        return boxBounds;
    }
}
