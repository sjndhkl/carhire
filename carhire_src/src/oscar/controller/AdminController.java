package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JComboBox;
import oscar.model.Car;
import oscar.model.CarClass;
import oscar.model.Rental;
import oscar.MVC.Controller;
import oscar.model.OscarComboBoxModelItem;
import oscar.model.Person;
import oscar.view.AdminView;
import oscar.model.Staff;
import oscar.util.TableUpdateTask;
import oscar.util.Utility;
import oscar.view.dialog.ClassDialog;
import oscar.view.dialog.CarDialog;
import oscar.view.dialog.StaffDialog;

/**
 *
 * @author schiodin
 */
public class AdminController extends Controller {

    // Views and dialogs
    private AdminView adminView;
    private StaffDialog staffDialog;
    private CarDialog carDialog;
    private ClassDialog classDialog;
    // Timer to update tables
    private Timer timer;
    // Filters to apply to queries
    private HashMap<String, String> filters;
    // The runnable that update the tables
    private TableUpdateTask tableUpdateTask;
    // dialogs editing variables
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
        tableUpdateTask = new TableUpdateTask(null, null, null);

        // Set up the tables
        updateStaffTbl();
        updateRentalTbl();
        updateCarTbl();
        updateClassTbl();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //car class search
        if(e.getSource().equals(adminView.getCarClassCb())){
            actionCarUpdateTable();
        }
        // Open staff view button
        if (e.getSource().equals(adminView.getOpenStaffBtn())) {
            actionOpenStaff();
        } // Staff Tab
        else if (e.getSource().equals(adminView.getStaffClearBtn())) {
            actionStaffClearFields();
        } else if (e.getSource().equals(adminView.getStaffAddBtn())) {
            actionStaffAdd();
        } // Staff Dialog
        else if (e.getSource().equals(staffDialog.getSaveBtn())) {
            actionStaffDlgSave();
        } else if (e.getSource().equals(staffDialog.getDeleteBtn())) {
            actionStaffDlgDelete();
        } else if (e.getSource().equals(staffDialog.getCancelBtn())) {
            actionStaffDlgCancel();
        } // Rental Tab
        else if (e.getSource().equals(adminView.getRentalClearBtn())) {
            actionRentalClearFields();
        } else if (e.getSource().equals(adminView.getRentalDeleteBtn())) {
            actionRentalDelete();
        } // Car Tab
        else if (e.getSource().equals(adminView.getCarClearBtn())) {
            actionCarClearFields();
        } else if (e.getSource().equals(adminView.getCarAddBtn())) {
            actionCarAdd();
        } // Car Dialog
        else if (e.getSource().equals(carDialog.getSaveBtn())) {
            actionCarDlgSave();
        } else if (e.getSource().equals(carDialog.getDeleteBtn())) {
            actionCarDlgDelete();
        } else if (e.getSource().equals(carDialog.getCancelBtn())) {
            actionCarDlgCancel();
        } // Class tab
        else if (e.getSource().equals(adminView.getCarClassAddBtn())) {
            actionClassAdd();
        } else if (e.getSource().equals(adminView.getCarClassClearBtn())) {
            actionClassClearFields();
        } // Class dialog
        else if (e.getSource().equals(classDialog.getSaveBtn())) {
            actionClassDlgSave();
        } else if (e.getSource().equals(classDialog.getDeleteBtn())) {
            actionClassDlgDelete();
        } else if (e.getSource().equals(classDialog.getCancelBtn())) {
            actionClassDlgCancel();
        }
    }

    private void actionOpenStaff() {
        new StaffController(true).start();
    }

    /*******************************************************************************
     *                          STAFF TAB
     *******************************************************************************/
    private void actionStaffClearFields() {
        //clear fields and reset the table records
        adminView.getStaffNameTxt().setText("");
        adminView.getStaffSurnameTxt().setText("");
        actionStaffUpdateTable();
    }

    private void actionStaffAdd() {
        // Disable the delete button and show the dialog to add a record
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

        //if we are editing a record
        if (editingStaff) {
            try {
                staff.updateDependentBy(Utility.convertToHashMapWithParent(staff), "personId", editingStaffId);
                actionStaffDlgCancel();
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } //else, if we are creating a new record
        else {
            try {
                // TODO: implement failure
                staff.addDependent();
                actionStaffDlgCancel();
            } catch (SQLException ex) {
                //TODO: handle error
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        updateStaffTbl();
    }

    private void actionStaffDlgCancel() {
        // resets all fields, close the dialog and set editing mode to false
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
    private void actionRentalDelete() {
        int selectedRow = adminView.getRentalTbl().getSelectedRow();
        String rentalId = (String) adminView.getRentalTbl().getValueAt(selectedRow, 0);
        new Rental().deleteBy("rentalId", rentalId);
        updateRentalTbl();
    }

    private void actionRentalClearFields() {
        // Make a new staff out of dialog fields
        adminView.getRentalRefCodeTxt().setText("");
        adminView.getRentalSurnameTxt().setText("");
        actionRentalUpdateTable();
    }

    /*******************************************************************************
     *                          CAR TAB
     *******************************************************************************/
    private void actionCarClearFields() {
        // Make a new staff out of dialog fields
        adminView.getCarBrandTxt().setText("");
        adminView.getCarClassCb().setSelectedIndex(0);
        adminView.getCarColorTxt().setText("");
        adminView.getCarModelTxt().setText("");
        adminView.getCarPlateTxt().setText("");
        adminView.getCarStatusCB().setSelectedIndex(0);
        adminView.getCarYearTxt().setText("");
        actionRentalUpdateTable();
    }

    private void actionCarAdd() {
        // Disable the delete button and show the dialog to add a record
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

        //if we are editing a record
        if (editingCar) {
            car.updateBy(Utility.convertToHashMap(car), "Plate", editingCarId);
        } else { //else, if we are creating a new record
            car.add();
        }
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
        // resets all fields, close the dialog and set editing mode to false
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
        // Disable the delete button and show the dialog to add a record
        classDialog.getDeleteBtn().setEnabled(false);
        classDialog.setVisible(true);
    }

    private void actionClassClearFields() {
        // Make a new staff out of dialog fields
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

        //if we are editing a record
        if (editingClass) {
            carClass.updateBy(Utility.convertToHashMap(carClass), "classId", editingClassId);
        } else { //else, if we are creating a new record
            carClass.add();
        }
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
        // resets all fields, close the dialog and set editing mode to false
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
    public void keyReleased(KeyEvent e) {
        // Staff
        if (e.getSource().equals(adminView.getStaffNameTxt())
                || e.getSource().equals(adminView.getStaffSurnameTxt())) {
            actionStaffUpdateTable();
        } // Rental
        else if (e.getSource().equals(adminView.getRentalRefCodeTxt())
                || e.getSource().equals(adminView.getRentalSurnameTxt())) {
            actionRentalUpdateTable();
        } // Rental
        else if (e.getSource().equals(adminView.getCarBrandTxt())
                || e.getSource().equals(adminView.getCarClassCb())
                || e.getSource().equals(adminView.getCarColorTxt())
                || e.getSource().equals(adminView.getCarModelTxt())
                || e.getSource().equals(adminView.getCarYearTxt())
                || e.getSource().equals(adminView.getCarPlateTxt())) {
            actionCarUpdateTable();
        } // Class
        else if (e.getSource().equals(adminView.getCarClassDisplayTxt())
                || e.getSource().equals(adminView.getCarClassNameTxt())) {
            actionClassUpdateTable();
        }
    }

    private void actionStaffUpdateTable() {
        filters = new HashMap<String, String>();

        if (!adminView.getStaffNameTxt().getText().isEmpty()) {
            filters.put("name", adminView.getStaffNameTxt().getText());
        }
        if (!adminView.getStaffSurnameTxt().getText().isEmpty()) {
            filters.put("surname", adminView.getStaffSurnameTxt().getText());
        }
        if (!filters.isEmpty()) {
            tableUpdateTask.cancel();
            tableUpdateTask = new TableUpdateTask(adminView.getStaffTbl(), filters, new Staff());
            timer.schedule(tableUpdateTask, this.TABLE_FILTERING_DELAY);
        } else {
            updateStaffTbl();
        }
    }

    private void actionRentalUpdateTable() {
        filters = new HashMap<String, String>();

        if (!adminView.getRentalRefCodeTxt().getText().isEmpty()) {
            filters.put("referenceCode", adminView.getRentalRefCodeTxt().getText());
        }
        if (!adminView.getRentalSurnameTxt().getText().isEmpty()) {
            filters.put("surname", adminView.getRentalSurnameTxt().getText());
        }
        if (!filters.isEmpty()) {
            tableUpdateTask.cancel();
            tableUpdateTask = new TableUpdateTask(adminView.getRentalTbl(), filters, new Rental());
            timer.schedule(tableUpdateTask, this.TABLE_FILTERING_DELAY);
        } else {
            updateRentalTbl();
        }
    }

    private void actionClassUpdateTable() {
        filters = new HashMap<String, String>();

        if (!adminView.getCarClassDisplayTxt().getText().isEmpty()) {
            filters.put("displayName", adminView.getCarClassDisplayTxt().getText());
        }
        if (!adminView.getCarClassNameTxt().getText().isEmpty()) {
            filters.put("name", adminView.getCarClassNameTxt().getText());
        }
        if (!filters.isEmpty()) {
            tableUpdateTask.cancel();
            tableUpdateTask = new TableUpdateTask(adminView.getClassTbl(), filters, new CarClass());
            timer.schedule(tableUpdateTask, this.TABLE_FILTERING_DELAY);
        } else {
            updateClassTbl();
        }
    }

    private void actionCarUpdateTable() {
        filters = new HashMap<String, String>();

        if (!adminView.getCarPlateTxt().getText().isEmpty()) {
            filters.put("plate", adminView.getCarPlateTxt().getText());
        }
        if (!adminView.getCarBrandTxt().getText().isEmpty()) {
            filters.put("brand", adminView.getCarBrandTxt().getText());
        }
        if (!adminView.getCarModelTxt().getText().isEmpty()) {
            filters.put("model", adminView.getCarModelTxt().getText());
        }
        if (!adminView.getCarYearTxt().getText().isEmpty()) {
            filters.put("year", adminView.getCarYearTxt().getText());
        }
        if (adminView.getCarClassCb().getSelectedIndex() != 0) {
             OscarComboBoxModelItem item = (OscarComboBoxModelItem) adminView.getCarClassCb().getSelectedItem();
            filters.put("classId", Integer.toString(item.Id));
        }
        if (!adminView.getCarColorTxt().getText().isEmpty()) {
            filters.put("color", adminView.getCarColorTxt().getText());
        }
        if (!filters.isEmpty()) {
            tableUpdateTask.cancel();
            tableUpdateTask = new TableUpdateTask(adminView.getCarTbl(), filters, new Car());
            timer.schedule(tableUpdateTask, this.TABLE_FILTERING_DELAY);
        } else {
            updateCarTbl();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Staff table
        if (e.getSource().equals(adminView.getStaffTbl())) {
            actionStaffSelectRow(adminView.getStaffTbl().getSelectedRow());
        }
        // car table
        if (e.getSource().equals(adminView.getCarTbl())) {
            actionCarSelectRow(adminView.getCarTbl().getSelectedRow());
        }
        // class table
        if (e.getSource().equals(adminView.getClassTbl())) {
            actionClassSelectRow(adminView.getClassTbl().getSelectedRow());
        }
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
        // Set car editing mode true
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
        // Set class editing mode true
        editingClass = true;
        HashMap<String, String> classRecord = new CarClass().findByPK(editingClassId);

        classDialog.getDisplayNameTxt().setText(classRecord.get("displayName"));
        classDialog.getNameTxt().setText(classRecord.get("name"));
        classDialog.getDescriptionTA().setText(classRecord.get("description"));
        classDialog.getPriceTxt().setText(classRecord.get("price"));

        // shows the dialog
        classDialog.setVisible(true);
    }

    private void updateStaffTbl() {
        adminView.getStaffTbl().setModel(new Staff().getTableModel());
    }

    private void updateRentalTbl() {
        adminView.getRentalTbl().setModel(new Rental().getTableModel());
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
        this.addElement(adminView.getOpenStaffBtn());
        // Staff tab
        this.addElement(adminView.getStaffClearBtn());
        this.addElement(adminView.getStaffAddBtn());
        this.addElement(adminView.getStaffSurnameTxt());
        this.addElement(adminView.getStaffNameTxt());
        this.addElement(adminView.getStaffTbl());
        // Rental tab
        this.addElement(adminView.getRentalRefCodeTxt());
        this.addElement(adminView.getRentalSurnameTxt());
        this.addElement(adminView.getRentalClearBtn());
        this.addElement(adminView.getRentalDeleteBtn());
        this.addElement(adminView.getRentalTbl());
        // Car tab
        adminView.getCarClassCb().setModel(new CarClass().getComboModel("displayName", "----"));
        adminView.getCarClassCb().setSelectedIndex(0);
         
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

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
