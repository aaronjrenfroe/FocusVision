import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import Processing.PreviewController;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import sun.plugin.javascript.navig.Anchor;

import java.util.Stack;

/**
 * Created by AaronR on 1/22/18.
 * for ?
 */
public class PreviewPane extends AnchorPane {

    // Consists of Image Preview, and BoxDrawing
    private final double HALF_SIDE = 50;

    PreviewController  previewController;
    ImageView preview;
    BorderPane pane1;

    Rectangle square;


    public PreviewPane(int width , int height) {
        super();
        preview = new ImageView();
        preview.setPreserveRatio(true);

        setStyle("-fx-background-color: #606360;");
        previewController = new PreviewController();
        pane1= new BorderPane();
        pane1.setMaxWidth(width);
        pane1.setMinWidth(width);
        pane1.setCenter(preview);


        AnchorPane.setRightAnchor(pane1,0.0);
        AnchorPane.setLeftAnchor(pane1,0.0);
        AnchorPane.setTopAnchor(pane1,0.0);
        AnchorPane.setBottomAnchor(pane1,0.0);

        getChildren().add(pane1);

        square = new Rectangle(HALF_SIDE*2,HALF_SIDE*2);
        square.setFill(Color.color(0,0,0,0));
        square.setStroke(Color.BLACK);
        getChildren().add(square);
        square.setVisible(false);
        square.setOnMouseClicked(e -> {
            square.setVisible(false);
        });



        setOnMouseClicked(e -> {
            updateBox( e.getX(),  e.getY());

        });

        setOnMouseDragged(e -> {
            updateBox( e.getX(),  e.getY());

        });
        startCameraInit();

    }

    private void updateBox(double x, double y){
        square.setVisible(true);
        square.setX(x - (HALF_SIDE));
        square.setY(y  - (HALF_SIDE));
    }

    private void startCameraInit(){
        // This is on a delay becasue on initialization
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



}
