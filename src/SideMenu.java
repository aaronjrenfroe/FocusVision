import Processing.SideMenuBottomZoom;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.opencv.core.Mat;

/**
 * Created by AaronR on 1/22/18.
 * for Hooter Whater
 */
public class SideMenu extends BorderPane {



    Label nameLable;
    TextField textField;
    Label idLable;
    TextField textField2;
    Mat bla;

    public SideMenu(int width, boolean isLiveWindow, Mat bla) {
        super();

        this.bla = bla;

        init(width, isLiveWindow);


    }

    public SideMenu(int width, boolean isLiveWindow) {
        super();

        init(width, isLiveWindow);


    }

    void init(int width, boolean isLiveWindow) {
        setMaxWidth(width);
        setMinWidth(width);

        setStyle("-fx-background-color: #336699;");
        if (isLiveWindow)
        {
            //change what side layout loded
            setTop(new SideMenuButtons());
        }
        else
        {
            setTop(new SideMenuButtonsStatic(bla));
        }
        //setTop(new SideMenuButtons());

        nameLable = new Label("Name");
        textField = new TextField();
        textField.setMaxWidth(150);
        idLable = new Label("ID");
        textField2 = new TextField();
        textField2.setMaxWidth(150);

        //setSpacing(10);

        //setAlignment(Pos.BASELINE_CENTER);
        VBox bottomBox = new VBox();
        bottomBox.setSpacing(10);
        bottomBox.setAlignment(Pos.CENTER);
        SideMenuBottomZoom menuSideZoom = new SideMenuBottomZoom(GlobalSettings.MENU_WIDTH);

        bottomBox.getChildren().addAll(nameLable, textField, idLable, textField2);
        bottomBox.getChildren().addAll(menuSideZoom); // add last to place on bottom
        setBottom(bottomBox);
    }

}
