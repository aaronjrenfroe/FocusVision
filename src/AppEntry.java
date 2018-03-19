/**
 * Created by AaronR on 1/22/18.
 * for Capstone
 */

import Models.DynamicPreviewController;
import Models.ViewManager;
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
        System.out.println("Terminating FX Application");
        ViewManager.getManager().getPrimaryStage().killTimer();
        window.close();
        Platform.exit();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Stoping JVM");
        System.exit(0);
    }
}
