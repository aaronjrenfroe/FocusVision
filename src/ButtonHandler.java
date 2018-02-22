/**
 * Created by AaronR on 2/20/18.
 * for ?
 */
public class ButtonHandler {

    Runnable runner;
    ButtonHandler bh;


    public void setRunner(Runnable runner) {
        this.runner = runner;
    }

    public void runRunner(){
        runner.run();
    }
}
