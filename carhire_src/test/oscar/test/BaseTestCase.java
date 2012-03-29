/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.test;

import java.util.HashMap;
import junit.framework.TestCase;

/**
 *
 * @author sujan
 */
public class BaseTestCase extends TestCase {
    
    public void printHashMap(HashMap<String,String> data){
        for(String key:data.keySet()){
            System.out.println(key+" : "+data.get(key));
        }
    }
    
}
