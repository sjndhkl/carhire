/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

import java.util.TimerTask;

/**
 *
 * @author Draga
 */
public class StaffUpdateTableTask extends TimerTask{

    @Override
    public void run() {
        //TODO: implement staff table filter update
        System.out.println(System.nanoTime() + "update staff table");
    }

}
