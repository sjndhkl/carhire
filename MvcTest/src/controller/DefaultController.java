/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author sujan
 */
public class DefaultController extends AbstractController {
    
    public static final String PERSON_NAME_PROPERTY = "Name";
    public static final String PERSON_ADDRESS_PROPERTY = "Address";
    public static final String PERSON_TELEPHONE_PROPERTY = "PhoneNumber";
    
    public void changeName(String newName){
        setModelProperty(PERSON_NAME_PROPERTY, newName);
    }
    
    public void changeAddress(String newAddress){
        
        setModelProperty(PERSON_ADDRESS_PROPERTY, newAddress);
        
    }
    public void changePhoneNumber(String newPhone){
        
        setModelProperty(PERSON_TELEPHONE_PROPERTY, newPhone);
        
    }
}
