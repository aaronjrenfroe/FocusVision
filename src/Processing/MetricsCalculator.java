package Processing;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
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

                Mat mat2 = mat.submat(centerY - radius, centerY + radius, centerX - radius, centerX + radius);

                Mat destination = new Mat();
                Mat matGray = new Mat();
                Imgproc.cvtColor(mat2, matGray, Imgproc.COLOR_BGR2GRAY);
                //  depth represents the number of colors in the image: RGB so 3
                Imgproc.Laplacian(mat2, destination, 3); // can also include kernel for Sobal filter https://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/laplace_operator/laplace_operator.html
                MatOfDouble median = new MatOfDouble();
                MatOfDouble std = new MatOfDouble();
                Core.meanStdDev(destination, median, std);
                // Laplace
                Core.MinMaxLocResult minMax =  Core.minMaxLoc(matGray);
                double focusMetric = Math.pow(std.get(0, 0)[0], 2);
                double mcontrast = (minMax.maxVal - minMax.minVal) / (minMax.maxVal + minMax.minVal);

                metrics.setMetrics(focusMetric,mcontrast);

            }catch(Exception af){
                System.out.println(af.getLocalizedMessage());
            }
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
