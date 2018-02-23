import Processing.VideoCap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.opencv.core.Mat;

/**
 * Created by richa on 2/12/2018.
 */
public class SideMenuButtons extends VBox {
    Button newButton;
    Button retakeButton;

    public SideMenuButtons() {

        newButton = new Button();
        newButton.setText("Capture");
        newButton.setMaxWidth(90);
        newButton.setMinWidth(90);

        newButton.setOnAction(e ->
        {
            System.out.println("Button was clicked");
            retakeButton.setDisable(false);
            newWindow();

        });

        retakeButton = new Button();
        retakeButton.setText("Recapture");
        //retakeButton.setMaxWidth(81);
        //retakeButton.setMinWidth(81);
        retakeButton.setDisable(true);
        retakeButton.setOnAction(e ->
        {
            System.out.println(retakeButton.getWidth());
            System.out.println("Retake Image");
        });

        this.getChildren().addAll(newButton, retakeButton);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(10));

    }

    private Mat newWindow()
    {
        Mat temp;
        temp = VideoCap.getInstance().getOneFrame();
        Stage newWindow = WindowFactory.createStaticWindow(temp);
        newWindow.show();

        return temp;


    }

}
