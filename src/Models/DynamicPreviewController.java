package Models;


import Processing.Mat2Image;
import Helpers.MetricsCalculator;
import Processing.VideoCap;

import Views.BasicLayout;
import Views.PreviewPane;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
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

    private int zoomLevel = 0;

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
        
    }

    private void startCameraInit(){
        cap = VideoCap.getInstance();
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

        return new TimerTask(){
            @Override
            public void run() {
                Mat mat = cap.getOneFrame();
                imageView.setImage(Mat2Image.getImage2(mat));

                if(selectionInfo != null) {
                    MetricsCalculator.getVarianceOfLaplacian(mat, selectionInfo[0], selectionInfo[1], selectionInfo[2], metrics);
                }
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

        StaticViewController staticController = ViewManager.getManager().getLast();
        if(staticController != null){
            staticController.setMat(VideoCap.getInstance().getOneFrame());
        }
    }

    public void changeCameraPressed(){
        // Kills the thread that refreshes the image shown.
        killTimer();

        // There was an issue getting the VidCap Instance
        // and resetting the timer right away and this fixed it
        try{
            Thread.sleep(200);
            VideoCap.getInstance().nextCamera();
            timer = new Timer();
            timer.scheduleAtFixedRate(getFrameUpdater(metrics), 0, 33);
        }catch (InterruptedException e){ // Thrown by Thread.sleep
            System.out.println("Could Not Sleep:" + e.getLocalizedMessage());
        }

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
    @Override
    public void zoomPressed(int value){
        zoomLevel +=(value * 10);

        if (zoomLevel < 0){
            zoomLevel = 0;
        }

        if(value > 0){
            System.out.print("Zoom in: ");
        }
        else if(value < 0){
            System.out.print("Zoom out: ");
        }
        System.out.println(zoomLevel);
    }

    @Override
    void shift(Point2D delta){
        System.out.println("");
    }

    public void updateWidth(double width){
        VideoCap.getInstance().setWidth(width);
    }


}
