package Helpers;

import Processing.Mat2Image;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Map;

/**
 * Created by AaronR on 2/27/18.
 * for ?
 */
public class ImageHelper {

    // save Image
    public static void saveImage(BufferedImage image, String location, String name, Map metaData){

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
}
