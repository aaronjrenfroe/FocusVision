/**
 * Created by AaronR on 1/22/18.
 * for Capstone
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Main extends Application{


    Stage window;
    BorderPane mainLayout;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Focus Vision");
        window.setOnCloseRequest(e -> {
            e.consume();
            onClose();
        });

        MenuPane menu = new MenuPane(GlobalSettings.MENU_WIDTH);
        PreviewPane preview = new PreviewPane(GlobalSettings.INITIAL_WIDTH - GlobalSettings.MENU_WIDTH, GlobalSettings.INITIAL_HEIGHT) ;


        mainLayout = new BorderPane();
        mainLayout.setLeft(menu);
        mainLayout.setCenter(preview);
        mainLayout.widthProperty().addListener( (obs, oldVal, newVal) -> {
            System.out.println(newVal);
            GlobalSettings.PreviewAreaWidth = (double)newVal;
        });
        mainLayout.heightProperty().addListener( (obs, oldVal, newVal) -> {
            System.out.println(newVal);
            GlobalSettings.PreviewAreaHeight = (double)newVal;
        });
        initTopMenu();


        Scene scene = new Scene(mainLayout, GlobalSettings.INITIAL_WIDTH, GlobalSettings.INITIAL_HEIGHT, Color.GRAY); // Gets stuck in here

        window.setScene(scene);

        this.window.show();

    }

    private void initTopMenu(){

        // Open menu
        Menu fileMenu = new Menu("_File");
        MenuItem openOption = new MenuItem("Open");
        MenuItem sep = new SeparatorMenuItem();
        MenuItem exitOption = new MenuItem("Exit");
        fileMenu.getItems().addAll(openOption, sep, exitOption);


        Menu viewMenu = new Menu("_View");
        MenuItem changeCamera = new MenuItem("Change Camera");
        MenuItem boxSize = new MenuItem("Change box size");
        MenuItem hideBox = new MenuItem("Hide Box");

        viewMenu.getItems().addAll(changeCamera, boxSize, hideBox);


        // Help Menu
        Menu helpMenu = new Menu("_Help");
        MenuItem noHelpForYou = new MenuItem("No Help For You");
        helpMenu.getItems().addAll(noHelpForYou);

        MenuBar menu= new MenuBar();
        menu.getMenus().addAll(fileMenu, viewMenu, helpMenu);

        this.mainLayout.setTop(menu);

    }

    private void onClose(){

        window.close();
    }
}
