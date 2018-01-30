package Processing;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import org.opencv.core.Size;

import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by AaronR on 1/22/18.
 * for ?
 */
public class PreviewController {

    private ScheduledExecutorService timer;
    VideoCap cap;
    ImageView preview;

    public void startCamera(double width, double height, ImageView preview) {
        this.preview = preview;

        System.out.println("Start Camera Pressed");

        cap = VideoCap.getInstance();

        this.timer = Executors.newSingleThreadScheduledExecutor();
        this.timer.scheduleAtFixedRate(getFrameUpdater(), 0, 33, TimeUnit.MILLISECONDS);

    }

    private Runnable getFrameUpdater(){
        return () -> {

            Image image = SwingFXUtils.toFXImage(cap.getOneFrame(), null);

            this.preview.setImage(image);
        };
    }

    public void updateSelection(double xPercent, double yPercent, double radiusPercent){
        cap.updateBounds(xPercent, yPercent, radiusPercent);
    }

    public BufferedImage getNativeImage(){
        return cap.getNativeImage();
    }

}

