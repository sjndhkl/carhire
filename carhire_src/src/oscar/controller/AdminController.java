package oscar.controller;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Timer;
import oscar.model.Car;
import oscar.model.CarClass;
import oscar.model.Rental;
import oscar.persistance.Controller;
import oscar.view.AdminView;
import oscar.model.Staff;
import oscar.persistance.StaffUpdateTableTask;
import oscar.persistance.BookingUpdateTableTask;
import oscar.persistance.CarUpdateTableTask;
import oscar.persistance.ClassUpdateTableTask;
import oscar.persistance.HireUpdatePersonTask;
import oscar.view.dialog.CarClassDialog;
import oscar.view.dialog.CarDialog;
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
    private BookingUpdateTableTask bookingUpdateTableTask;
    private CarUpdateTableTask carUpdateTableTask;
    private ClassUpdateTableTask classUpdateTableTask;
    private HireUpdatePersonTask hireUpdatePersonTask;
    // The staff controller to handle staff tabs
    private StaffController staffController;

    @Override
    public void run() {
        this.setName("Admin");
        adminView = new AdminView();
        staffController = new StaffController();
        this.addElement(adminView);
        this.addElement(adminView.getLogoutBtn());
        // Staff tab
        this.addElement(adminView.getStaffClearBtn());
        this.addElement(adminView.getStaffAddBtn());
        this.addElement(adminView.getStaffSurnameTxt());
        this.addElement(adminView.getStaffNameTxt());
        // Booking tab
        this.addElement(adminView.getBookingRefCodeTxt());
        this.addElement(adminView.getBookingSurnameTxt());
        this.addElement(adminView.getBookingClearBtn());
        this.addElement(adminView.getBookingDeleteBtn());
        // Car tab
        this.addElement(adminView.getCarAddBtn());
        this.addElement(adminView.getCarClearBtn());
        this.addElement(adminView.getCarBrandTxt());
        this.addElement(adminView.getCarClassCb());
        this.addElement(adminView.getCarColorTxt());
        this.addElement(adminView.getCarModelTxt());
        this.addElement(adminView.getCarPlateTxt());
        this.addElement(adminView.getCarStatusCB());
        // Class tab
        this.addElement(adminView.getCarClassAddBtn());
        this.addElement(adminView.getCarClassClearBtn());
        this.addElement(adminView.getCarClassDisplayTxt());
        this.addElement(adminView.getCarClassNameTxt());
        // Hire tab
        this.addElement(adminView.getHireAddressTxt());
        this.addElement(adminView.getHireBtn());
        this.addElement(adminView.getHireChauffeuredCB());
        this.addElement(adminView.getHireClassCB());
        this.addElement(adminView.getHireClearBtn());
        this.addElement(adminView.getHireDateOfBirthDP());
        this.addElement(adminView.getHireEmailTxt());
        this.addElement(adminView.getHireFromDP());
        this.addElement(adminView.getHireInsuranceCB());
        this.addElement(adminView.getHireNameTxt());
        this.addElement(adminView.getHirePersonLoadBtn());
        this.addElement(adminView.getHirePhoneTxt());
        this.addElement(adminView.getHireRefCodeSearchBtn());
        this.addElement(adminView.getHireRefCodeTxt());
        this.addElement(adminView.getHireSurnameTxt());
        this.addElement(adminView.getHireToDP());

        // Filter timers
        timer = new Timer();
        staffUpdateTableTask = new StaffUpdateTableTask();
        bookingUpdateTableTask = new BookingUpdateTableTask();
        carUpdateTableTask = new CarUpdateTableTask();
        classUpdateTableTask = new ClassUpdateTableTask();
        hireUpdatePersonTask = new HireUpdatePersonTask();

        // Set up the tables
        adminView.getStaffTbl().setModel(new Staff().getTableModel());
        adminView.getBookingTbl().setModel(new Rental().getTableModel());
        adminView.getCarTbl().setModel(new Car().getTableModel());
        adminView.getCarClassTbl().setModel(new CarClass().getTableModel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(adminView.getLogoutBtn()))
            actionLogout();
        // Staff Tab
        else if (e.getSource().equals(adminView.getStaffClearBtn()))
            actionStaffClearFields();
        else if (e.getSource().equals(adminView.getStaffAddBtn()))
            actionStaffAdd();
        // Staff Dialog
        // Booking Tab
        else if (e.getSource().equals(adminView.getBookingClearBtn()))
            actionBookingClearFields();
        else if (e.getSource().equals(adminView.getBookingDeleteBtn()))
            actionBookingDelete();
        // Car Tab
        else if (e.getSource().equals(adminView.getCarClearBtn()))
            actionCarClearFields();
        else if (e.getSource().equals(adminView.getCarAddBtn()))
            actionCarAdd();
        // Class tab
        else if (e.getSource().equals(adminView.getCarClassAddBtn()))
            actionClassAdd();
        else if (e.getSource().equals(adminView.getCarClassClearBtn()))
            actionClassClearFields();
        //Hire tab
        else if (e.getSource().equals(adminView.getHireBtn()))
            actionHire();
        else if (e.getSource().equals(adminView.getHireClearBtn()))
            actionHireClearFields();
        else if (e.getSource().equals(adminView.getHirePersonLoadBtn()))
            actionHireLoadPerson();
        else if (e.getSource().equals(adminView.getHireRefCodeSearchBtn()))
            actionHireSearchRefCode();
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

    private void actionBookingDelete() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void actionBookingClearFields() {
        adminView.getBookingRefCodeTxt().setText("");
        adminView.getBookingSurnameTxt().setText("");
        actionBookingUpdateTable();
    }

    private void actionCarClearFields() {
        adminView.getCarBrandTxt().setText("");
        adminView.getCarClassCb().setSelectedIndex(0);
        adminView.getCarColorTxt().setText("");
        adminView.getCarModelTxt().setText("");
        adminView.getCarPlateTxt().setText("");
        adminView.getCarStatusCB().setSelectedIndex(0);
        adminView.getCarYearTxt().setText("");
        actionBookingUpdateTable();
    }

    private void actionCarAdd() {
        // TODO: finish implementation
        this.addElement(new CarDialog(adminView, true));
    }

    private void actionClassAdd() {
        // TODO: finish implementation
        this.addElement(new CarClassDialog(adminView, true));
    }

    private void actionClassClearFields() {
        adminView.getCarClassDisplayTxt().setText("");
        adminView.getCarClassNameTxt().setText("");
        actionClassUpdateTable();
    }

    private void actionHire() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void actionHireSearchRefCode() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void actionHireLoadPerson() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void actionHireClearFields() {
        adminView.getHireChauffeuredCB().setSelected(false);
        adminView.getHireClassCB().setSelectedIndex(0);
        adminView.getHireInsuranceCB().setSelected(false);
        adminView.getHireDateOfBirthDP().setDate(null);
        adminView.getHireFromDP().setDate(null);
        adminView.getHireToDP().setDate(null);
        adminView.getHireAddressTxt().setText("");
        adminView.getHireEmailTxt().setText("");
        adminView.getHireNameTxt().setText("");
        adminView.getHirePhoneTxt().setText("");
        adminView.getHireRefCodeTxt().setText("");
        adminView.getHireSurnameTxt().setText("");
    }

    /*
     * Handle Text Field Listeners calling specific actions
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Staff
        if (e.getSource().equals(adminView.getStaffNameTxt())
                || e.getSource().equals(adminView.getStaffSurnameTxt()))
            actionStaffUpdateTable();
        // Booking
        else if (e.getSource().equals(adminView.getBookingRefCodeTxt())
                || e.getSource().equals(adminView.getBookingSurnameTxt()))
            actionBookingUpdateTable();
        // Class
        else if (e.getSource().equals(adminView.getCarClassDisplayTxt())
                || e.getSource().equals(adminView.getCarClassNameTxt()))
            actionClassUpdateTable();
    }

    private void actionStaffUpdateTable() {
        staffUpdateTableTask.cancel();
        staffUpdateTableTask = new StaffUpdateTableTask();
        timer.schedule(staffUpdateTableTask, this.TABLE_FILTERING_DELAY);
    }

    private void actionBookingUpdateTable() {
        bookingUpdateTableTask.cancel();
        bookingUpdateTableTask = new BookingUpdateTableTask();
        timer.schedule(bookingUpdateTableTask, this.TABLE_FILTERING_DELAY);
    }

    private void actionClassUpdateTable() {
        classUpdateTableTask.cancel();
        classUpdateTableTask = new ClassUpdateTableTask();
        timer.schedule(classUpdateTableTask, this.TABLE_FILTERING_DELAY);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
