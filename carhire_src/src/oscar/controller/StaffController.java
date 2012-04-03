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
        this.addView(staffView);
        this.addButtonListener(staffView.getLogoutBtn());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(staffView.getLogoutBtn())) {
            actionLogout();
        }
    }

    private void actionLogout() {
        new LoginController().start();
        this.safeStop();
    }
}
