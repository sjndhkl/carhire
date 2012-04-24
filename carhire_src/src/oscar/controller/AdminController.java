package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import oscar.model.Car;
import oscar.model.CarClass;
import oscar.model.Rental;
import oscar.MVC.Controller;
import oscar.model.Person;
import oscar.view.AdminView;
import oscar.model.Staff;
import oscar.task.StaffUpdateTask;
import oscar.task.BookingUpdateTask;
import oscar.task.CarUpdateTask;
import oscar.task.ClassUpdateTask;
import oscar.util.Utility;
import oscar.view.dialog.ClassDialog;
import oscar.view.dialog.CarDialog;
import oscar.view.dialog.StaffDialog;

/**
 *
 * @author schiodin
 */
public class AdminController extends Controller {

    private AdminView adminView;
    private StaffDialog staffDialog;
    private CarDialog carDialog;
    private ClassDialog classDialog;
    private Timer timer;
    private StaffUpdateTask staffUpdateTask;
    private BookingUpdateTask bookingUpdateTask;
    private CarUpdateTask carUpdateTask;
    private ClassUpdateTask classUpdateTask;
    // dialog editing variables
    private boolean editingStaff = false;
    private String editingStaffId;
    private boolean editingCar = false;
    private String editingCarId;
    private boolean editingClass = false;
    private String editingClassId;

    @Override
    public void run() {
        this.setName("Admin");

        // init views and their components
        initAdminView();
        initStaffDialog();
        initCarDialog();
        initClassDialog();

        // Filter timers
        timer = new Timer();
        staffUpdateTask = new StaffUpdateTask();
        bookingUpdateTask = new BookingUpdateTask();
        carUpdateTask = new CarUpdateTask();
        classUpdateTask = new ClassUpdateTask();

        // Set up the tables
        updateStaffTbl();
        updateBookingTbl();
        updateCarTbl();
        updateClassTbl();
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
        // Car Dialog
        else if (e.getSource().equals(carDialog.getSaveBtn()))
            actionCarDlgSave();
        else if (e.getSource().equals(carDialog.getDeleteBtn()))
            actionCarDlgDelete();
        else if (e.getSource().equals(carDialog.getCancelBtn()))
            actionCarDlgCancel();
        // Class tab
        else if (e.getSource().equals(adminView.getCarClassAddBtn()))
            actionClassAdd();
        else if (e.getSource().equals(adminView.getCarClassClearBtn()))
            actionClassClearFields();
        // Class dialog
        else if (e.getSource().equals(classDialog.getSaveBtn()))
            actionClassDlgSave();
        else if (e.getSource().equals(classDialog.getDeleteBtn()))
            actionClassDlgDelete();
        else if (e.getSource().equals(classDialog.getCancelBtn()))
            actionClassDlgCancel();
    }

    private void actionLogout() {
        this.removeAllElement();
        new LoginController().start();
    }

    /*******************************************************************************
     *                          STAFF TAB
     *******************************************************************************/
    private void actionStaffClearFields() {
        adminView.getStaffNameTxt().setText("");
        adminView.getStaffSurnameTxt().setText("");
        actionStaffUpdateTable();
    }

    private void actionStaffAdd() {
        staffDialog.getDeleteBtn().setEnabled(false);
        staffDialog.setVisible(true);
    }

    /*******************************************************************************
     *                          STAFF DIALOG
     *******************************************************************************/
    private void actionStaffDlgSave() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Make a new staff out of dialog fields
        Staff staff = new Staff(
                staffDialog.getNameTxt().getText(),
                staffDialog.getSurnameTxt().getText(),
                dateFormat.format(staffDialog.getDateOfBirthDP().getDate()),
                staffDialog.getEmailTxt().getText(),
                staffDialog.getAddressTA().getText(),
                staffDialog.getPhoneTxt().getText(),
                staffDialog.getUsernameTxt().getText(),
                new String(staffDialog.getPasswordPwd().getPassword()),
                staffDialog.getAdminCB().isSelected(),
                staffDialog.getChauffeurCB().isSelected());

        if (editingStaff)
            try {
                staff.updateDependentBy(Utility.convertToHashMapWithParent(staff), "personId", editingStaffId);
                actionStaffDlgCancel();
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        else
            try {
                // TODO: implement failure
                staff.addDependent();
                actionStaffDlgCancel();
            } catch (SQLException ex) {
                //TODO: handle error
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        updateStaffTbl();
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
        staffDialog.getAdminCB().setSelected(false);
        staffDialog.getChauffeurCB().setSelected(false);
        staffDialog.getDeleteBtn().setEnabled(true);
        staffDialog.setVisible(false);
        editingStaff = false;
    }

    private void actionStaffDlgDelete() {
        Staff staff = new Staff();
        staff.deleteBy("personId", editingStaffId);
        actionStaffDlgCancel();
        updateStaffTbl();
    }

    /*******************************************************************************
     *                          BOOKING TAB
     *******************************************************************************/
    private void actionBookingDelete() {
        int selectedRow = adminView.getBookingTbl().getSelectedRow();
        String bookingId = (String) adminView.getBookingTbl().getValueAt(selectedRow, 0);
        new Rental().deleteBy("rentalId", bookingId);
        updateBookingTbl();
    }

    private void actionBookingClearFields() {
        adminView.getBookingRefCodeTxt().setText("");
        adminView.getBookingSurnameTxt().setText("");
        actionBookingUpdateTable();
    }

    /*******************************************************************************
     *                          CAR TAB
     *******************************************************************************/
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
        carDialog.getDeleteBtn().setEnabled(false);
        carDialog.setVisible(true);
    }

    /*******************************************************************************
     *                          CAR DIALOG
     *******************************************************************************/
    private void actionCarDlgSave() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Car car = new Car(carDialog.getPlateTxt().getText(),
                carDialog.getBrandTxt().getText(),
                carDialog.getModelTxt().getText(),
                Integer.parseInt(carDialog.getYearTxt().getText()),
                Integer.parseInt(carDialog.getMilieageTxt().getText()),
                Integer.parseInt(carDialog.getLastServiceMileageTxt().getText()),
                dateFormat.format(carDialog.getLastServiceDP().getDate()),
                carDialog.getClassCB().getSelectedIndex(),
                carDialog.getColorTxt().getText(),
                carDialog.getBranchCB().getSelectedIndex(),
                carDialog.getServiceMilesTxt().getText(),
                Integer.parseInt(carDialog.getServiceMonthTxt().getText())/*,
                Car.CarStatus.values()[carDialog.getStatusCB().getSelectedIndex()]*/);


        if (editingCar)
            car.updateBy(Utility.convertToHashMap(car), "Plate", editingCarId);
        else
            car.add();
        actionCarDlgCancel();
        updateCarTbl();
    }

    private void actionCarDlgDelete() {
        Car car = new Car();
        car.deleteBy("plate", editingCarId);
        actionCarDlgCancel();
        updateCarTbl();
    }

    private void actionCarDlgCancel() {
        carDialog.getBranchCB().setSelectedIndex(0);
        carDialog.getBrandTxt().setText("");
        carDialog.getClassCB().setSelectedIndex(0);
        carDialog.getColorTxt().setText("");
        carDialog.getLastServiceDP().setDate(null);
        carDialog.getLastServiceMileageTxt().setText("");
        carDialog.getMilieageTxt().setText("");
        carDialog.getModelTxt().setText("");
        carDialog.getPlateTxt().setText("");
        carDialog.getServiceMilesTxt().setText("");
        carDialog.getServiceMonthTxt().setText("");
        carDialog.getStatusCB().setSelectedIndex(0);
        carDialog.getYearTxt().setText("");
        carDialog.getDeleteBtn().setEnabled(true);
        carDialog.setVisible(false);
        editingCar = false;
    }

    /*******************************************************************************
     *                          CLASS TAB
     *******************************************************************************/
    private void actionClassAdd() {
        classDialog.getDeleteBtn().setEnabled(false);
        classDialog.setVisible(true);
    }

    private void actionClassClearFields() {
        adminView.getCarClassDisplayTxt().setText("");
        adminView.getCarClassNameTxt().setText("");
        actionClassUpdateTable();
    }

    /*******************************************************************************
     *                          CLASS DIALOG
     *******************************************************************************/
    private void actionClassDlgSave() {
        CarClass carClass = new CarClass(
                classDialog.getNameTxt().getText(),
                classDialog.getDisplayNameTxt().getText(),
                classDialog.getDescriptionTA().getText(),
                Float.parseFloat(classDialog.getPriceTxt().getText()));

        if (editingClass)
            carClass.updateBy(Utility.convertToHashMap(carClass), "classId", editingClassId);
        else
            carClass.add();
        actionClassDlgCancel();
        updateClassTbl();
    }

    private void actionClassDlgDelete() {
        CarClass carClass = new CarClass();
        carClass.deleteBy("classId", editingClassId);
        actionClassDlgCancel();
        updateClassTbl();
    }

    private void actionClassDlgCancel() {
        classDialog.getNameTxt().setText("");
        classDialog.getDisplayNameTxt().setText("");
        classDialog.getDescriptionTA().setText("");
        classDialog.getPriceTxt().setText("");
        classDialog.getDeleteBtn().setEnabled(true);
        actionClassUpdateTable();
        classDialog.setVisible(false);
        editingClass = false;
    }
    /*
     * Handle Text Field Listeners calling specific actions
     */

    @Override
    public void keyTyped(KeyEvent e) {
        // Staff
        if (e.getSource().equals(adminView.getStaffNameTxt())
                || e.getSource().equals(adminView.getStaffSurnameTxt()))
            actionStaffUpdateTable(); // Booking
        else if (e.getSource().equals(adminView.getBookingRefCodeTxt())
                || e.getSource().equals(adminView.getBookingSurnameTxt()))
            actionBookingUpdateTable(); // Class
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
    public void mouseClicked(MouseEvent e) {
        // Staff table
        if (e.getSource().equals(adminView.getStaffTbl()))
            actionStaffSelectRow(adminView.getStaffTbl().getSelectedRow());
        // car table
        if (e.getSource().equals(adminView.getCarTbl()))
            actionCarSelectRow(adminView.getCarTbl().getSelectedRow());
        // class table
        if (e.getSource().equals(adminView.getClassTbl()))
            actionClassSelectRow(adminView.getClassTbl().getSelectedRow());
    }

    private void actionStaffSelectRow(int selectedRow) {
        editingStaffId = (String) adminView.getStaffTbl().getValueAt(selectedRow, 0);
        // Set staff editing mode true
        editingStaff = true;
        HashMap<String, String> staffRecord = new Staff().findByPK(editingStaffId);
        HashMap<String, String> personRecord = new Person().findByPK(editingStaffId);

        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        staffDialog.getAddressTA().setText(personRecord.get("address"));
        try {
            staffDialog.getDateOfBirthDP().setDate(df.parse(personRecord.get("dateOfBirth")));
        } catch (ParseException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        staffDialog.getEmailTxt().setText(personRecord.get("email"));
        staffDialog.getNameTxt().setText(personRecord.get("name"));
        staffDialog.getSurnameTxt().setText(personRecord.get("surname"));
        //staffDialog.getPasswordPwd().setText(record.get("password"));
        staffDialog.getPasswordPwd().setText("");
        staffDialog.getUsernameTxt().setText(staffRecord.get("username"));
        staffDialog.getPhoneTxt().setText(personRecord.get("phone"));
        staffDialog.getAdminCB().setSelected((staffRecord.get("admin").contains("1")) ? true : false);
        staffDialog.getChauffeurCB().setSelected((staffRecord.get("chauffeur").contains("1")) ? true : false);
        // shows the dialog
        staffDialog.setVisible(true);
    }

    private void actionCarSelectRow(int selectedRow) {
        editingCarId = (String) adminView.getCarTbl().getValueAt(selectedRow, 0);
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        // Set staff editing mode true
        editingCar = true;
        HashMap<String, String> carRecord = new Car().findByPK(editingCarId);

        carDialog.getPlateTxt().setText(carRecord.get("plate"));
        carDialog.getBrandTxt().setText(carRecord.get("brand"));
        carDialog.getModelTxt().setText(carRecord.get("model"));
        carDialog.getClassCB().setSelectedIndex(Integer.parseInt(carRecord.get("classId")));
        carDialog.getYearTxt().setText(carRecord.get("year"));
        carDialog.getColorTxt().setText(carRecord.get("color"));
        carDialog.getMilieageTxt().setText(carRecord.get("mileage"));
        carDialog.getLastServiceMileageTxt().setText(carRecord.get("lastServiceMiles"));
        try {
            carDialog.getLastServiceDP().setDate(df.parse(carRecord.get("lastServiceDate")));
        } catch (ParseException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        carDialog.getBranchCB().setSelectedIndex(Integer.parseInt(carRecord.get("branch")));
        carDialog.getServiceMonthTxt().setText(carRecord.get("serviceMonths"));
        carDialog.getServiceMilesTxt().setText(carRecord.get("serviceMiles"));

        // shows the dialog
        carDialog.setVisible(true);
    }

    private void actionClassSelectRow(int selectedRow) {
        editingClassId = (String) adminView.getClassTbl().getValueAt(selectedRow, 0);
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        // Set staff editing mode true
        editingClass = true;
        HashMap<String, String> classRecord = new CarClass().findByPK(editingClassId);

        classDialog.getDisplayNameTxt().setText(classRecord.get("displayName"));
        classDialog.getNameTxt().setText(classRecord.get("name"));
        classDialog.getDescriptionTA().setText(classRecord.get("description"));
        classDialog.getPriceTxt().setText(classRecord.get("price"));

        // shows the dialog
        classDialog.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void updateStaffTbl() {
        adminView.getStaffTbl().setModel(new Staff().getTableModel());
    }

    private void updateBookingTbl() {
        adminView.getBookingTbl().setModel(new Rental().getTableModel());
    }

    private void updateCarTbl() {
        adminView.getCarTbl().setModel(new Car().getTableModel());
    }

    private void updateClassTbl() {
        adminView.getClassTbl().setModel(new CarClass().getTableModel());
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
        this.addElement(adminView.getStaffTbl());
        // Booking tab
        this.addElement(adminView.getBookingRefCodeTxt());
        this.addElement(adminView.getBookingSurnameTxt());
        this.addElement(adminView.getBookingClearBtn());
        this.addElement(adminView.getBookingDeleteBtn());
        this.addElement(adminView.getBookingTbl());
        // Car tab
        this.addElement(adminView.getCarAddBtn());
        this.addElement(adminView.getCarClearBtn());
        this.addElement(adminView.getCarBrandTxt());
        this.addElement(adminView.getCarClassCb());
        this.addElement(adminView.getCarColorTxt());
        this.addElement(adminView.getCarModelTxt());
        this.addElement(adminView.getCarPlateTxt());
        this.addElement(adminView.getCarStatusCB());
        this.addElement(adminView.getCarTbl());
        // Class tab
        this.addElement(adminView.getCarClassAddBtn());
        this.addElement(adminView.getCarClassClearBtn());
        this.addElement(adminView.getCarClassDisplayTxt());
        this.addElement(adminView.getCarClassNameTxt());
        this.addElement(adminView.getClassTbl());
    }

    private void initStaffDialog() {

        staffDialog = new StaffDialog(adminView, true);
        this.addElement(staffDialog);
        // Staff dialog
        this.addElement(staffDialog.getSaveBtn());
        this.addElement(staffDialog.getDeleteBtn());
        this.addElement(staffDialog.getCancelBtn());
    }

    private void initCarDialog() {

        carDialog = new CarDialog(adminView, true);
        this.addElement(carDialog);
        // Staff dialog
        this.addElement(carDialog.getSaveBtn());
        this.addElement(carDialog.getDeleteBtn());
        this.addElement(carDialog.getCancelBtn());
    }

    private void initClassDialog() {
        classDialog = new ClassDialog(adminView, true);
        this.addElement(classDialog);
        // Staff dialog
        this.addElement(classDialog.getSaveBtn());
        this.addElement(classDialog.getDeleteBtn());
        this.addElement(classDialog.getCancelBtn());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
