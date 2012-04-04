package oscar.persistance;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author schiodin
 */
public abstract class Controller extends Thread implements ActionListener {

    private ArrayList<DbRecord> registeredModels;
    private ArrayList<AbstractView> registeredViews;
    private ArrayList<JButton> registeredButtonListeners;
    private ArrayList<JTextField> registeredTextFieldListeners;

    public Controller() {
        this.registeredModels = new ArrayList<DbRecord>();
        this.registeredViews = new ArrayList<AbstractView>();
        this.registeredButtonListeners = new ArrayList<JButton>();
        this.registeredTextFieldListeners = new ArrayList<JTextField>();
    }

    public void addModel(DbRecord model) {
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
        for (AbstractView v : registeredViews) {
            v.setVisible(false);
        }
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
        for (JButton b : this.registeredButtonListeners) {
            b.removeActionListener(this);
        }
        this.registeredButtonListeners = new ArrayList<JButton>();
    }

    public void addTextFieldListener(JTextField textField) {
        this.registeredTextFieldListeners.add(textField);
        textField.addActionListener(this);
    }

    public void removeTextFieldListener(JTextField textField) {
        textField.removeActionListener(this);
        this.registeredTextFieldListeners.remove(textField);
    }

    public void removeAllTextFieldListeners() {
        for (JTextField b : this.registeredTextFieldListeners) {
            b.removeActionListener(this);
        }
        this.registeredTextFieldListeners = new ArrayList<JTextField>();
    }

    protected void safeStop() {
        // handle deistantiation and stuff like that
        this.removeAllButtonListeners();
        this.removeAllView();
        //this.stop();
    }
}
