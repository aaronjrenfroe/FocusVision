package Models;

import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by AaronR on 1/22/18.
 * for ?
 */
public class ViewManager {

    private static ViewManager manager;

    private ArrayList<AbstractViewController> views;

    private ViewManager() {
        views = new ArrayList();
    }

    int totaleViewCreated = 0;

    public static ViewManager getManager(){
        if(manager == null){
            manager = new ViewManager();
        }
        return manager;
    }

    public AbstractViewController getPrimaryStage(){
        if(views.size() == 0){
            return null;
        }
        return views.get(0);
    }

    public void addStage(AbstractViewController view){
        views.add(view);
        totaleViewCreated ++;
    }

    public void removeStage(AbstractViewController view){
        if(views.contains(view)){
            views.remove(view);

        }
    }

    public StaticViewController getLast(){
        if(views.size() >= 0) {
            return (StaticViewController) views.get(views.size() - 1);
        }
        return null;
    }

    public int getTotalViewsCreated(){
        return totaleViewCreated;
    }


}
