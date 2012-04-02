/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.DefaultController;

/**
 *
 * @author sujan
 */
public class PersonModel extends AbstractModel {
    
    private String name;
    private String address;
    private String phoneNumber;

    public void setPhoneNumber(String phoneNumber) {
        String oldPhone = this.phoneNumber;
        this.phoneNumber = phoneNumber;
        firePropertyChange(DefaultController.PERSON_TELEPHONE_PROPERTY, oldPhone, this.phoneNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void init(){
       setName("Sujan");
       setAddress("Mulpani");
       setPhoneNumber("1231283971923");
    }

    public void setAddress(String address) {
        String oldAddress = this.address;
        this.address = address;
        firePropertyChange(DefaultController.PERSON_ADDRESS_PROPERTY, oldAddress, this.address);
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        firePropertyChange(DefaultController.PERSON_NAME_PROPERTY, oldName, this.name);
    }
    
    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
    
    
    
    
}
