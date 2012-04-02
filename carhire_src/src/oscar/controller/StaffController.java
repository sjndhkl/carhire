package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import oscar.persistance.Controller;
import oscar.view.StaffView;

/**
 *
 * @author Draga
 */
public class StaffController extends Controller implements ActionListener {
    private StaffView staffView;

    public void run() {
        this.setName("Staff");
        staffView = new StaffView();
        this.addView(staffView);
        staffView.getLogoutBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(staffView.getLogoutBtn()))
            logout();
    }

    private void logout() {
        staffView.getLogoutBtn().removeActionListener(this);
        this.removeView(staffView);
        new LoginController().start();
    }
}
