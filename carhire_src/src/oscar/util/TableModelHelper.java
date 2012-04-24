/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.util;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author sujan
 */
public class TableModelHelper {
    
    public static TableModel getTableModel(ArrayList<HashMap<String, String>> map,Object[] displayColumns,Object[] columnNames){
        
        DefaultTableModel model = new DefaultTableModel(displayColumns, 0);
        for (HashMap<String, String> row : map){
        
            Object[] obj = new Object[columnNames.length];
            int i = 0;
            for(Object col:columnNames){
                
                   obj[i] = row.get(col.toString());
                   i++;

            }
            
            model.addRow(obj);
        
        }
        return model;
        
    }
    
}
