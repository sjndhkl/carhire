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
    
    public AdminController() {
        adminView = new AdminView();
        this.addView(adminView);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
