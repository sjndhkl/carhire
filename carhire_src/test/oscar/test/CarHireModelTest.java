/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.test;

import java.util.ArrayList;
import java.util.HashMap;
import junit.framework.TestCase;
import oscar.model.BranchModel;
import oscar.persistance.DbRecord;

/**
 *
 * @author sujan
 */
public class CarHireModelTest extends TestCase {


    public void testFindOneBy(){
        
        BranchModel model = new BranchModel();
        HashMap<String,String> record = model.findOneBy("branchId","1");
        assertEquals("1", record.get("branchId"));
        
        
    }
    
    public void testFindOneByColumnType(){
        BranchModel model = new BranchModel();
        HashMap<String,String> record = model.findOneBy("country","UK", DbRecord.ColumnType.STRING);
        assertEquals("UK", record.get("country"));
    }
    
    public void testDeleteBy(){
        
        BranchModel model = new BranchModel();
        assertEquals(false, model.deleteBy("country", "regular expression", DbRecord.ColumnType.STRING));
        
    }
    
    
}
