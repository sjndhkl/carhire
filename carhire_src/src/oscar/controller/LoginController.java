package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import oscar.model.Staff;
import oscar.MVC.Controller;
import oscar.view.LoginView;

/**
 * The controller that lets user authenticate into the system
 * @author schiodin
 */
public class LoginController extends Controller {

    // Views and dialogs
    private LoginView loginView;

    @Override
    public void run() {
        this.setName("Login");
        loginView = new LoginView();
        this.addElement(loginView);
        this.addElement(loginView.getBtnLogin());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(loginView.getBtnLogin()))
            login();
    }

    private void login() {
        Staff staff = new Staff("username", loginView.getUsername());
        if (staff.authorize(loginView.getPassword(), loginView)) {
            System.out.println("this is logged");
            // clear the password field
            loginView.setPassword("");
            // close loginView
            this.removeAllElement();
            // launch staff or admin controller
            if (staff.isAdmin())
                new AdminController().start();
            else
                new StaffController(false).start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
