/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oscar.model.Person;
import oscar.model.Staff;
import oscar.persistance.Controller;
import oscar.view.AdminView;
import oscar.view.LoginView;
import oscar.view.StaffView;

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
        this.addView(loginView);
        this.addButtonListener(loginView.getBtnLogin());
        //.addModel(new Person());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(loginView.getBtnLogin()))
            login();
    }

    private void login() {
        Staff staff = new Staff("username", loginView.getUsername());
        try {
            if (staff.authorize(loginView.getPassword())) {
                System.out.println("this is logged");
                // remove the password
                loginView.setPassword("");
                this.safeStop();
                // launch staff contoller, if admin launches the admin one as well
                if (staff.isAdmin())
                    new AdminController().start();
                else
                    new StaffController().start();
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
