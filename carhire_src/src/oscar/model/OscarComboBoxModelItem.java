/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.model;

/**
 *
 * @author sujan
 */
public class OscarComboBoxModelItem {
    
    /**
     * 
     */
    public int Id;
    /**
     * 
     */
    public String DisplayValue;
    
    /**
     * 
     * @param id
     * @param displayValue
     */
    public OscarComboBoxModelItem(int id,String displayValue){
        this.Id = id;
        this.DisplayValue = displayValue;
    }
    
    @Override
    public String toString(){
        return DisplayValue;
    }
    
}
