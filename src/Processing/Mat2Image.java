package Processing;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javafx.scene.image.Image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Mat2Image {

    Mat mat = new Mat();
    BufferedImage img;
    byte[] dat;

    public Mat2Image() {

    }

    public Mat2Image(Mat mat) {
        getSpace(mat);
    }

    public void getSpace(Mat mat) {
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2BGR);
        this.mat = mat;
        int w = mat.cols(), h = mat.rows();
        // These only run once to init data
        if (dat == null || dat.length != w * h * 3) {
            dat = new byte[w * h * 3];
        }
        if (img == null || img.getWidth() != w || img.getHeight() != h || img.getType() != BufferedImage.TYPE_3BYTE_BGR) {
            img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        }
    }

    public BufferedImage getImage(Mat mat){
        getSpace(mat);
        mat.get(0, 0, dat);
        img.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), dat);
        return img;
    }

    public static Image getImage2(Mat mat){
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".bmp",mat, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }


    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}