/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sujan
 */
public class Utility {

    public static HashMap<String, String> convertToHashMap(Object ob) {
        Class currentClass = ob.getClass();
        Method method = null;
        Field[] fields = currentClass.getDeclaredFields();
        HashMap<String, String> objHashMap = new HashMap<String, String>();
        for (Field f : fields) {

            if (!Modifier.isStatic(f.getModifiers())) {
                // switch(g.get)
                String type = f.getType().getSimpleName();
                //System.out.println("get"+ucFirst(f.getName()));
                try {
                    if (type.equals("String")) {

                        method = currentClass.getMethod("get"+ucFirst(f.getName()), new Class[] {});
                        Object obj = method.invoke(ob, new Object[] {});
                        if (obj != null) {
                            objHashMap.put(f.getName(), obj.toString());
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(currentClass.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return objHashMap;
    }
    
    
    public static Object fill(HashMap<String,String> hashMap,Object ob){
        Class currentClass = ob.getClass();
        Method method = null;
        for(String key:hashMap.keySet()){
                try {
                        method = currentClass.getMethod("set"+ucFirst(key), new Class[] { String.class });
                        method.invoke(ob, new Object[] { hashMap.get(key) });
                   
                } catch (Exception ex) {
                    Logger.getLogger(currentClass.getName()).log(Level.SEVERE, null, ex);
                }
        }
        return ob;
    }

    public static String ucFirst(String str) {
        char[] stringArray = str.toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);
        return new String(stringArray);
    }

}
