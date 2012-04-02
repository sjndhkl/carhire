/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import model.AbstractModel;
import view.AbstractViewPanel;

/**
 *
 * @author sujan
 */
public abstract class AbstractController implements PropertyChangeListener {
    
        private ArrayList<AbstractModel> registeredModels;
        private ArrayList<AbstractViewPanel> registeredViews;
        
        public AbstractController(){
            this.registeredModels=new ArrayList<AbstractModel>();
            this.registeredViews = new ArrayList<AbstractViewPanel>();
        }
        
        public void addModel(AbstractModel model){
            this.registeredModels.add(model);
            model.addPropertyChangeListener(this);
        }
        
        public void removeModel(AbstractModel model){
            this.registeredModels.remove(model);
            model.removePropertyChangeListener(this);
        }
        
        
        public void addView(AbstractViewPanel view){
            this.registeredViews.add(view);
        }
        public void removeView(AbstractViewPanel view){
            this.registeredViews.remove(view);
        }
        
        @Override
        public void propertyChange(PropertyChangeEvent e){
            for(AbstractViewPanel view:this.registeredViews){
               view.modelPropertyChange(e);
            }
        }
        
        public void setModelProperty(String propertyName,Object newValue){
            for(AbstractModel model: this.registeredModels){
                try{
                    Method method = model.getClass().getMethod("set"+propertyName,new Class[]{
                        newValue.getClass()
                    });
                    method.invoke(model,newValue);
                }catch(Exception e){
                    
                }
            }
        }
    
}
