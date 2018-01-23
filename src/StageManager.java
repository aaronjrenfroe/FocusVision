import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by AaronR on 1/22/18.
 * for ?
 */
public class StageManager {

    private StageManager manager;
    private ArrayList<Stage> stages;

    private StageManager() {
        stages = new ArrayList();
    }


    public StageManager getManager(){
        if(manager == null){
            manager = new StageManager();
        }
        return manager;
    }

    public Stage getPrimaryStage(){
        if(stages.size() == 0){
            return null;
        }
        return stages.get(0);
    }

    public void addStage(Stage stage){
        stages.add(stage);
    }


}
