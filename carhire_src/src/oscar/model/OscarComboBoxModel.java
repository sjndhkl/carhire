/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.model;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author sujan
 */
public class OscarComboBoxModel extends AbstractListModel implements ComboBoxModel {
    
    private List<OscarComboBoxModelItem> list;
    
    private OscarComboBoxModelItem selection = null;

    public void setData(List<OscarComboBoxModelItem> list){
        this.list = list;
    } 

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public void setSelectedItem(Object o) {
        this.selection = (OscarComboBoxModelItem)o;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public Object getElementAt(int i) {
        return list.get(i);
    }
    
}