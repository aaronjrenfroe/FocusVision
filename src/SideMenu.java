import Processing.SideMenuBottomZoom;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Created by AaronR on 1/22/18.
 * for Hooter Whater
 */
public class SideMenu extends BorderPane {




    TextField textField;
    TextField textField2;

    public SideMenu(int width) {
        super();
        setMaxWidth(width);
        setMinWidth(width);

        setStyle("-fx-background-color: #336699;");
        setTop(new SideMenuButtons());


        textField = new TextField();
        textField.setMaxWidth(150);
        textField2 = new TextField();
        textField2.setMaxWidth(150);

        //setSpacing(10);

        //setAlignment(Pos.BASELINE_CENTER);

        SideMenuBottomZoom menuSideZoom = new SideMenuBottomZoom(GlobalSettings.MENU_WIDTH);

        setBottom(menuSideZoom);

    }
}
