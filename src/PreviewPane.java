import Processing.Metrics;
import com.sun.javafx.binding.StringFormatter;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import Processing.PreviewController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by AaronR on 1/22/18.
 * for ?
 */
public class PreviewPane extends AnchorPane {

    // Consists of Image Preview, and BoxDrawing


    PreviewController  previewController;
    ImageView preview;
    BorderPane pane1;
    Rectangle square;

    public PreviewPane(int width , int height) {
        super();
        // Allows clicks to pass through
        this.setPickOnBounds(true);


        preview = new ImageView();
        preview.setStyle("-fx-background-color: #336699;");
        preview.setPreserveRatio(true);

        setStyle("-fx-background-color: #606360;");
        previewController = new PreviewController();
        pane1= new BorderPane();
        pane1.setMaxWidth(width);
        pane1.setMinWidth(width);
        preview.fitWidthProperty().bind(widthProperty());
        //preview.fitHeightProperty().bind(heightProperty());

        pane1.setCenter(preview);
        widthProperty().addListener( l -> {
            System.out.println(l);
        });

        AnchorPane.setRightAnchor(pane1,0.0);
        AnchorPane.setLeftAnchor(pane1,0.0);
        AnchorPane.setTopAnchor(pane1,0.0);
        AnchorPane.setBottomAnchor(pane1,0.0);

        getChildren().add(pane1);

        square = new Rectangle(GlobalSettings.HALF_SIDE*2, GlobalSettings.HALF_SIDE*2);
        square.setFill(Color.color(0,0,0,0));
        square.setStroke(Color.BLACK);
        getChildren().add(square);
        square.setVisible(false);
        square.setMouseTransparent(true);


        preview.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2){
                System.out.println("Dumb");
                square.setVisible(false);
                e.consume();
            }else {
                requestToMoveBox(e.getX(), e.getY());
            }

        });
        preview.setOnMouseDragged(e -> {

            requestToMoveBox( e.getX(),  e.getY());
        });

        startCameraInit();
        setLayout();
    }

    private void setLayout(){
        Label focus = new Label();
        System.out.println(Metrics.get().getLaplaceProperty());
        focus.textProperty().bind(Metrics.get().getLaplaceProperty());

        this.getChildren().add(focus);
        AnchorPane.setBottomAnchor( focus, 10.0);
        AnchorPane.setLeftAnchor(focus, 10.0);

        Label contrast = new Label();

        contrast.textProperty().bind(Metrics.get().getMichelsonContrastProperty());


        AnchorPane.setBottomAnchor(contrast, 10.0 + 20);
        AnchorPane.setLeftAnchor(contrast, 10.0);

        this.getChildren().add(contrast);

    }


    private void requestToMoveBox(double x, double y){
        System.out.println();

        System.out.println("x: " + x + " y: " + y);

        Bounds bounds = preview.boundsInParentProperty().getValue();

        double xReq = bounds.getMinX() + x - GlobalSettings.HALF_SIDE;
        double yReq =  bounds.getMinY() + y - GlobalSettings.HALF_SIDE;
        double xPos = Math.min(xReq, GlobalSettings.PreviewAreaWidth - GlobalSettings.MENU_WIDTH - 2* GlobalSettings.HALF_SIDE - 1);

        xPos = Math.max(xPos, bounds.getMinX());

        double yPos = Math.min(yReq, bounds.getHeight() + bounds.getMinY() - 2* GlobalSettings.HALF_SIDE - 1);
        yPos = Math.max(yPos, bounds.getMinY());

        updateBox(xPos ,yPos);
        updateVidCapMetricBox(x,y);
    }

    private void updateBox(double x, double y){
        System.out.println("Actual y " + y);
        square.setVisible(true);
        square.setX(x);
        square.setY(y);

    }

    private void startCameraInit(){
        // This is on a delay because on initialization
        // the pane doesn't have a size

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                        System.out.println( getWidth() +" x "+ getHeight()  );
                        previewController.startCamera(getWidth(), getHeight(), preview);
                    }
                },
                1000
        );
    }

    private void updateVidCapMetricBox(double centerX, double centerY){

        Bounds localBounds = preview.boundsInLocalProperty().getValue();
        double radiusWithRespectToWidth = GlobalSettings.HALF_SIDE / localBounds.getWidth();

        //TODO:  check edge cases literally
        double percentX = centerX/localBounds.getWidth();
        double percentY = centerY/localBounds.getHeight();

        percentX = Math.min(percentX, 1-(radiusWithRespectToWidth));
        percentX = Math.max(percentX, radiusWithRespectToWidth);

        percentY = Math.min(percentY, 1-(GlobalSettings.HALF_SIDE / localBounds.getHeight()));
        percentY = Math.max(percentY, GlobalSettings.HALF_SIDE / localBounds.getHeight());

        System.out.println(percentX + " by " + percentY);

        previewController.updateSelection(percentX, percentY, radiusWithRespectToWidth);

    }

}
