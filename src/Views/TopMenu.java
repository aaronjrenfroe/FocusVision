package Views;

import Models.AbstractViewController;
import Processing.VideoCap;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.awt.*;
import java.net.URL;

/**
 * Created by AaronR on 2/5/18.
 * for ?
 */
public class TopMenu extends MenuBar {

    AbstractViewController controller;

    public TopMenu(String forWindow, AbstractViewController controller) {
        this.controller = controller;

        switch(forWindow){
            case "MAIN":
                initForMainWindow();
                break;
            case "VIEWER":
                initForViewWindow();
                break;
            default:
                break;
        }
    }

    private void initForViewWindow(){
        Menu fileMenu = new Menu("_File");
        MenuItem openOption = new MenuItem("Open");
        MenuItem saveOption = new MenuItem("Save");
        MenuItem sep = new SeparatorMenuItem();
        MenuItem exitOption = new MenuItem("Exit");
        fileMenu.getItems().addAll(openOption, saveOption, sep, exitOption);

        // Help Menu
        Menu helpMenu = new Menu("_Help");
        MenuItem openGithub = new MenuItem("View On Github");
        openGithub.setOnAction(e -> {
            openWebpage("https://github.com/aaronjrenfroe/FocusVision");
        });
        helpMenu.getItems().addAll(openGithub);

        this.getMenus().addAll(fileMenu, helpMenu);
    }

    private void initForMainWindow(){
        Menu fileMenu = new Menu("_File");
        MenuItem openOption = new MenuItem("Open");
        MenuItem sep = new SeparatorMenuItem();
        MenuItem exitOption = new MenuItem("Exit");
        fileMenu.getItems().addAll(openOption, sep, exitOption);


        Menu viewMenu = new Menu("_View");
        MenuItem changeCamera = new MenuItem("Change Camera");
        changeCamera.setOnAction(e -> {
            VideoCap.getInstance().nextCamera();
        });

        MenuItem detectCameras = new MenuItem("Detect Cameras");
        detectCameras.setOnAction(e -> {
            VideoCap.getInstance().countCameras();
        });

        MenuItem boxSize = new MenuItem("Change box size");
        MenuItem hideBox = new MenuItem("Hide Box");

        viewMenu.getItems().addAll(changeCamera, detectCameras,boxSize, hideBox);

        // Help Menu
        Menu helpMenu = new Menu("_Help");
        MenuItem openGithub = new MenuItem("View On Github");
        openGithub.setOnAction(e -> {
            openWebpage("https://github.com/aaronjrenfroe/FocusVision");
        });
        helpMenu.getItems().addAll(openGithub);

        this.getMenus().addAll(fileMenu, viewMenu, helpMenu);
    }


    private static boolean openWebpage(String strUrl) {

        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URL(strUrl).toURI());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }



}
