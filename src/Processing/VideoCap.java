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
import static org.opencv.videoio.Videoio.*;
import java.awt.image.BufferedImage;
import java.util.SplittableRandom;


import org.opencv.core.Core;
import sun.jvm.hotspot.utilities.AssertionFailure;

import javax.imageio.ImageIO;



public class VideoCap {

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    }

    private VideoCapture cap;
    private Mat2Image mat2Img;
    private Size size;
    private double[] boxBounds;

    public VideoCap(Size size){
        this.size = size;
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
            getVarianceOfLaplacian(mat2Img.mat);
            // original code
            Mat mat = new Mat();
            Imgproc.resize(mat2Img.mat, mat, size);
            return mat2Img.getImage(mat);
        }
    }


    public void getVarianceOfLaplacian(Mat mat){
        if (boxBounds != null) {
            try {
                int centerx = (int) boxBounds[2];
                int centery = (int) boxBounds[3];
                int halfWidth = (int) boxBounds[0] / 2;
                int halfHeight = (int) boxBounds[1] / 2;

                Mat mat2 = mat.submat(centery - halfHeight, centery + halfHeight, centerx - halfWidth, centerx + halfWidth);
                Size s = new Size(boxBounds[0], boxBounds[1]);
                Imgproc.resize(mat2, mat2, s);
                Mat destination = new Mat();
                Mat matGray = new Mat();
                Imgproc.cvtColor(mat2, matGray, Imgproc.COLOR_BGR2GRAY);
                //  ddepth represents the number of colors in the image: RGB so 3
                Imgproc.Laplacian(mat2, destination, 3); // can also include kernal for sobal filter https://docs.opencv.org/2.4/doc/tutorials/imgproc/imgtrans/laplace_operator/laplace_operator.html
                MatOfDouble median = new MatOfDouble();
                MatOfDouble std = new MatOfDouble();
                Core.meanStdDev(destination, median, std);
                
                System.out.println(Math.pow(std.get(0, 0)[0], 2));
                //System.out.println("x: " + centerx+" y: " + centery +" w: " + boxBounds[0] +" y: " + boxBounds[1]);

            }catch(Exception af){
                System.out.println(af.getLocalizedMessage());

            }
        }

    }

    public void setBoxbounds(double w,double h,double x,double y){
        boxBounds = new double[]{w ,h ,x ,y};
    }


    public void setSize(int width, int height) {
        this.size = new Size(width, height);
    }

    public void saveImage(String fileName){
        cap.read(mat2Img.mat);

        Mat mat = mat2Img.mat;
        Imgcodecs.imwrite(fileName.replace(" ", "_")+".png", mat);
    }
}
