/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.model;

import java.util.HashMap;
import java.util.Set;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author sujan
 */
public class OscarComboBoxModel<Integer,String> extends AbstractListModel implements ComboBoxModel {
    
    private HashMap<Integer,String> map;
    
    private Integer selection = null;

    public void setData(HashMap<Integer,String> map){
        this.map = map;
    } 

    @Override
    public int getSize() {
        return map.size();
    }

    @Override
    public void setSelectedItem(Object o) {
        this.selection = (Integer)o;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public Object getElementAt(int i) {
        Integer key = this.positionToKey(this.map.keySet(), i);
        if(key!=null)
            return this.map.get(key);
        return null;
    }
    
    private Integer positionToKey(Set<Integer> set,int pos){
        Integer[] array_set = (Integer[]) set.toArray();
        if(array_set.length>0){
            return array_set[pos];
        }
        return null;
    }
    
}