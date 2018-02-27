package Models;



/**
 * Created by AaronR on 2/25/18.
 * for ?
 */

public class DynamicPreviewModel extends ViewModel {


    public DynamicPreviewModel() {

        super("FocusVision");
        saveLocation.set(getDefaultSaveLocation() + "/FocusVison/Images");
    }

    private String getDefaultSaveLocation(){
        return System.getProperty("user.home");
    }

    // capture image
    public void captureImage(){
        System.out.println("Should Capture Image and Open New Window");
    }
    // recapture Image

    public void reCaptureImage(){

        System.out.println("Should Replace Image in last Opened Window");
    }

}
