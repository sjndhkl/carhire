/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.test;

import java.util.HashMap;
import junit.framework.TestCase;
import oscar.model.Branch;
import oscar.persistance.DbRecord;

/**
 *
 * @author sujan
 */
public class CarHireModelTest extends TestCase {

    public void testFindOneBy() {

        DbRecord model = new DbRecord("branch");
        HashMap<String, String> record = model.findOneBy("branchId", "1");
        assertEquals("1", record.get("branchId"));


    }

    public void testFindOneByColumnType() {
        DbRecord model = new DbRecord("branch");
        HashMap<String, String> record = model.findOneBy("country", "UK", DbRecord.ColumnType.STRING);
        assertEquals("UK", record.get("country"));
    }

    public void testDeleteBy() {

        DbRecord model = new DbRecord("branch");
        assertEquals(false, model.deleteBy("country", "regular expression", DbRecord.ColumnType.STRING));

    }

    public void testUpdateDbRecord() {
        DbRecord model = new DbRecord("branch");
        HashMap<String, String> row = model.findOneBy("branchId", "8");
        row.put("location", "new location");
        row.put("country", "italy");
        assertEquals(true, model.updateBy(row, "branchId", "8"));
    }

    public void testAdd() {
        Branch model = new Branch(0, "Test Location Is Cool", "UK");
        assertEquals(true, model.add());
    }

    public void testUpdate() {
        Branch model = new Branch();
        model.setBranchId(10);
        model.setCountry("Italy");
        assertEquals(true, model.update());
    }

    public void testDelete() {
        Branch model = new Branch();
        model.setBranchId(12);
        assertEquals(false, model.delete());
    }
}
