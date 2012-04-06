package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Timer;
import oscar.persistance.Controller;
import oscar.view.AdminView;
import oscar.model.Staff;
import oscar.persistance.StaffUpdateTableTask;
import oscar.view.dialog.StaffDialog;

/**
 *
 * @author schiodin
 */
public class AdminController extends Controller {

    private AdminView adminView;
    private StaffDialog staffDialog;
    private Timer timer;
    private StaffUpdateTableTask staffUpdateTableTask;

    @Override
    public void run() {
        this.setName("Admin");
        adminView = new AdminView();
        /*this.addView(adminView);
        this.addButtonListener(adminView.getLogoutBtn());
        this.addButtonListener(adminView.getStaffClearBtn());
        this.addButtonListener(adminView.getStaffAddBtn());
        this.addTextFieldListener(adminView.getStaffSurnameTxt());
        this.addTextFieldListener(adminView.getStaffNameTxt());*/
        this.addElement(adminView);
        this.addElement(adminView.getLogoutBtn());
        this.addElement(adminView.getStaffClearBtn());
        this.addElement(adminView.getStaffAddBtn());
        this.addElement(adminView.getStaffSurnameTxt());
        this.addElement(adminView.getStaffNameTxt());
        timer = new Timer();
        staffUpdateTableTask = new StaffUpdateTableTask();
        this.addElement(adminView);

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
        this.removeAllElement();
        new LoginController().start();
    }

    private void actionStaffClearFields() {
        adminView.getStaffNameTxt().setText("");
        adminView.getStaffSurnameTxt().setText("");
        actionStaffUpdateTable();
    }

    private void actionStaffAdd() {
        // TODO: finish implementation
        this.addElement(new StaffDialog(adminView, true));
    }

    /*
     * Handle Text Field Listeners calling specific actions
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource().equals(adminView.getStaffNameTxt())
                || e.getSource().equals(adminView.getStaffSurnameTxt()))
            actionStaffUpdateTable();
    }

    private void actionStaffUpdateTable() {
        staffUpdateTableTask.cancel();
        staffUpdateTableTask = new StaffUpdateTableTask();
        timer.schedule(staffUpdateTableTask, this.TABLE_FILTERING_DELAY);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
