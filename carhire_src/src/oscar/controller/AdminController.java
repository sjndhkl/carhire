package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import oscar.persistance.Controller;
import oscar.view.AdminView;

/**
 *
 * @author schiodin
 */
public class AdminController extends Controller {

    private AdminView adminView;

    public void run() {
        this.setName("Admin");
        adminView = new AdminView();
        this.addView(adminView);
        this.addButtonListener(adminView.getLogoutBtn());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(adminView.getLogoutBtn())) {
            logout();
        }
    }

    private void logout() {
        new LoginController().start();
        this.safeStop();
    }
}
