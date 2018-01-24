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
    double width ;
    private double[] boxBounds;

    public VideoCap(double width){
        this.width = width;
        System.out.println("Width of passed cap: "+width);

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

    }


    public BufferedImage getOneFrame() {
        cap.read(mat2Img.mat);
        if (mat2Img.mat.empty()){
            System.out.println("Null");
            return null;
        }else{
            Mat mat = new Mat();
            double nativeWidth = mat2Img.mat.width();

            double hightScale = this.width / nativeWidth;

            Imgproc.resize(mat2Img.mat, mat, new Size(width, hightScale * mat2Img.mat.height()));
            return mat2Img.getImage(mat);
        }
    }




    public void setSize(int width, int height) {
        this.width = width;

    }

    public void saveImage(String fileName){
        cap.read(mat2Img.mat);

        Mat mat = mat2Img.mat;
        Imgcodecs.imwrite(fileName.replace(" ", "_")+".png", mat);
    }
}


