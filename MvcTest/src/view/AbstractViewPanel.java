/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;

/**
 *
 * @author sujan
 */
public abstract class AbstractViewPanel extends JPanel {
    
    public abstract void modelPropertyChange(PropertyChangeEvent evt);
    
}
