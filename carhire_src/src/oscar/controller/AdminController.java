package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Timer;
import oscar.model.Car;
import oscar.model.Rental;
import oscar.persistance.Controller;
import oscar.view.AdminView;
import oscar.model.Staff;
import oscar.persistance.StaffUpdateTableTask;
import oscar.persistance.BookingUpdateTableTask;
import oscar.persistance.CarUpdateTableTask;
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

    @Override
    public void run() {
        this.setName("Admin");
        adminView = new AdminView();
        this.addElement(adminView);
        this.addElement(adminView.getLogoutBtn());
        // Staff
        this.addElement(adminView.getStaffClearBtn());
        this.addElement(adminView.getStaffAddBtn());
        this.addElement(adminView.getStaffSurnameTxt());
        this.addElement(adminView.getStaffNameTxt());
        // Booking
        this.addElement(adminView.getBookingRefCodeTxt());
        this.addElement(adminView.getBookingSurnameTxt());
        this.addElement(adminView.getBookingClearBtn());
        this.addElement(adminView.getBookingDeleteBtn());
        // Car
        this.addElement(adminView.getCarAddBtn());
        this.addElement(adminView.getCarClearBtn());
        this.addElement(adminView.getCarBrandTxt());
        this.addElement(adminView.getCarClassCb());
        this.addElement(adminView.getCarColorTxt());
        this.addElement(adminView.getCarModelTxt());
        this.addElement(adminView.getCarPlateTxt());
        this.addElement(adminView.getCarStatusCB());
        // Filter timers
        timer = new Timer();
        staffUpdateTableTask = new StaffUpdateTableTask();
        bookingUpdateTableTask = new BookingUpdateTableTask();
        carUpdateTableTask = new CarUpdateTableTask();

        // Set up the tables
        adminView.getBookingTbl().setModel(new Rental().getTableModel());
        adminView.getCarTbl().setModel(new Car().getTableModel());
        adminView.getStaffTbl().setModel(new Staff().getTableModel());

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
            actionAddCar();
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

    private void actionAddCar() {
        // TODO: finish implementation
        this.addElement(new CarDialog(adminView, true));
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

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
