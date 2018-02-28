package Models;

import Helpers.GlobalSettings;
import Models.DynamicPreviewController;
import Models.StaticViewController;

import Processing.VideoCap;
import Views.BasicLayout;
import Views.PreviewPane;
import Views.SideMenu;
import Views.TopMenu;
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

        DynamicPreviewController controller = new DynamicPreviewController();

        SideMenu menu = new SideMenu(GlobalSettings.MENU_WIDTH, true, controller);

        PreviewPane preview = new PreviewPane(GlobalSettings.INITIAL_WIDTH - GlobalSettings.MENU_WIDTH, controller);

        TopMenu topMenu = new TopMenu("MAIN",controller);

        BasicLayout mainLayout = new BasicLayout();
        mainLayout.setSideMenu(menu);
        mainLayout.setPreview(preview);
        mainLayout.setTopMenu(topMenu);

        Scene scene = new Scene(mainLayout.getLayout(), GlobalSettings.INITIAL_WIDTH, GlobalSettings.INITIAL_HEIGHT, Color.GRAY);
        window.setScene(scene);
        return window;

    }


    public static Stage createStaticWindow(DynamicPreviewController creator) {
        StaticViewController controller = new StaticViewController(VideoCap.getInstance().getOneFrame());
        controller.setPatientName(creator.getPatientName());
        controller.setSaveLocation(creator.getSaveLocation());

        TopMenu tm = new TopMenu("VIEWER", controller);
        PreviewPane pp = new PreviewPane(GlobalSettings.INITIAL_WIDTH - GlobalSettings.MENU_WIDTH, controller);

        BasicLayout bl = new BasicLayout();

        //StaticViewModel model = new StaticViewModel();

        bl.setPreview(pp);
        bl.setSideMenu(new SideMenu(GlobalSettings.MENU_WIDTH, false, controller));
        bl.setTopMenu(tm);

        Stage newWindow = new Stage();

        newWindow.setTitle("New Window");

        Scene scene = new Scene(bl.getLayout());

        newWindow.setScene(scene);
        System.out.println("We need to persists new windows for image re-taking");

        return newWindow;

    }
}
