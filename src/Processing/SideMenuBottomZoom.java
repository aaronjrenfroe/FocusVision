package Processing;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by richa on 2/12/2018.
 */
public class SideMenuBottomZoom extends HBox
{
    Button zoomIn;
    Text percentage;  // = new Text(10, 50, "100%");
    Button zoomOut;


    public SideMenuBottomZoom(int width)
    {
        super();
        setMaxWidth(width);
        setMinWidth(width);

        zoomIn = new Button();
        zoomIn.setText("+");
        zoomIn.setMaxWidth(31);
        zoomIn.setMinWidth(31);

        zoomIn.setOnAction(e ->
        {
            System.out.println("Button was clicked -- zooming in!!");
        });

        percentage = new Text();
        percentage.setText("100%");

        zoomOut = new Button();
        zoomOut.setText("-");
        zoomOut.setMaxWidth(31);
        zoomOut.setMinWidth(31);

        zoomOut.setOnAction(e ->
        {
            System.out.println("Button was clicked -- zooming Out!!");
        });

        setSpacing(10);

        setAlignment(Pos.BASELINE_CENTER);

        getChildren().addAll(zoomIn, percentage, zoomOut);

    }
}
