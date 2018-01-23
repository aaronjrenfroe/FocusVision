import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import Processing.PreviewController;
/**
 * Created by AaronR on 1/22/18.
 * for ?
 */
public class PreviewPane extends AnchorPane {

    // Consists of Image Preview, and BoxDrawing


    public PreviewPane(int width, int height) {
        super();
        ImageView preview = new ImageView();

        setStyle("-fx-background-color: #606360;");
        PreviewController  previewController = new PreviewController();

        getChildren().add(preview);

        previewController.startCamera(getWidth(), getHeight(),preview);

    }

}
