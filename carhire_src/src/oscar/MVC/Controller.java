package oscar.MVC;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author schiodin
 */
public abstract class Controller extends Thread implements ActionListener, KeyListener, MouseListener {

    /**
     * delay in ms of the tables filtering triggered by key pressing in the text fields
     */
    public static long TABLE_FILTERING_DELAY = 500;
    private ArrayList<Object> elements;

    /**
     * Constructor, initialize its elements
     */
    public Controller() {
        this.elements = new ArrayList<Object>();
    }

    /**
     * add an element to the controller to be tracked
     * @param element can be JButton, JComboBox, JTextField, JTable or AbstractView
     */
    public void addElement(Object element) {
        elements.add(element);

        if (element.getClass().equals(JButton.class)) {
            ((JButton) element).addActionListener(this);
        } else if (element.getClass().equals(JComboBox.class)) {
            ((JComboBox) element).addActionListener(this);
        } else if (element.getClass().equals(JTextField.class)) {
            ((JTextField) element).addKeyListener(this);
        } else if (element.getClass().getSuperclass().equals(JTable.class)) {
            ((JTable) element).addMouseListener(this);
        } else if (element.getClass().getSuperclass().equals(AbstractView.class)) {
            ((AbstractView) element).setVisible(true);
        }
    }

    /**
     * remove all registered elements
     */
    public void removeAllElement() {
        for (Object element : elements) {
            if (element.getClass().equals(JButton.class)) {
                ((JButton) element).removeActionListener(this);
            } else if (element.getClass().equals(JTextField.class)) {
                ((JTextField) element).removeKeyListener(this);
            } else if (element.getClass().getSuperclass().equals(AbstractView.class)) {
                ((AbstractView) element).setVisible(false);
            } else if (element.getClass().equals(JDialog.class)) {
                ((JDialog) element).setVisible(false);
            }
        }
        elements = new ArrayList<Object>();
    }
}
