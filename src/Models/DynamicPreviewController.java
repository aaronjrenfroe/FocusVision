package Models;


import Processing.Mat2Image;
import Helpers.MetricsCalculator;
import Processing.VideoCap;

import Views.BasicLayout;
import Views.PreviewPane;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.opencv.core.Mat;


import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by AaronR on 2/25/18.
 * for ?
 */

public class DynamicPreviewController extends AbstractViewController {

    private Timer timer;
    VideoCap cap;
    SimpleBooleanProperty recaptureButtonDisabledProperty;
    private SimpleStringProperty captureButtonText;

    boolean hasStartedCapturing = false;

    public String getCaptureButtonText() {
        return captureButtonText.get();
    }

    public SimpleStringProperty captureButtonTextProperty() {
        return captureButtonText;
    }



    public DynamicPreviewController(Stage stage) {
        super("FocusVision", stage);
        captureButtonText = new SimpleStringProperty("Open Camera");
        ViewManager.getManager().setPrimaryController(this);

        recaptureButtonDisabledProperty = new SimpleBooleanProperty();
        recaptureButtonDisabledProperty.set(true);
        cap = VideoCap.getInstance();

        //startCameraInit();


    }

    private void startCameraInit(){
        // This is on a delay because on initialization
        // the pane doesn't have a size
        this.timer = new Timer();

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // times are in milliseconds
                        timer.scheduleAtFixedRate(getFrameUpdater(metrics),0,33);
                    }
                },
                1000
        );
    }


    private TimerTask getFrameUpdater(Metrics metrics){
        Mat2Image mat2Img = new Mat2Image();
        return new TimerTask(){
            @Override
            public void run() {
                Mat mat = cap.getOneFrame();
                if(selectionInfo != null) {
                    MetricsCalculator.getVarianceOfLaplacian(mat, selectionInfo[0], selectionInfo[1], selectionInfo[2], metrics);
                }
                Image image = SwingFXUtils.toFXImage(mat2Img.getImage(cap.getOneFrame()), null);
                imageView.setImage(image);
            }
        };
    }

    // capture image
    public void captureImagePressed(){
        if(hasStartedCapturing) {

            System.out.println("RequestToCaptureImage");
            Stage newWindow = WindowFactory.createStaticWindow(this, VideoCap.getInstance().getOneFrame(), "Capture " + (ViewManager.getManager().getTotalViewsCreated() + 1), patientName.get(), selectionInfo);
            newWindow.show();
            recaptureButtonDisabledProperty.setValue(false);
        }else{

            startCameraInit();
            hasStartedCapturing = true;
            captureButtonText.set("Capture");
        }

    }

    // recapture Image
    public void reCaptureImagePressed(){
        System.out.println("Should Replace Image in last Opened Window");
        StaticViewController staticController = ViewManager.getManager().getLast();
        if(staticController != null){
            staticController.setMat(VideoCap.getInstance().getOneFrame());
        }
    }

    public void changeCameraPressed(){
        timer.cancel();
        timer.purge();
        VideoCap.getInstance().nextCamera();
        timer = new Timer();
        timer.scheduleAtFixedRate(getFrameUpdater(metrics), 0, 33);
    }

    public void recountCameraPressed(){
        VideoCap.getInstance().countCameras();
    }

    public void lastCreatedCaptureDeleted(){
        recaptureButtonDisabledProperty.setValue(true);
    }

    public SimpleBooleanProperty getRecaptureButtonDisabledProperty() {
        return recaptureButtonDisabledProperty;
    }

    public void killTimer(){
        if(this.timer != null) {
            this.timer.cancel();
            this.timer.purge();
        }
        System.out.println("Controller Finalised");

    }

    public void updateWidth(double width){
        VideoCap.getInstance().setWidth(width);
    }


}
