package oscar.persistance;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 *
 * @author schiodin
 */
public abstract class Controller extends Thread implements PropertyChangeListener {
    
        private ArrayList<DbRecord> registeredModels;
        private ArrayList<AbstractView> registeredViews;
        
        public Controller(){
            this.registeredModels = new ArrayList<DbRecord>();
            this.registeredViews = new ArrayList<AbstractView>();
        }
        
        public void addModel(DbRecord model){
            this.registeredModels.add(model);
        }
        
        public void removeModel(DbRecord model){
            this.registeredModels.remove(model);
        }
        
        public void addView(AbstractView view){
            this.registeredViews.add(view);
            view.setVisible(true);
        }
        public void removeView(AbstractView view){
            this.registeredViews.remove(view);
            view.setVisible(false);
        }
        
        @Override
        public void propertyChange(PropertyChangeEvent e){
            for(AbstractView view:this.registeredViews){
            }
        }
        
        private void safeStop (){
            // handle deistantiation and stuff like that
            this.stop();
        }
}
