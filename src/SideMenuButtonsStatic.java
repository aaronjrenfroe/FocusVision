import Processing.VideoCap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;


/**
 * Created by richa on 2/12/2018.
 */
public class SideMenuButtonsStatic extends VBox {
    Button newButton;
    Button retakeButton;
    Mat bla;

    public SideMenuButtonsStatic(Mat bla) {

        this.bla = bla;
        newButton = new Button();
        newButton.setText("Save");
        newButton.setMaxWidth(90);
        newButton.setMinWidth(90);

        newButton.setOnAction(e ->
        {
            System.out.println("Button was clicked");
            retakeButton.setDisable(false);
            saveImage("capturedImage");

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

    private void saveImage(String fileName)
    {
       //save image function
        String imagePath = System.getProperty("user.home") + "/Desktop";

        Imgcodecs.imwrite(fileName.replace(" ", "_")+".png", bla);

    }

}
