package oscar.persistance;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 *
 * @author schiodin
 */
public abstract class Controller extends Thread implements ActionListener {

    private ArrayList<DbRecord> registeredModels;
    private ArrayList<AbstractView> registeredViews;
    private ArrayList<JButton> registeredButtonListeners;

    public Controller() {
        this.registeredModels = new ArrayList<DbRecord>();
        this.registeredViews = new ArrayList<AbstractView>();
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

    public void addButtonListener(JButton button) {
        this.registeredButtonListeners.add(button);
        button.removeActionListener(this);
    }

    public void removeButtonListener(JButton button) {
        button.removeActionListener(this);
        this.registeredButtonListeners.remove(button);
    }

    public void removeAllButtonListener() {
        for (JButton b : registeredButtonListeners) {
            b.removeActionListener(this);
            this.registeredButtonListeners.remove(b);
        }
    }

    private void safeStop() {
        // handle deistantiation and stuff like that
        this.stop();
    }
}
