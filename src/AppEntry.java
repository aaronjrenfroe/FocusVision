/**
 * Created by AaronR on 1/22/18.
 * for Capstone
 */

import Models.WindowFactory;
import Views.BasicLayout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class AppEntry extends Application{


    Stage window;
    BasicLayout mainLayout;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.window = WindowFactory.createLiveWindow(primaryStage);

        window.setOnCloseRequest(e -> {
            e.consume();
            onClose();
        });

        this.window.show();

    }


    private void onClose(){
        System.out.println("Closing Window");
        window.close();
        Platform.exit();
        System.exit(0);
    }
}
