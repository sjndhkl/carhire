package oscar.persistance;;

/**
 *
 * @author sujan
 */
public interface DbRecordable {

    boolean add();
    
    boolean delete();
    
    boolean update();
    
}
