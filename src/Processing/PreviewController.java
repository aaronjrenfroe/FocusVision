package Processing;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.core.Size;

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



    private double[] selectionInfo;


    public void startCamera(double width, double height, ImageView preview, Metrics metrics) {
        this.preview = preview;
        System.out.println("Start Camera Pressed");
        cap = VideoCap.getInstance();
        this.timer = Executors.newSingleThreadScheduledExecutor();
        this.timer.scheduleAtFixedRate(getFrameUpdater(metrics), 0, 33, TimeUnit.MILLISECONDS);

    }

    private Runnable getFrameUpdater(Metrics metrics){
        Mat2Image mat2Img = new Mat2Image();

        return () -> {
            //mat2Img.getImage(cap.getOneFrame())
            Mat mat = cap.getOneFrame();
            if(selectionInfo != null) {
                MetricsCalculator.getVarianceOfLaplacian(mat, selectionInfo[0], selectionInfo[1], selectionInfo[2], metrics);
            }
            Image image = SwingFXUtils.toFXImage(mat2Img.getImage(cap.getOneFrame()), null);
            this.preview.setImage(image);
        };
    }

    public void updateSelection(double xPercent, double yPercent, double radiusPercent){

        selectionInfo = new double[3];
        selectionInfo[0] = xPercent;//(int) Math.round(xPercent * nativeSize.width);
        selectionInfo[1] = yPercent; //(int) Math.round(yPercent * nativeSize.height);
        selectionInfo[2] = radiusPercent; //(int) Math.round(radius);
        System.out.println(selectionInfo[0] +" "+selectionInfo[1] + " " + selectionInfo[2]);

    }

}

