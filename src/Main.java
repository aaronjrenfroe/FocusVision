/**
 * Created by AaronR on 1/22/18.
 * for Capstone
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application{

    Stage window;


    final private int INITIAL_WIDTH = 1100;
    final private int INITIAL_HEIGHT = 720;
    final private int MENU_WIDTH = 200;

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

        MenuPane menu = new MenuPane(MENU_WIDTH);
        PreviewPane preview = new PreviewPane(INITIAL_WIDTH - MENU_WIDTH,INITIAL_HEIGHT) ;


        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(menu);
        mainLayout.setCenter(preview);

        Scene scene = new Scene(mainLayout, INITIAL_WIDTH, INITIAL_HEIGHT, Color.GRAY);
        window.setScene(scene);




        this.window.show();
    }

    private void onClose(){

        window.close();
    }
}
