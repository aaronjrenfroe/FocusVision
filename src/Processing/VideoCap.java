package Processing;

/**
 * Created by AaronR on 10/12/17.
 * for ?
 */

import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;


import org.opencv.core.Core;



public class VideoCap {

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    }

    private VideoCapture cap;
    private Mat2Image mat2Img;
    private double width ;
    private int[] boxBounds;
    Size nativeSize;

    public VideoCap(double width){

        this.width = width;

        cap = new VideoCapture();

        cap.open(0);
        if (cap.isOpened()) {
            System.out.println("Camera Opened");
        }else{
            cap.open(0);
            System.out.println("Might be in use by another program or it hasn't been released yet :(");
        }

//        cap.set(CV_CAP_PROP_FRAME_WIDTH, 2592);
//        cap.set(CV_CAP_PROP_FRAME_HEIGHT, 1944);
        mat2Img = new Mat2Image();
        cap.read(mat2Img.mat);
        if(mat2Img.mat.empty()){
            System.out.println("Can't Open Camera");
        }
        nativeSize = mat2Img.mat.size();
        System.out.println("Size: " +nativeSize);

    }




    public BufferedImage getOneFrame() {
        cap.read(mat2Img.mat);
        if (mat2Img.mat.empty()){
            System.out.println("Null");
            return null;
        }else{
            // If we would like to display a lower resolution image
//            Mat mat = new Mat();
//            double nativeWidth = mat2Img.mat.width();
//
//            double heightScale = this.width / nativeWidth;
//
//            Imgproc.resize(mat2Img.mat, mat, new Size(width, heightScale * mat2Img.mat.height()));
            getVarianceOfLaplacian(mat2Img.mat);

            return mat2Img.getImage(mat2Img.mat);
        }
    }

    public void getVarianceOfLaplacian(Mat mat){

        if (boxBounds != null) {
            try {
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
                Metrics.get().setLaplace( Math.pow(std.get(0, 0)[0], 2));
                // Michaelson Contrast
                Core.MinMaxLocResult minMax =  Core.minMaxLoc(matGray);
                Metrics.get().setMichelsonContrast((minMax.maxVal - minMax.minVal) / (minMax.maxVal + minMax.minVal));


            }catch(Exception af){
                System.out.println(af.getLocalizedMessage());

            }
        }
    }

    //
    public void updateBounds(double xPercent, double yPercent, double radiusPercent){
        boxBounds = new int[3];
        // Radius in pixels: The value passed is a percentage with respect to width
        double radius = nativeSize.width * radiusPercent;

        boxBounds[0] = (int) Math.round(xPercent * nativeSize.width);
        boxBounds[1] = (int) Math.round(yPercent * nativeSize.height);
        boxBounds[2] = (int) Math.round(radius);

    }



    public void saveImage(String fileName){
        cap.read(mat2Img.mat);

        Mat mat = mat2Img.mat;
        Imgcodecs.imwrite(fileName.replace(" ", "_")+".png", mat);
    }
}


