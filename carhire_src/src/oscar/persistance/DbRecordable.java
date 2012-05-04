package oscar.persistance;;

/**
 *
 * @author sujan
 */
public interface DbRecordable {

    /**
     * 
     * @return
     */
    boolean add();
    
    /**
     * 
     * @return
     */
    boolean delete();
    
    /**
     * 
     * @return
     */
    boolean update();
    
}
