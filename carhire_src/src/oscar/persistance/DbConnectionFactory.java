package oscar.persistance;

/**
 *
 * @author sujan
 */
public class DbConnectionFactory {
    
    /** Databse types*/
    public enum Database{
        /**
         * 
         */
        MYSQL
    }
    
    
    
    /**
     * Returns the appropriate connection for the database type
     * @param dbType type of databse
     * @return the connectable
     */
    public static DbConnectable connect(Database dbType){
        
        switch(dbType){
            case MYSQL: return new MySqlConnection(); 
        }
        return null;
    }
    
    
}
