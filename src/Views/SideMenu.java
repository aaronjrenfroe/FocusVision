package Views;

import Helpers.GlobalSettings;
import Models.AbstractViewController;
import Models.DynamicPreviewController;
import Models.StaticViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Created by AaronR on 1/22/18.
 * for Hooter Whater
 */
public class SideMenu extends BorderPane {



    Label nameLable;
    TextField textFieldName;
    Label idLable;
    TextField textFieldLocation;
    AbstractViewController controller;


    public SideMenu(int width, boolean isLiveWindow, AbstractViewController controller) {
        super();

        this.controller = controller;
        init(width, isLiveWindow);
    }

    void init(int width, boolean isLiveWindow) {
        setMaxWidth(width);
        setMinWidth(width);

        setStyle("-fx-background-color: #336699;");

        Label boxSizeLabel = new Label("Box Size: ");
        boxSizeLabel.setTextFill(Color.WHITE);

        Spinner<Integer> boxSizeSpinner;
        boxSizeSpinner = new Spinner(10, 100, GlobalSettings.INITIAL_BOX_SIZE);
        boxSizeSpinner.setEditable(true);

        boxSizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            controller.setBoxSize(newValue);
        });
        boxSizeSpinner.setPrefWidth(75);
        HBox boxSizeItems = new HBox();
        boxSizeItems.getChildren().addAll(boxSizeLabel,boxSizeSpinner);
        boxSizeItems.setAlignment(Pos.CENTER);
        boxSizeItems.setSpacing(10);

        if (isLiveWindow)
        {
            //change what side layout loaded
            SideMenuButtons top = new SideMenuButtons((DynamicPreviewController) controller);
            top.getChildren().add(boxSizeItems);
            setTop(top);
        }
        else
        {
            SideMenuButtonsStatic top = new SideMenuButtonsStatic((StaticViewController) controller);
            top.getChildren().add(boxSizeItems);
            setTop(top);
        }
        //setTop(new SideMenuButtons());

        nameLable = new Label("Name");
        nameLable.setTextFill(Color.WHITE);
        textFieldName = new TextField();
        textFieldName.setMaxWidth(GlobalSettings.MENU_WIDTH-20);
        textFieldName.setText(controller.getPatientName());

        idLable = new Label("Save Location");
        idLable.setTextFill(Color.WHITE);
        textFieldLocation = new TextField();
        textFieldLocation.setMaxWidth(GlobalSettings.MENU_WIDTH-20);
        textFieldLocation.setText(controller.getSaveLocation());

        //textFieldName.textProperty().bind(controller.getPatientNameProperty());

        textFieldName.setOnKeyReleased(e -> {
            controller.setPatientName(textFieldName.getText());
        });

        textFieldLocation.setOnKeyReleased(e -> {
            controller.setSaveLocation(textFieldLocation.getText());
        });



        //setAlignment(Pos.BASELINE_CENTER);
        VBox bottomBox = new VBox();
        bottomBox.setSpacing(10);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setPadding(new Insets(10));
        SideMenuBottomZoom menuSideZoom = new SideMenuBottomZoom(GlobalSettings.MENU_WIDTH, controller);

        bottomBox.getChildren().addAll(nameLable, textFieldName, idLable, textFieldLocation);
        bottomBox.getChildren().addAll(menuSideZoom); // add last to place on bottom
        setBottom(bottomBox);
    }

}
