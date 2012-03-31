package oscar;

//import java.util.ArrayList;
//import java.util.HashMap;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import oscar.view.LoginView;
import oscar.view.StaffView;


/**
 *
 * @author sujan
 */
public class Main extends SingleFrameApplication {
    
     /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
       // show(new AdminView(this));
        show(new LoginView());
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of CarhireApp
     */
    public static Main getApplication() {
        return Application.getInstance(Main.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(Main.class, args);
    }
    
    
    /*
    public static void main(String... args){

        Branch record = new Branch();
        //record.setTable("topics");
        /*
        try{
            int count = record.count();
            System.out.println("Total Records were : "+count);
            
            ArrayList<HashMap<String,String>> rs = record.findAll();
            
           // System.out.println("No. of Records :"+rs.size());
            
            for(HashMap<String,String> row:rs){
                
                for(String key:row.keySet()){
                    System.out.println(key +" : "+row.get(key));
                }
                
            }
            rs = null;
            
        }catch(Exception e){
            System.out.println(e.getCause());
        }
         * 
        
    }
*/
    
    
}
