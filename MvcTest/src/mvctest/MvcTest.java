/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mvctest;

import controller.DefaultController;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import model.PersonModel;
import view.PersonInfoChangerViewPanel;
import view.PersonInfoViewPanel;

/**
 *
 * @author sujan
 */
public class MvcTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        PersonModel model = new PersonModel();
        DefaultController controller = new DefaultController();
        PersonInfoChangerViewPanel changerView = new PersonInfoChangerViewPanel(controller);
        PersonInfoViewPanel displayView = new PersonInfoViewPanel(controller);
        
        controller.addView(displayView);
        controller.addView(changerView);
        controller.addModel(model);
        model.init();
        
        JFrame displayFrame = new JFrame("Person View");
        displayFrame.getContentPane().add(displayView, BorderLayout.CENTER);
        displayFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        displayFrame.pack();
        
        JDialog propertiesDialog = new JDialog(displayFrame, "Person Changer");
        propertiesDialog.setModal(false);
        propertiesDialog.getContentPane().add(changerView, BorderLayout.CENTER);
        propertiesDialog.pack();
        
        displayFrame.setVisible(true);
        propertiesDialog.setVisible(true);

        
    }
}
