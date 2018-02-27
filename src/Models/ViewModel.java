package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

/**
 * Created by AaronR on 2/25/18.
 * for ?
 */
public abstract class ViewModel {

    ImageView imageView;
    String windowName;


    SimpleStringProperty patientName;
    SimpleStringProperty saveLocation;
    SimpleIntegerProperty boxSize;

    public ViewModel(String windowName) {

        imageView = new ImageView();
        this.windowName = windowName;
        System.out.println(saveLocation);

        patientName = new SimpleStringProperty();
        saveLocation = new SimpleStringProperty();
        boxSize.set(50);

    }

    // change box size
    // on resize
    // other menu things
    // open help


    public String getPatientName() {
        return patientName.get();
    }

    public SimpleStringProperty patientNameProperty() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName.set(patientName);
    }

    public String getSaveLocation() {
        return saveLocation.get();
    }

    public SimpleStringProperty saveLocationProperty() {
        return saveLocation;
    }

    public void setSaveLocation(String saveLocation) {
        this.saveLocation.set(saveLocation);
    }

    public int getBoxSize() {
        return boxSize.get();
    }

    public SimpleIntegerProperty boxSizeProperty() {
        return boxSize;
    }

    public void setBoxSize(int newSize) {
        if (newSize > 10 && newSize < 120) {
            boxSize.set(newSize);
        }
    }



}
