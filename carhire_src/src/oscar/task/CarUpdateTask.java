package oscar.task;

import java.util.TimerTask;

/**
 *
 * @author schiodin
 */
public class CarUpdateTask extends TimerTask {

    @Override
    public void run() {
        //TODO: implement booking table filter update
        System.out.println(System.nanoTime() + "update Car table");
    }
}