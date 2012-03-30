/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.test;
import oscar.model.Person;

/**
 *
 * @author sujan
 */
public class DependentDbRecordTest extends BaseTestCase {
    
    public void testAdd(){
        
        Person person = new Person();
        person.setAddress("This is address");
        person.setEmail("sujan@dhakal.com");
        printHashMap(person.toHashMap());
        assertEquals("sujan@dhakal.com", person.toHashMap().get("email"));
        
    }
    
}
