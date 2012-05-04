package oscar.util;

/**
 *
 * @author Draga
 */
public abstract class Validator {

    /**
     * 
     * @param s
     * @return
     */
    public static Float parseFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * 
     * @param s
     * @return
     */
    public static Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
