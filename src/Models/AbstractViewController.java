package Models;
import Helpers.GlobalSettings;
import Helpers.ImageHelper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.opencv.core.Mat;

import java.io.File;

/**
 * Created by AaronR on 2/25/18.
 * for ?
 */
public abstract class AbstractViewController implements MatProvider{

    ImageView imageView;
    String windowName;
    SimpleStringProperty patientName;
    SimpleStringProperty saveLocation;
    SimpleIntegerProperty boxSize;
    double[] currentBoxPosition;
    double[] selectionInfo;

    Metrics metrics;
    Stage stage;

    public AbstractViewController(String windowName, Stage stage) {
        this.stage = stage;
        imageView = new ImageView();
        this.windowName = windowName;
        System.out.println(saveLocation);

        if(this.getClass() == DynamicPreviewController.class) {
            metrics = new Metrics(true);
        }else{
            metrics = new Metrics(false);
        }

        patientName = new SimpleStringProperty();
        saveLocation = new SimpleStringProperty();
        boxSize = new SimpleIntegerProperty();
        boxSize.set(GlobalSettings.INITIAL_BOX_SIZE);
        patientName.set("untitled");
        saveLocation.set(getDefaultSaveLocation() + "/Desktop/FocusVision/Images/");
        File file = new File(saveLocation.getValue());
        file.mkdirs();
        currentBoxPosition = new double[2];

    }

    private String getDefaultSaveLocation(){
        return System.getProperty("user.home");
    }

    // open Image
    public void openImagePressed(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        String filePath = file.getName();
        System.out.println(filePath);
        String extension = "";

        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i+1);
        }

        if (extension.contains("png") || extension.contains("tiff") || extension.contains("tif") || extension.contains("jpg") || extension.contains("jpeg")){

            double selectInfo[] = ImageHelper.parseSelectionFromName(file.getName());

            Mat mat = ImageHelper.openImage(file);
            Stage stage = WindowFactory.createStaticWindow(this, mat, file.getAbsolutePath(), file.getName(), selectInfo);
            stage.show();
        }

    }

    public String getPatientName() {
        return patientName.get();
    }

    public SimpleStringProperty getPatientNameProperty() {return patientName;}

    public SimpleStringProperty patientNameProperty() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName.set(patientName);
    }

    public String getSaveLocation() {
        return saveLocation.get();
    }

    public SimpleStringProperty saveLocationProperty() {
        return saveLocation;
    }

    public void setSaveLocation(String saveLocation) {
        this.saveLocation.set(saveLocation);
    }

    public int getBoxSize() {
        return boxSize.get();
    }

    public SimpleIntegerProperty boxSizeProperty() {
        return boxSize;
    }

    public void setBoxSize(int newSize) {
        if (newSize > 10 && newSize < 120) {
            boxSize.set(newSize);
        }
        requestToMoveBox(currentBoxPosition[0], currentBoxPosition[1]);
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

    public double[] requestToMoveBox(double x, double y){
        // x and y coordinates of location clicked within image
        System.out.println("x: " + x + " y: " + y);
        currentBoxPosition[0] = x;
        currentBoxPosition[1] = y;

        Bounds bounds = imageView.boundsInParentProperty().getValue();

        double xReq = bounds.getMinX() + x - halfBoxSize();
        double yReq =  bounds.getMinY() + y - halfBoxSize();


        double xPos = Math.min(xReq, GlobalSettings.PreviewAreaWidth - GlobalSettings.MENU_WIDTH - (boxSize.get()) - 1);
        xPos = Math.max(xPos, bounds.getMinX());


        double yPos = Math.min(yReq, bounds.getHeight() + bounds.getMinY() - (boxSize.get()) - 1);
        yPos = Math.max(yPos, bounds.getMinY());

        updateVidCapMetricBox(x,y);
        double[] xAndYPos = {xPos,yPos};
        return xAndYPos;

    }

    protected void updateVidCapMetricBox(double centerX, double centerY){

        Bounds localBounds = imageView.boundsInLocalProperty().getValue();
        double radiusWithRespectToWidth = halfBoxSize() / localBounds.getWidth();

        //TODO:  check edge cases literally
        double percentX = centerX/localBounds.getWidth();
        double percentY = centerY/localBounds.getHeight();

        percentX = Math.min(percentX, 1-(radiusWithRespectToWidth));
        percentX = Math.max(percentX, radiusWithRespectToWidth);

        percentY = Math.min(percentY, 1-(halfBoxSize() / localBounds.getHeight()));
        percentY = Math.max(percentY, boxSize.get() / localBounds.getHeight());

        System.out.println(percentX + " by " + percentY);
        updateSelection(percentX, percentY, radiusWithRespectToWidth);


    }

    public void updateSelection(double xPercent, double yPercent, double radiusPercent){

        selectionInfo = new double[3];
        selectionInfo[0] = xPercent;//(int) Math.round(xPercent * nativeSize.width);
        selectionInfo[1] = yPercent; //(int) Math.round(yPercent * nativeSize.height);
        selectionInfo[2] = radiusPercent; //(int) Math.round(radius);
        System.out.println(selectionInfo[0] +" "+selectionInfo[1] + " " + selectionInfo[2]);

    }


    public void zoomInPressed(){
        System.out.println("Button was clicked -- zooming In!!");
    }

    public void zoomOutPressed(){
        System.out.println("Button was clicked -- zooming Out!!");
    }

    protected void notifyBoxMoved(){
        // this gits called when box gets moved and this method gets implemented in StaticViewController
        // It is Empty here on purpose.
    }

    private double halfBoxSize(){
        return boxSize.get()/2.0;
    }

    public void translatePressed(int direction)
    {
        switch (direction)
        {
            case 1:
                System.out.println("up");
                break;
            case 2:
                System.out.println("right");
                break;
            case 3:
                System.out.println("down");
                break;
            case 4:
                System.out.println("left");
                break;
            default:
                System.out.println("default");
                break;
        }
    }

    public Metrics getMetrics() {
        return metrics;
    }

}
