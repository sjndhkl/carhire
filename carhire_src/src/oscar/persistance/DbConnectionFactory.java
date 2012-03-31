package oscar.persistance;

/**
 *
 * @author sujan
 */
public class DbConnectionFactory {
    
    public enum Database{
        MYSQL
    }
    
    
    
    public static DbConnectable connect(Database dbType){
        
        switch(dbType){
            case MYSQL: return new MySqlConnection(); 
        }
        return null;
    }
    
    
}
