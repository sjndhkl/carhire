/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class LoginController extends Controller implements ActionListener {

    private LoginView loginView;
    private AdminView adminView;
    private StaffView staffView;

    public LoginController() {
        loginView = new LoginView();
        loginView.getBtnLogin().addActionListener(this);
        this.addView(loginView);
        //.addModel(new Person());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(loginView.getBtnLogin())) {
            login();
        }
    }

    private void login() {
        oscar.model.Staff staff = new Staff("username", loginView.getUsername());
        try {
            if (staff.authorize(loginView.getPassword())) {
                System.out.println("this is logged");
                // remove the password
                loginView.setPassword("");
                // hides the login view
                this.removeView(loginView);
                // launch a new view
                if (staff.isAdmin()) {
                    new AdminController().start();
                    //this.stop();
                } else {
                    staffView = new StaffView();
                    this.addView(staffView);
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
