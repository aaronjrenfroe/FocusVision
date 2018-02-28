package Models;

import Helpers.ImageHelper;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by AaronR on 2/25/18.
 * for ?
 */

public class StaticViewController extends AbstractViewController {
    // pass it a Mat or pass it a png

    Mat mat;

    // Event Handlers

    // load from file
    public StaticViewController(Mat image){
        super("");

        init(image);
    }

    private void init(Mat image){
        mat = image;

    }

    @Override
    public void setImageView(ImageView imageView){
        this.imageView = imageView;
        this.imageView.setImage(SwingFXUtils.toFXImage(ImageHelper.getBufferedImageFromMat(this.mat), null));
    }



    // open Image
    public void openImage(String loc){

        System.out.println("Should Open Image");

    }

    // save Image
    // open Image
    public void saveImagePressed(){

        System.out.println("Should Save Image to this location");
        System.out.println(this.getSaveLocation()+"/"+this.getPatientName());

        saveImage(this.getSaveLocation()+"/"+this.getPatientName());
    }

    private void saveImage(String fileName)
    {
        //save image function
        Imgproc.cvtColor(this.mat, this.mat, Imgproc.COLOR_RGB2BGR);
        Imgcodecs.imwrite(fileName.replace(" ", "_")+".png", this.mat);
        this.imageView.setImage(SwingFXUtils.toFXImage(ImageHelper.getBufferedImageFromMat(this.mat), null));

    }

    // Top Menu Functions



}

