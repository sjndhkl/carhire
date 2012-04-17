/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Timer;
import oscar.model.Staff;
import oscar.persistance.Controller;
import oscar.persistance.HireUpdatePersonTask;
import oscar.view.LoginView;

/**
 *
 * @author schiodin
 */
public class LoginController extends Controller {

    private LoginView loginView;

    @Override
    public void run() {
        this.setName("Login");
        loginView = new LoginView();
        /*this.addView(loginView);
        this.addButtonListener(loginView.getBtnLogin());*/
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
        if (staff.authorize(loginView.getPassword())) {
            System.out.println("this is logged");
            // remove the password
            loginView.setPassword("");
            this.removeAllElement();
            // launch staff contoller, if admin launches the admin one as well
            if (staff.isAdmin())
                new AdminController().start();
            new StaffController().start();
        }
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
