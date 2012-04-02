package oscar.persistance;

/**
 *
 * @author sujan
 */
public interface DbConnectable {

    java.sql.Connection getConnection();
    
    void closeConnection();
    
}
