package oscar.test;

import java.util.HashMap;
import oscar.model.Branch;
import oscar.MVC.DbRecord;

/**
 *
 * @author sujan
 */
public class CarHireModelTest extends BaseTestCase {

    public void testPkConstructor() {
        Branch branch = new Branch("1");
        System.out.println("Id: " + branch.getBranchId());
        System.out.println("Location: " + branch.getLocation());
        System.out.println("Country: " + branch.getCountry());
    }
    
    public void testFindOneBy() {
        DbRecord model = new DbRecord("branch");
        HashMap<String, String> record = model.findOneBy("branchId", "1");
        assertEquals("1", record.get("branchId"));
    }

    public void testFindOneByColumnType() {
        //DbRecord model = new DbRecord("branch");
        //HashMap<String, String> record = model.findOneBy("country", "Italy", DbRecord.ColumnType.STRING);
        //assertEquals("Italy", record.get("country"));
    }

    public void testDeleteBy() {
        DbRecord model = new DbRecord("branch");
        assertEquals(false, model.deleteBy("country", "regular expression", DbRecord.ColumnType.STRING));
    }

    public void testUpdateDbRecord() {
        DbRecord model = new DbRecord("branch");
        HashMap<String, String> row = model.findOneBy("branchId", "1");
        row.put("location", "new location");
        row.put("country", "italy");
        assertEquals(true, model.updateBy(row, "branchId", "1"));
    }

    public void testAdd() {
        Branch model = new Branch(0, "Test Location Is Cool", "UK");
        assertEquals(true, model.add());
    }

    public void testUpdate() {
        Branch model = new Branch();
        model.setBranchId(1);
        model.setCountry("Italy");
        model.setLocation("Cagliari");
        assertEquals(true, model.update());
    }

    public void testDelete() {
        Branch model = new Branch();
        model.setBranchId(12);
        assertEquals(false, model.delete());
    }
}
