package Models;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by AaronR on 1/26/18.
 * for ?
 */
public class Metrics {
    private static final int BUFFER_SIZE = 10;

    private SimpleStringProperty lapProperty;
    private SimpleStringProperty mcontrastProperty;

    private double michelsonContrast = 0.0;
    private double rmsContrast = 0.0;

    private double [] mcontrastBuffer;
    private double [] focusBuffer;

    private int bufferPosition;


    public Metrics(){


        lapProperty = new SimpleStringProperty();
        mcontrastProperty = new SimpleStringProperty();

        lapProperty.set("Laplace Variance: n/a");
        mcontrastProperty.set("Michelson Contrast: n/a");

        michelsonContrast = 0.0;
        rmsContrast = 0.0;

        focusBuffer = new double[BUFFER_SIZE];
        mcontrastBuffer = new double[BUFFER_SIZE];
        bufferPosition = 0;

    }


    public SimpleStringProperty getLaplaceProperty() {
        return lapProperty;
    }

    public void setMetrics(double laplace, double michelsonContrast) {
        focusBuffer[bufferPosition] = laplace;
        mcontrastBuffer[bufferPosition] = michelsonContrast;
        bufferPosition += 1;

        if (bufferPosition == BUFFER_SIZE){
            bufferPosition = 0;
        }

        Platform.runLater(() -> {
            lapProperty.set("Laplace Variance: " + (int) Math.round(getMean(focusBuffer)));
            mcontrastProperty.set("Michelson Contrast: " + ((int)(getMean(mcontrastBuffer) * 1000))/1000.0);
        });
    }

    private double getMean(double[] array){
        double sum = 0.0;
        for (double d : array) sum += d;
        return sum/array.length;
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
