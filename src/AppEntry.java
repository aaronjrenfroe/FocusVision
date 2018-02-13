/**
 * Created by AaronR on 1/22/18.
 * for Capstone
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppEntry extends Application{


    Stage window;
    BasicLayout mainLayout;


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

        SideMenu menu = new SideMenu(GlobalSettings.MENU_WIDTH);
        PreviewPane preview = new PreviewPane(GlobalSettings.INITIAL_WIDTH - GlobalSettings.MENU_WIDTH) ;


        mainLayout = new BasicLayout();

        mainLayout.setSideMenu(menu);
        mainLayout.setPreview(preview);
        mainLayout.setTopMenu(new TopMenu("MAIN"));



        Scene scene = new Scene(mainLayout.getLayout(), GlobalSettings.INITIAL_WIDTH, GlobalSettings.INITIAL_HEIGHT, Color.GRAY);
        window.setScene(scene);
        this.window.show();
        
    }


    private void onClose(){
        System.out.println("Closing Window");
        window.close();
        Platform.exit();
        System.exit(0);
    }
}
