
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package oscar.test;

//~--- non-JDK imports --------------------------------------------------------

import oscar.model.Person;

/**
 *
 * @author sujan
 */
public class DependentDbRecordTest extends BaseTestCase {
    public void testAdd() {
        
        Person person = new Person();
        person.setName("Sujan");
        person.setSurname("Dhakal");
        person.setDateOfBirth("2011-1-1");
        person.setPhone("2312312");
        person.setAddress("This is address");
        person.setEmail("sujan@dhakal.com");
        
        /*Staff staff = new Staff();
        staff.setAddress("test@test.com");
        staff.setName("Sujan");
         * 
         */
       // staff.setDependentTable("person");
        
        
        //printHashMap(person.toHashMap());
       // HashMap<String,String> record = new HashMap<String, String>();
        //record.put("email", "test@test.com");
        //Utility.fill(record, person);
        System.out.println(person.addPk());
        //assertEquals(true, person.add() );
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
