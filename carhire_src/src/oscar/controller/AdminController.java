package oscar.controller;

import java.awt.event.ActionEvent;
import oscar.persistance.Controller;
import oscar.view.AdminView;
import oscar.model.Staff;
import oscar.view.dialog.StaffDialog;

/**
 *
 * @author schiodin
 */
public class AdminController extends Controller {

    private AdminView adminView;
    private StaffDialog staffDialog;

    @Override
    public void run() {
        this.setName("Admin");
        adminView = new AdminView();
        this.addView(adminView);
        this.addButtonListener(adminView.getLogoutBtn());
        this.addButtonListener(adminView.getStaffClearBtn());
        this.addButtonListener(adminView.getStaffAddBtn());
        adminView.getStaffTbl().setModel(new Staff().getTableModel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(adminView.getLogoutBtn()))
            actionLogout();
        /* Staff Tab */
        else if (e.getSource().equals(adminView.getStaffClearBtn()))
            actionStaffClearFields();
        else if (e.getSource().equals(adminView.getStaffAddBtn()))
            actionStaffAdd();
        /* Staff Dialog */
    }

    private void actionLogout() {
        this.safeStop();
        new LoginController().start();
    }

    private void actionStaffClearFields() {
        adminView.getStaffNameTxt().setText("");
        adminView.getStaffSurnameTxt().setText("");
        // TODO: update table filters
    }

    private void actionStaffAdd() {
        // TODO: finish implementation
        staffDialog = new StaffDialog(adminView, true);
    }
}
