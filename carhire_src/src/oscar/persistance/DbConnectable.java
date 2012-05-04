package oscar.persistance;

/**
 *
 * @author sujan
 */
public interface DbConnectable {

    /**
     * 
     * @return
     */
    java.sql.Connection getConnection();
    
    /**
     * 
     */
    void closeConnection();
    
}
