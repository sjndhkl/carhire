/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author sujan
 */
public abstract class AbstractModel {
    
    protected PropertyChangeSupport propertyChangeSupport;
    
    public AbstractModel(){
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener l)
    {
        propertyChangeSupport.addPropertyChangeListener(l);
        
    }
    
    public void removePropertyChangeListener(PropertyChangeListener l){
        propertyChangeSupport.removePropertyChangeListener(l);
    }
    
    public void firePropertyChange(String propertyName,Object oldValue,Object newValue){
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
        
    }
        
    
}
