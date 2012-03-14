/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.test;

import java.util.ArrayList;
import java.util.HashMap;
import junit.framework.TestCase;
import oscar.model.CategoryModel;
import oscar.persistance.DbRecord;

/**
 *
 * @author sujan
 */
public class CarHireModelTest extends TestCase {


    public void testFindOneBy(){
        
        CategoryModel model = new CategoryModel();
        HashMap<String,String> record = model.findOneBy("id","1");
        assertEquals("1", record.get("id"));
        
        
    }
    
    public void testFindOneByColumnType(){
        CategoryModel model = new CategoryModel();
        HashMap<String,String> record = model.findOneBy("name","cakephp", DbRecord.ColumnType.STRING);
        assertEquals("cakephp", record.get("name"));
    }
    
    public void testDeleteBy(){
        
        CategoryModel model = new CategoryModel();
        assertEquals(false, model.deleteBy("name", "regular expression", DbRecord.ColumnType.STRING));
        
    }
    
    
}
