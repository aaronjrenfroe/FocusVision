/**
 * Created by AaronR on 1/24/18.
 * for ?
 */
public class GlobalSettings {

    static final int INITIAL_WIDTH = 1100;
    static final int INITIAL_HEIGHT = 720;

    static final int MENU_WIDTH = 200;

    static double HALF_SIDE = 50;

    static double PreviewAreaWidth = INITIAL_WIDTH - MENU_WIDTH;
    static double PreviewAreaHeight = INITIAL_HEIGHT;

    private int cameraWidth;
    private int cameraHeight;

    private int boxSize = 50;

    public int getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(int boxSize) {
        this.boxSize = boxSize;
    }

    public int getCameraWidth() {
        return cameraWidth;
    }

    public void setCameraWidth(int cameraWidth) {
        this.cameraWidth = cameraWidth;
    }

    public int getCameraHeight() {
        return cameraHeight;
    }

    public void setCameraHeight(int cameraHeight) {
        this.cameraHeight = cameraHeight;
    }
}

