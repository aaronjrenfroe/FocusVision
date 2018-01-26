package Processing;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by AaronR on 1/26/18.
 * for ?
 */
public class Metrics {

    private static Metrics metrics;

    private SimpleStringProperty lapProperty;
    private SimpleStringProperty mcontrastProperty;

    private double michelsonContrast = 0.0;
    private double rmsContrast = 0.0;

    private Metrics(){

        lapProperty = new SimpleStringProperty();
        mcontrastProperty = new SimpleStringProperty();

        lapProperty.set("n/a");
        mcontrastProperty.set("n/a");

        michelsonContrast = 0.0;
        rmsContrast = 0.0;

    }


    public static Metrics get(){
        if(metrics == null){
            metrics = new Metrics();
        }
        return metrics;
    }

    public SimpleStringProperty getLaplaceProperty() {
        return lapProperty;
    }


    public void setLaplace(double laplace) {
        Platform.runLater(() -> {
            lapProperty.set("Focus: " + (int) Math.round(laplace));
        });
    }


    public void setMichelsonContrast(double michelsonContrast) {
        this.michelsonContrast = michelsonContrast;

        Platform.runLater(() -> {
            mcontrastProperty.set("Contrast: " + ((int)(michelsonContrast * 1000))/1000.0);
        });
    }

    public SimpleStringProperty getMichelsonContrastProperty() {
        return mcontrastProperty;
    }

    public void setRmsContrast(double rmsContrast) {
        this.rmsContrast = rmsContrast;
    }

    public double getRmsContrastProperty() {
        return rmsContrast;
    }



}
