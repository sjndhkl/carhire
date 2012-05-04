package oscar.util;

import java.util.HashMap;
import javax.swing.JFrame;
import oscar.view.dialog.ErrorDialog;
//import oscar.view.dialog.ValidationErrorDialog;

/**
 *
 * @author Draga
 */
public abstract class ValidationPopup {

    public static void popup(HashMap<String, String> errors, JFrame frame) {
        String errorString = "<html>";
        for (String attribute : errors.keySet()) {
            errorString += attribute + ": ";
            errorString += errors.get(attribute);
            errorString += "<br />";
        }
        errorString += "</html>";
        System.out.println(errorString);

        ErrorDialog errorDialog = new ErrorDialog(frame, true);
        errorDialog.getMessageLbl().setText(errorString);
        errorDialog.setVisible(true);
    }
}
