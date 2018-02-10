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

    private static VideoCap instance;

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    }

    private VideoCapture cap;
    private Mat2Image mat2Img;
    private double width;
    private int[] boxBounds;
    Size nativeSize;
    int cameraCount;
    int currentCamera;

    private VideoCap(){

        cap = new VideoCapture();
        countCameras();
        currentCamera = -1;
        while(!cap.isOpened()){
            nextCamera();
        }

        mat2Img = new Mat2Image();
        cap.read(mat2Img.mat);
        if(mat2Img.mat.empty()){
            System.out.println("Can't Open Camera");
        }
        nativeSize = mat2Img.mat.size();
        System.out.println("Size: " +nativeSize);

    }

    public void countCameras(){

        cameraCount = -1;
        VideoCapture tempCap = new VideoCapture();
        do {
            cameraCount++;

            tempCap.open(cameraCount);

        }while (tempCap.isOpened());
    }

    public void nextCamera(){
        if(cameraCount > 1 || currentCamera == -1) {
            currentCamera = ++currentCamera % cameraCount;
            cap.open(currentCamera);
        }
    }

    public static VideoCap getInstance(){
        if (instance == null){
            instance = new VideoCap();
        }
        return instance;
    }

    protected BufferedImage getOneFrame() {
        BufferedImage nativeImage =  getNativeImage();
        System.gc();
        cap.read(mat2Img.mat);
        if (mat2Img.mat.empty()){
            System.out.println("Null");
            return null;
        }else{
//            If we would like to display a lower resolution image
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

    protected void getVarianceOfLaplacian(Mat mat){

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
                Core.MinMaxLocResult minMax =  Core.minMaxLoc(matGray);
                double focusMetric = Math.pow(std.get(0, 0)[0], 2);
                double mcontrast = (minMax.maxVal - minMax.minVal) / (minMax.maxVal + minMax.minVal);

                Metrics.get().setMetrics(focusMetric,mcontrast);



            }catch(Exception af){
                System.out.println(af.getLocalizedMessage());

            }
        }
    }

    //
    protected void updateBounds(double xPercent, double yPercent, double radiusPercent){
        boxBounds = new int[3];
        // Radius in pixels: The value passed is a percentage with respect to width
        double radius = nativeSize.width * radiusPercent;

        boxBounds[0] = (int) Math.round(xPercent * nativeSize.width);
        boxBounds[1] = (int) Math.round(yPercent * nativeSize.height);
        boxBounds[2] = (int) Math.round(radius);

    }

    public BufferedImage getNativeImage(){
        cap.read(mat2Img.mat);
        if (mat2Img.mat.empty()){
            System.out.println("Null");
            return null;
        }else {

            return mat2Img.getImage(mat2Img.mat);
        }
    }

    /*
    protected void saveImage(String fileName){
        cap.read(mat2Img.mat);
        Mat mat = mat2Img.mat;
        Imgcodecs.imwrite(fileName.replace(" ", "_")+".png", mat);
    }
    */
}


