package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import oscar.persistance.Controller;
import oscar.view.AdminView;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;
import oscar.model.Person;
import oscar.model.Staff;
import oscar.persistance.DbRecord;

/**
 *
 * @author schiodin
 */
public class AdminController extends Controller {

    private AdminView adminView;

    @Override
    public void run() {
        this.setName("Admin");
        adminView = new AdminView();
        this.addView(adminView);
        this.addButtonListener(adminView.getLogoutBtn());
        adminView.getStaffTbl().setModel(new Staff().getTableModel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(adminView.getLogoutBtn()))
            actionLogout();
    }

    private void actionLogout() {
        this.safeStop();
        new LoginController().start();
    }
}
