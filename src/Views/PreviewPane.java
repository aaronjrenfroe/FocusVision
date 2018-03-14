package Views;

import Helpers.GlobalSettings;
import Models.AbstractViewController;
import Models.Metrics;
import Processing.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by AaronR on 1/22/18.
 * for ?
 */

public class PreviewPane extends AnchorPane {

    // Consists of Image Preview, and BoxDrawing

    AbstractViewController controller;

    PreviewController  previewController;
    ImageView preview;
    BorderPane pane1;
    Rectangle square;


    public PreviewPane(int width, AbstractViewController controller) {
        super();
        this.controller = controller;

        commonInit(width);
        controller.setImageView(preview);

    }

    private void commonInit(int width){

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
        square.setStroke(Color.RED);
        getChildren().add(square);
        square.setVisible(false);
        square.setMouseTransparent(true);



        preview.setOnMouseClicked(e -> {

            if(e.getClickCount() == 2){
                square.setVisible(false);
                e.consume();
            }else {
                requestToMoveBox(e.getX(), e.getY());
            }

        });

        preview.setOnMouseDragged(e -> {
            requestToMoveBox( e.getX(),  e.getY());
        });
        setLayout();
    }

    private void setLayout(){
        
        HBox hbox = new HBox();
        hbox.setSpacing(10);

        hbox.setStyle("-fx-background-color: #FFFFFF;");

        SimpleStringProperty[] metricProperties = controller.getMetrics().getProperties();
        // TODO: REFACOTOR SO we LIMIT the NUMBER OF ITEMS IN VBOX


        for (int i = 0; i < metricProperties.length; i+=2) {

            VBox vbox = new VBox();
            vbox.setStyle("-fx-background-color: #FFFFFF;");

            // Top Label
            Label metricLabel = new Label();
            metricLabel.textProperty().bind(metricProperties[i]);
            vbox.getChildren().add(metricLabel);

            // Bottom Label
            if(i + 1 <= metricProperties.length) {
                Label metricLabel2 = new Label();
                metricLabel2.textProperty().bind(metricProperties[i + 1]);
                vbox.getChildren().add(metricLabel2);
            }

            hbox.getChildren().add(vbox);
        }

        pane1.setBottom(hbox);

    }

    private void requestToMoveBox(double x, double y){
        double[] xAndYPosOfBox = controller.requestToMoveBox(x,y);
        updateBox(xAndYPosOfBox[0] ,xAndYPosOfBox[1]);
    }

    private void updateBox(double x, double y){
        System.out.println("Actual y " + y);
        square.setVisible(true);
        square.setX(x);
        square.setY(y);
    }



}
