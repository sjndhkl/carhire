package oscar.MVC;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *
 * @author schiodin
 */
public abstract class Controller extends Thread implements ActionListener, KeyListener {

    public static long TABLE_FILTERING_DELAY = 500;
    private ArrayList<Object> elements;
    private Object View;

    public Controller() {
        this.elements = new ArrayList<Object>();
    }

    public void addElement(Object element) {
        elements.add(element);

        if (element.getClass().equals(JButton.class))
            ((JButton) element).addActionListener(this);
        else if (element.getClass().equals(JComboBox.class))
            ((JComboBox) element).addActionListener(this);
        else if (element.getClass().equals(JTextField.class))
            ((JTextField) element).addKeyListener(this);
        else if (element.getClass().getSuperclass().equals(AbstractView.class))
            ((AbstractView) element).setVisible(true);
        //else if (element.getClass().getSuperclass().equals(JDialog.class))
        //    ((JDialog) element).setVisible(true);
    }

    public void removeAllElement() {
        for (Object element : elements)
            if (element.getClass().equals(JButton.class))
                ((JButton) element).removeActionListener(this);
            else if (element.getClass().equals(JTextField.class))
                ((JTextField) element).removeKeyListener(this);
            else if (element.getClass().getSuperclass().equals(AbstractView.class))
                ((AbstractView) element).setVisible(false);
            else if (element.getClass().equals(JDialog.class))
                ((JDialog) element).setVisible(false);
        elements = new ArrayList<Object>();
    }

    /*public void addModel(DbRecord model) {
    this.registeredModels.add(model);
    }
    
    public void removeModel(DbRecord model) {
    this.registeredModels.remove(model);
    }
    
    public void addView(AbstractView view) {
    this.registeredViews.add(view);
    view.setVisible(true);
    }
    
    public void removeView(AbstractView view) {
    this.registeredViews.remove(view);
    view.setVisible(false);
    }
    
    public void removeAllView() {
    for (AbstractView v : registeredViews)
    v.setVisible(false);
    this.registeredViews = new ArrayList<AbstractView>();
    }
    
    public void addButtonListener(JButton button) {
    this.registeredButtonListeners.add(button);
    button.addActionListener(this);
    }
    
    public void removeButtonListener(JButton button) {
    button.removeActionListener(this);
    this.registeredButtonListeners.remove(button);
    }
    
    public void removeAllButtonListeners() {
    for (JButton b : this.registeredButtonListeners)
    b.removeActionListener(this);
    this.registeredButtonListeners = new ArrayList<JButton>();
    }
    
    public void addTextFieldListener(JTextField textField) {
    this.registeredTextFieldListeners.add(textField);
    textField.addKeyListener(this);
    }
    
    public void removeTextFieldListener(JTextField textField) {
    textField.removeKeyListener(this);
    this.registeredTextFieldListeners.remove(textField);
    }
    
    public void removeAllTextFieldListeners() {
    for (JTextField b : this.registeredTextFieldListeners)
    b.removeKeyListener(this);
    this.registeredTextFieldListeners = new ArrayList<JTextField>();
    }
    
    protected void safeStop() {
    // handle deistantiation and stuff like that
    
    this.removeAllButtonListeners();
    this.removeAllView();
    this.removeAllTextFieldListeners();
    //this.stop();
    }
     */
}
