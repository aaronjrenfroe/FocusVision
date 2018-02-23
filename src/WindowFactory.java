import Processing.VideoCap;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.opencv.core.Mat;

/**
 * Created by AaronR on 2/20/18.
 * for ?
 */
public class WindowFactory {

    public static Stage createLiveWindow(Stage window) {
        window.setTitle("Focus Vision");

        SideMenu menu = new SideMenu(GlobalSettings.MENU_WIDTH, true);
        PreviewPane preview = new PreviewPane(GlobalSettings.INITIAL_WIDTH - GlobalSettings.MENU_WIDTH) ;


        BasicLayout mainLayout = new BasicLayout();

        mainLayout.setSideMenu(menu);
        mainLayout.setPreview(preview);
        mainLayout.setTopMenu(new TopMenu("MAIN"));


        Scene scene = new Scene(mainLayout.getLayout(), GlobalSettings.INITIAL_WIDTH, GlobalSettings.INITIAL_HEIGHT, Color.GRAY);
        window.setScene(scene);
        return window;

    }

    public static Stage createStaticWindow(Mat bla) {

        TopMenu tm = new TopMenu("VIEWER");

        BasicLayout bl = new BasicLayout();
        PreviewPane pp = new PreviewPane(GlobalSettings.INITIAL_WIDTH - GlobalSettings.MENU_WIDTH, bla);

        bl.setPreview(pp);
        bl.setSideMenu(new SideMenu(GlobalSettings.MENU_WIDTH, false, bla));
        bl.setTopMenu(tm);

        Stage newWindow = new Stage();

        newWindow.setTitle("New Window");

        Scene scene = new Scene(bl.getLayout());

        newWindow.setScene(scene);
        System.out.println("We need to persists new windows for image re-taking");

        return newWindow;


    }
}
