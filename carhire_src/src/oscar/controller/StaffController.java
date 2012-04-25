package oscar.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicMenuUI.ChangeHandler;
import oscar.MVC.Controller;
import oscar.model.CarClass;
import oscar.model.OscarComboBoxModelItem;
import oscar.model.Person;
import oscar.task.HirePersonUpdateTask;
import oscar.util.TableModelHelper;
import oscar.view.StaffView;

/**
 *
 * @author Draga
 */
public class StaffController extends Controller {

    private StaffView staffView;
    /*// weather the controller is attached to the admin one
    private boolean passive = false;*/
    private HirePersonUpdateTask hirePersonUpdateTask;
    private Timer timer;
    
    
    private boolean lastRequest = false;
    private boolean foundAndFilled = false;
    private int personId;
    private boolean hasParent = false;

    StaffController() {
        super();
    }

    StaffController(boolean hasParent) {
        this.hasParent = hasParent;
    }

    @Override
    public void run() {
        this.setName("Staff");
        staffView = new StaffView();
        this.addElement(staffView);
        if(hasParent)
            staffView.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        // Hire tab
        staffView.getHireClassCB().setModel(new CarClass().getComboModel("displayName","---- select car class ---"));
        staffView.getHireClassCB().setSelectedIndex(0);
        
        this.addElement(staffView.getHireAddressTxt());
        this.addElement(staffView.getHireBtn());
        this.addElement(staffView.getHireChauffeuredCB());
        this.addElement(staffView.getHireClassCB());
        this.addElement(staffView.getHireClearBtn());
        this.addElement(staffView.getHireDateOfBirthDP());
        this.addElement(staffView.getHireEmailTxt());
        this.addElement(staffView.getHireFromDP());
        this.addElement(staffView.getHireInsuranceCB());
        this.addElement(staffView.getHireNameTxt());
        this.addElement(staffView.getHirePersonLoadBtn());
        this.addElement(staffView.getHirePhoneTxt());
        this.addElement(staffView.getHireRefCodeSearchBtn());
        this.addElement(staffView.getHireRefCodeTxt());
        this.addElement(staffView.getHireSurnameTxt());
        this.addElement(staffView.getHireToDP());
        
        /**
         * Action Listener for the Combobox
         * When someone selects a CarClass the cars in the selected carclass would appear
         */
        staffView.getHireClassCB().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                OscarComboBoxModelItem item = (OscarComboBoxModelItem) ((JComboBox)e.getSource()).getSelectedItem();
                if(item.Id>0){
                    ArrayList<HashMap<String,String>> data = new CarClass().getCars(item.Id);
                    if(data != null){
                        staffView.getHireTbl().setModel(TableModelHelper.getTableModel(data, new Object[]{"Plate Number","Brand","Model"}, new Object[]{"plate","brand","model"}));
                    }
                }else{
                    //clear
                }
                
            }
        });


        timer = new Timer();
        hirePersonUpdateTask = new HirePersonUpdateTask();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(staffView.getHireBtn())) {
            actionHire();
        } else if (e.getSource().equals(staffView.getHireClearBtn())) {
            actionHireClearFields();
        } else if (e.getSource().equals(staffView.getHirePersonLoadBtn())) {
            actionHireLoadPerson();
        } else if (e.getSource().equals(staffView.getHireRefCodeSearchBtn())) {
            actionHireSearchRefCode();
        }
    }

    private void actionLogout() {
        new LoginController().start();
        this.removeAllElement();
    }

    private void actionHire() {
        //throw new UnsupportedOperationException("Not yet implemented");
        System.out.println(this.personId);
        OscarComboBoxModelItem item = (OscarComboBoxModelItem) this.staffView.getHireClassCB().getSelectedItem();
        
        System.out.println(item.Id);
    }

    private void actionHireSearchRefCode() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void actionHireLoadPerson() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void actionHireClearFields() {
        staffView.getHireChauffeuredCB().setSelected(false);
        staffView.getHireClassCB().setSelectedIndex(0);
        staffView.getHireInsuranceCB().setSelected(false);
        staffView.getHireDateOfBirthDP().setDate(null);
        staffView.getHireFromDP().setDate(null);
        staffView.getHireToDP().setDate(null);
        staffView.getHireAddressTxt().setText("");
        staffView.getHireEmailTxt().setText("");
        staffView.getHireNameTxt().setText("");
        staffView.getHirePhoneTxt().setText("");
        staffView.getHireRefCodeTxt().setText("");
        staffView.getHireSurnameTxt().setText("");
        this.setEditablePersonFields(true);
        staffView.getHireNameTxt().requestFocusInWindow();
        this.lastRequest = false;
        this.foundAndFilled = false;
        this.personId = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (!staffView.getSearchModeCheckBox().isSelected()) {
            return;
        }

        if (this.lastRequest == false && !foundAndFilled) {
            //find the record
            this.lastRequest = true;
            ArrayList<HashMap<String, String>> records = new Person().findAllLike(this.getColumns());
            if (records != null) {
                if (records.size() == 1) {
                    this.populateFields(records.get(0));
                } else {
                    this.setButtonStatus(false);
                }
            } else {
                System.out.println("No records found");
            }
            this.lastRequest = false;

        } else {
            try {
                Thread.sleep(TABLE_FILTERING_DELAY);
            } catch (InterruptedException ex) {
                Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setEditablePersonFields(boolean status) {
        staffView.getHireNameTxt().setEnabled(status);
        staffView.getHireSurnameTxt().setEnabled(status);
        staffView.getHireEmailTxt().setEnabled(status);
        staffView.getHireDateOfBirthDP().setEnabled(status);
        staffView.getHireAddressTxt().setEnabled(status);
        staffView.getHirePhoneTxt().setEnabled(status);
        this.setButtonStatus(!status);
    }
    
    private void setButtonStatus(boolean status){
        
       org.jdesktop.application.ResourceMap resourceMap =  org.jdesktop.application.Application.getInstance().getContext().getResourceMap(StaffView.class);
        
        if(status){
            staffView.getHirePersonLoadBtn().setIcon(resourceMap.getIcon("hirePersonLoadBtnActive.icon"));
        }else{
            staffView.getHirePersonLoadBtn().setIcon(resourceMap.getIcon("hirePersonLoadBtn.icon"));
        }
        staffView.getHirePersonLoadBtn().setEnabled(status);
        
    }

    private void populateFields(HashMap<String, String> data) {

        this.personId = Integer.parseInt(data.get("personId"));
        staffView.getHireNameTxt().setText(data.get("name"));
        staffView.getHireSurnameTxt().setText(data.get("surname"));

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = df.parse(data.get("dateOfBirth"));
            staffView.getHireDateOfBirthDP().setDate(dateOfBirth);

        } catch (Exception e) {
            e.printStackTrace();
        }
        staffView.getHireEmailTxt().setText(data.get("email"));

        staffView.getHireAddressTxt().setText(data.get("address"));

        staffView.getHirePhoneTxt().setText(data.get("phone"));

        foundAndFilled = true; //to stop the searching process
        this.setEditablePersonFields(false);

    }

    private HashMap<String, String> getColumns() {
        HashMap<String, String> columns = new HashMap<String, String>();
        if (!staffView.getHireNameTxt().getText().equals("")) {
            columns.put("name", staffView.getHireNameTxt().getText());
        }
        if (!staffView.getHireSurnameTxt().getText().equals("")) {
            columns.put("surname", staffView.getHireSurnameTxt().getText());
        }

        Date birthDate = staffView.getHireDateOfBirthDP().getDate();
        if (birthDate != null) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            columns.put("dateOfBirth", df.format(birthDate));
        }

        if (!staffView.getHireEmailTxt().getText().equals("")) {
            columns.put("email", staffView.getHireAddressTxt().getText());
        }
        if (!staffView.getHireAddressTxt().getText().equals("")) {
            columns.put("address", staffView.getHireAddressTxt().getText());
        }
        if (!staffView.getHirePhoneTxt().getText().equals("")) {
            columns.put("phone", staffView.getHirePhoneTxt().getText());
        }
        return columns;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
