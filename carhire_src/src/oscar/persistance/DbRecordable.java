/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.persistance;

import java.util.HashMap;

/**
 *
 * @author sujan
 */
public interface DbRecordable {
    boolean add();
    boolean delete();
    boolean update();
    HashMap<String, String> toHashMap();
    Object toObject(HashMap<String, String> hashMap);
}
