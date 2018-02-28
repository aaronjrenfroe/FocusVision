package Models;
import Helpers.GlobalSettings;
import Helpers.MetricsCalculator;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

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
    double[] selectionInfo;
    Metrics metrics;

    public AbstractViewController(String windowName) {

        imageView = new ImageView();
        this.windowName = windowName;
        System.out.println(saveLocation);
        metrics = new Metrics();
        patientName = new SimpleStringProperty();
        saveLocation = new SimpleStringProperty();
        boxSize = new SimpleIntegerProperty();
        boxSize.set(50);
        saveLocation.set(getDefaultSaveLocation() + "/FocusVison/Images");

    }

    private String getDefaultSaveLocation(){
        return System.getProperty("user.home");
    }

    // change box size
    // on resize
    // other menu things
    // open help


    public String getPatientName() {
        return patientName.get();
    }

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
    }

    public void setImageView(ImageView imageView){
        this.imageView = imageView;
    }

    public void updateSelection(double xPercent, double yPercent, double radiusPercent){

        selectionInfo = new double[3];
        selectionInfo[0] = xPercent;//(int) Math.round(xPercent * nativeSize.width);
        selectionInfo[1] = yPercent; //(int) Math.round(yPercent * nativeSize.height);
        selectionInfo[2] = radiusPercent; //(int) Math.round(radius);
        System.out.println(selectionInfo[0] +" "+selectionInfo[1] + " " + selectionInfo[2]);

    }

    public double[] requestToMoveBox(double x, double y){

        System.out.println("x: " + x + " y: " + y);

        Bounds bounds = imageView.boundsInParentProperty().getValue();

        double xReq = bounds.getMinX() + x - GlobalSettings.HALF_SIDE;
        double yReq =  bounds.getMinY() + y - GlobalSettings.HALF_SIDE;
        double xPos = Math.min(xReq, GlobalSettings.PreviewAreaWidth - GlobalSettings.MENU_WIDTH - 2* GlobalSettings.HALF_SIDE - 1);

        xPos = Math.max(xPos, bounds.getMinX());

        double yPos = Math.min(yReq, bounds.getHeight() + bounds.getMinY() - 2* GlobalSettings.HALF_SIDE - 1);
        yPos = Math.max(yPos, bounds.getMinY());


        updateVidCapMetricBox(x,y);
        double[] xAndYPos = {xPos,yPos};
        return xAndYPos;

    }

    private void updateVidCapMetricBox(double centerX, double centerY){

        Bounds localBounds = imageView.boundsInLocalProperty().getValue();
        double radiusWithRespectToWidth = GlobalSettings.HALF_SIDE / localBounds.getWidth();

        //TODO:  check edge cases literally
        double percentX = centerX/localBounds.getWidth();
        double percentY = centerY/localBounds.getHeight();

        percentX = Math.min(percentX, 1-(radiusWithRespectToWidth));
        percentX = Math.max(percentX, radiusWithRespectToWidth);

        percentY = Math.min(percentY, 1-(GlobalSettings.HALF_SIDE / localBounds.getHeight()));
        percentY = Math.max(percentY, GlobalSettings.HALF_SIDE / localBounds.getHeight());

        System.out.println(percentX + " by " + percentY);
        updateSelection(percentX, percentY, radiusWithRespectToWidth);

//        if(!isLive) {
//            MetricsCalculator.getVarianceOfLaplacian(this.mat, percentX,percentY, radiusWithRespectToWidth , metrics);
//        }


    }

    public SimpleStringProperty getLaplaceProperty() {
        return metrics.getLaplaceProperty();
    }
    public SimpleStringProperty getMichelsonContrastProperty() {
        return metrics.getMichelsonContrastProperty();
    }


    public void zoomInPressed(){
        System.out.println("Button was clicked -- zooming In!!");
    }

    public void zoomOutPressed(){
        System.out.println("Button was clicked -- zooming Out!!");
    }




}