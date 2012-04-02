
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package oscar.test;

//~--- non-JDK imports --------------------------------------------------------

import junit.framework.TestCase;

//~--- JDK imports ------------------------------------------------------------

import java.util.HashMap;

/**
 *
 * @author sujan
 */
public class BaseTestCase extends TestCase {
    public void printHashMap(HashMap<String, String> data) {
        for (String key : data.keySet()) {
            System.out.println(key + " : " + data.get(key));
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
