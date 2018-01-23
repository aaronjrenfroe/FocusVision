import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by AaronR on 1/22/18.
 * for Hooter Whater
 */
public class MenuPane extends VBox{

    Button newButton;
    Button retakeButton;

    TextField textField;
    TextField textField2;

    public MenuPane(int width) {
        super();
        setMaxWidth(width);
        setMinWidth(width);

        setStyle("-fx-background-color: #336699;");


        newButton = new Button();
        newButton.setText("Capture");
        newButton.setMaxWidth(81);
        newButton.setMinWidth(81);

        newButton.setOnAction(e -> {
            System.out.println("Button was clicked");
            retakeButton.setDisable(false);
        });

         retakeButton = new Button();
        retakeButton.setText("Recapture");
        retakeButton.setMaxWidth(81);
        retakeButton.setMinWidth(81);
        retakeButton.setDisable(true);
        retakeButton.setOnAction(e -> {
            System.out.println("Retake Image");
        });

        textField = new TextField();
        textField.setMaxWidth(150);
        textField2 = new TextField();
        textField2.setMaxWidth(150);

        setSpacing(10);

        setAlignment(Pos.BASELINE_CENTER);

        getChildren().addAll(newButton, retakeButton, textField, textField2);

    }
}
