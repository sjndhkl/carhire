package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import oscar.model.Car;
import oscar.model.CarClass;
import oscar.model.Person;
import oscar.model.Rental;
import oscar.persistance.Controller;
import oscar.view.AdminView;
import oscar.model.Staff;
import oscar.persistance.StaffUpdateTask;
import oscar.persistance.BookingUpdateTask;
import oscar.persistance.CarUpdateTask;
import oscar.persistance.ClassUpdateTask;
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
    private StaffUpdateTask staffUpdateTask;
    private BookingUpdateTask bookingUpdateTask;
    private CarUpdateTask carUpdateTask;
    private ClassUpdateTask classUpdateTask;

    @Override
    public void run() {
        this.setName("Admin");

        initAdminView();
        initStaffDialog();

        // Filter timers
        timer = new Timer();
        staffUpdateTask = new StaffUpdateTask();
        bookingUpdateTask = new BookingUpdateTask();
        carUpdateTask = new CarUpdateTask();
        classUpdateTask = new ClassUpdateTask();

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
        else if (e.getSource().equals(staffDialog.getSaveBtn()))
            actionStaffDlgSave();
        else if (e.getSource().equals(staffDialog.getDeleteBtn()))
            actionStaffDlgDelete();
        else if (e.getSource().equals(staffDialog.getCancelBtn()))
            actionStaffDlgCancel();
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
        staffDialog.setVisible(true);
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
        staffUpdateTask.cancel();
        staffUpdateTask = new StaffUpdateTask();
        timer.schedule(staffUpdateTask, this.TABLE_FILTERING_DELAY);
    }

    private void actionBookingUpdateTable() {
        bookingUpdateTask.cancel();
        bookingUpdateTask = new BookingUpdateTask();
        timer.schedule(bookingUpdateTask, this.TABLE_FILTERING_DELAY);
    }

    private void actionClassUpdateTable() {
        classUpdateTask.cancel();
        classUpdateTask = new ClassUpdateTask();
        timer.schedule(classUpdateTask, this.TABLE_FILTERING_DELAY);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void actionStaffDlgSave() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Person person = new Person(
                staffDialog.getNameTxt().getText(),
                staffDialog.getSurnameTxt().getText(),
                dateFormat.format(staffDialog.getDateOfBirthDP().getDate()),
                staffDialog.getEmailTxt().getText(),
                staffDialog.getAddressTA().getText(),
                staffDialog.getPhoneTxt().getText());
        // TODO: implement failure
        person.add();
        //person
        //Staff staff = new Staff
    }

    private void actionStaffDlgCancel() {
        staffDialog.getAddressTA().setText("");
        staffDialog.getDateOfBirthDP().setDate(null);
        staffDialog.getEmailTxt().setText("");
        staffDialog.getNameTxt().setText("");
        staffDialog.getSurnameTxt().setText("");
        staffDialog.getPasswordPwd().setText("");
        staffDialog.getUsernameTxt().setText("");
        staffDialog.getPhoneTxt().setText("");
        staffDialog.setVisible(false);
    }

    private void actionStaffDlgDelete() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void initAdminView() {
        adminView = new AdminView();
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
    }

    private void initStaffDialog() {
        
        staffDialog = new StaffDialog(adminView, true);
        this.addElement(staffDialog);
        // Staff dialog
        this.addElement(staffDialog.getSaveBtn());
        this.addElement(staffDialog.getDeleteBtn());
        this.addElement(staffDialog.getCancelBtn());
    }
}
