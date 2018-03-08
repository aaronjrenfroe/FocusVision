package Models;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by AaronR on 1/26/18.
 * for ?
 */
public class Metrics {
    private static final int BUFFER_SIZE = 1;

    private static final int EDGE_STRENGTH_INDEX = 0;
    private static final int CONTRAST_INDEX = 1;
    private static final int BRIGHTNESS_INDEX = 2;
    private static final int STANDARD_DEVIATION_INDEX = 3;

    private static final String EDGE_STRENGTH_LABEL = "Laplace Variance: ";
    private static final String CONTRAST_LABEL = "Michelson Contrast: ";
    private static final String BRIGHTNESS_LABEL = "Brightness: ";
    private static final String STANDARD_DEVIATION_LABEL = "Standard Deviation: ";

    private SimpleStringProperty lapProperty;
    private SimpleStringProperty mcontrastProperty;
    private SimpleStringProperty brightnessProperty;
    private SimpleStringProperty standardDevProperty;


    private double [] mcontrastBuffer;
    private double [] focusBuffer;
    private double [] brightnessBuffer;
    private double [] standardDeviationBuffer;



    // ADD BUFFERS
    // ADD GETTERS AND SETTERS

    private int[] bufferPositions;


    public Metrics(){


        lapProperty = new SimpleStringProperty();
        mcontrastProperty = new SimpleStringProperty();
        brightnessProperty = new SimpleStringProperty();
        standardDevProperty = new SimpleStringProperty();

        lapProperty.set("Laplace Variance: n/a");
        mcontrastProperty.set("Michelson Contrast: n/a");
        brightnessProperty.set("Brightness: n/a");
        standardDevProperty.set("Standard Deviation: n/a");


        focusBuffer = new double[BUFFER_SIZE];
        mcontrastBuffer = new double[BUFFER_SIZE];
        brightnessBuffer = new double[BUFFER_SIZE];
        standardDeviationBuffer = new double[BUFFER_SIZE];

        bufferPositions = new int[4];

    }




    public void setContrast(double michelsonContrast) {

        mcontrastBuffer[bufferPositions[CONTRAST_INDEX]] = michelsonContrast;
        setProperty(CONTRAST_LABEL + ((int)(getMean(mcontrastBuffer) * 1000))/1000.0, mcontrastProperty, CONTRAST_INDEX);
    }

    public void setEdgeStrength(double laplace) {
        focusBuffer[bufferPositions[EDGE_STRENGTH_INDEX]] = laplace;

        setProperty(EDGE_STRENGTH_LABEL + (int) Math.round(getMean(focusBuffer)), lapProperty, EDGE_STRENGTH_INDEX);
    }

    public void setBrightness(double brightness) {

        focusBuffer[bufferPositions[BRIGHTNESS_INDEX]] = brightness;

        setProperty(BRIGHTNESS_LABEL + (int) Math.round(getMean(focusBuffer)) + "%", brightnessProperty, BRIGHTNESS_INDEX);
    }

    public void setStandardDeviation(double stdDev) {

        focusBuffer[bufferPositions[STANDARD_DEVIATION_INDEX]] = stdDev;
        setProperty(STANDARD_DEVIATION_LABEL+ (int) Math.round(getMean(focusBuffer)), standardDevProperty, STANDARD_DEVIATION_INDEX);
    }

    private void  setProperty(String value, SimpleStringProperty property, int bufferIndex){
        bufferPositions[bufferIndex] += 1;

        if (bufferPositions[bufferIndex] == BUFFER_SIZE){
            bufferPositions[bufferIndex] = 0;
        }

        Platform.runLater(() -> {
            property.set(value);
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

    public SimpleStringProperty getLaplaceProperty() {
        return lapProperty;
    }

    public SimpleStringProperty getBrightnessProperty() {
        return brightnessProperty;
    }

    public SimpleStringProperty getStandardDevProperty() {
        return standardDevProperty;
    }




}
