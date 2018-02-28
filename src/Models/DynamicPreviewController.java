package Models;


import Processing.Mat2Image;
import Helpers.MetricsCalculator;
import Processing.VideoCap;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.opencv.core.Mat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by AaronR on 2/25/18.
 * for ?
 */

public class DynamicPreviewController extends AbstractViewController {

    private ScheduledExecutorService timer;
    VideoCap cap;

    public DynamicPreviewController() {
        super("FocusVision");

        cap = VideoCap.getInstance();
        startCameraInit();

    }

    // capture image
    public void captureImage(){
        System.out.println("Should Capture Image and Open New Window");
    }
    // recapture Image

    public void reCaptureImage(){
        System.out.println("Should Replace Image in last Opened Window");
    }

    private void startCameraInit(){
        // This is on a delay because on initialization
        // the pane doesn't have a size
        this.timer = Executors.newSingleThreadScheduledExecutor();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                        timer.scheduleAtFixedRate(getFrameUpdater(metrics), 0, 33, TimeUnit.MILLISECONDS);
                    }
                },
                1000
        );
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
            this.imageView.setImage(image);
        };
    }

    public void captureImagePressed(){
        System.out.println("RequestToCaptureImage");

        Stage newWindow = WindowFactory.createStaticWindow(this);
        newWindow.show();



    }







}
