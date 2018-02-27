package Models;

import Processing.VideoCap;
import javafx.embed.swing.SwingFXUtils;
import org.opencv.core.Mat;

import javax.swing.text.html.ImageView;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by AaronR on 2/25/18.
 * for ?
 */

public class StaticViewModel extends ViewModel {
    // pass it a Mat or pass it a png

    Mat mat;

    // Event Handlers

    // load from file
    public StaticViewModel(Mat image){
        super("");
        init(image);
    }


    private void init(Mat image){
        mat = image;

        //this.imageView.setImage(SwingFXUtils.toFXImage(image, null));
    }



    private Mat bufferedImageToMat(BufferedImage bImage){
        Mat mat = new Mat();
        mat.put(bImage.getWidth(), bImage.getHeight(), ((DataBufferByte) bImage.getRaster().getDataBuffer()).getData());
        return mat;

    }


    // open Image
    public void openImageRunable(String loc){

        System.out.println("Should Open Image");


    }

    // save Image
    // open Image
    public void saveImageRunnable(String loc, String fileName){

        System.out.println("Should Save Image to this locaiton");
        System.out.println(loc);
        System.out.println(fileName);


    }

    // Top Menu Functions



}

