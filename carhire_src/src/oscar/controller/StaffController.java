package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import oscar.persistance.Controller;
import oscar.view.StaffView;

/**
 *
 * @author Draga
 */
public class StaffController extends Controller {

    private StaffView staffView;

    @Override
    public void run() {
        this.setName("Staff");
        staffView = new StaffView();
        /*this.addView(staffView);
        this.addButtonListener(staffView.getLogoutBtn());*/
        this.addElement(staffView);
        this.addElement(staffView.getLogoutBtn());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(staffView.getLogoutBtn())) {
            actionLogout();
        }
    }

    private void actionLogout() {
        new LoginController().start();
        this.removeAllElement();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
