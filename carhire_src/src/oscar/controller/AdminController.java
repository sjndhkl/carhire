package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import oscar.persistance.Controller;
import oscar.view.AdminView;

/**
 *
 * @author schiodin
 */
public class AdminController extends Controller implements ActionListener {
    private AdminView adminView;

    public void run() {
        this.setName("Admin");
        adminView = new AdminView();
        this.addView(adminView);
        adminView.getLogoutBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(adminView.getLogoutBtn()))
            logout();
    }

    private void logout() {
        adminView.getLogoutBtn().removeActionListener(this);
        this.removeView(adminView);
        new LoginController().start();
    }
}
