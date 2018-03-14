package Helpers;

import Processing.Mat2Image;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by AaronR on 2/27/18.
 * for ?
 */
public class ImageHelper {

    public static void saveImage(String fileName, Mat mat)
    {
        //save image function

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
        Imgcodecs.imwrite(fileName, mat);

    }

    // open Image
    public static Mat openImage(File file){
        Mat mat = Imgcodecs.imread(file.getAbsolutePath());
        return mat;
    }

    public static BufferedImage openImage(String location){

        return null;
    }

    public static void getImageMetadata(){
        System.out.println("Should get image Metadata");
    }

    public static BufferedImage getBufferedImageFromMat(Mat mat){
        Mat2Image mat2Img = new Mat2Image();
        return mat2Img.getImage(mat);
    }

    public Mat bufferedImageToMat(BufferedImage bImage){
        Mat mat = new Mat();
        mat.put(bImage.getWidth(), bImage.getHeight(), ((DataBufferByte) bImage.getRaster().getDataBuffer()).getData());
        return mat;

    }

    public static double[] parseSelectionFromName(String filename){
        double selectInfo[] = new double[3];  // create array for x y and radius
        filename = filename.substring(0, filename.length()-4);  // take out .png from file name

        String temp[];

        temp = filename.split("_");  // give temp array values for split string

        selectInfo[2] = Double.parseDouble(temp[temp.length-1]);  // radius
        selectInfo[1] = Double.parseDouble(temp[temp.length-2]);  // y
        selectInfo[0] = Double.parseDouble(temp[temp.length-3]);  // x
        
        return selectInfo;
    }
}
