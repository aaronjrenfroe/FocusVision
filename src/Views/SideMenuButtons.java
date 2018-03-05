package Views;

import Models.DynamicPreviewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Created by richa on 2/12/2018.
 */
public class SideMenuButtons extends VBox {
    Button newButton;
    Button retakeButton;
    DynamicPreviewController controller;

    public SideMenuButtons(DynamicPreviewController controller) {

        this.controller = controller;
        newButton = new Button();
        newButton.setText("Capture");
        newButton.setMaxWidth(90);
        newButton.setMinWidth(90);

        newButton.setOnAction(e ->
        {
            System.out.println("Button was clicked");
            controller.captureImagePressed();

        });

        retakeButton = new Button();
        retakeButton.setText("Recapture");
        //retakeButton.setMaxWidth(81);
        //retakeButton.setMinWidth(81);
        retakeButton.setDisable(true);
        retakeButton.setOnAction(e ->
        {
            controller.reCaptureImagePressed();
        });
        retakeButton.disableProperty().bind(controller.getRecaptureButtonDisabledProperty());

        this.getChildren().addAll(newButton, retakeButton);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10));

    }

}
