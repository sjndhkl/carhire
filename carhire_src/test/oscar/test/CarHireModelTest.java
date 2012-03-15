/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.test;

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
        
        DbRecord model = new DbRecord("branch");
        HashMap<String,String> record = model.findOneBy("branchId","1");
        assertEquals("1", record.get("branchId"));
        
        
    }
    
    public void testFindOneByColumnType(){
        DbRecord model = new DbRecord("branch");
        HashMap<String,String> record = model.findOneBy("country","UK", DbRecord.ColumnType.STRING);
        assertEquals("UK", record.get("country"));
    }
    
    public void testDeleteBy(){
        
        DbRecord model = new DbRecord("branch");
        assertEquals(false, model.deleteBy("country", "regular expression", DbRecord.ColumnType.STRING));
        
    }
    
    public void testAdd(){
        BranchModel model = new BranchModel(0, "Location test", "UK");
        assertEquals(true, model.add(model.toHashMap()));
    }
    
    
    public void testUpdate()
    {
        DbRecord model = new DbRecord("branch");
        HashMap<String,String> row = model.findOneBy("branchId", "8");
        row.put("location", "new location");
        row.put("country","italy");
        assertEquals(true, model.updateBy(row, "branchId", "8"));
    }
    
    
}
