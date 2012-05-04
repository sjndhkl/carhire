package oscar.util;

import java.util.HashMap;
import java.util.TimerTask;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;
import oscar.MVC.DbRecord;

/**
 * This is runnable task to be schedule to update a table in future
 * @author schiodin
 */
public class TableUpdateTask extends TimerTask {

    /** the table to query */
    private JXTable table;
    /** the hashmap of filters as column -> filter */
    private HashMap<String, String> filters;
    /** the model to ask the filtered table model to set into model */
    private DbRecord model;

    /**
     * Creates a new task
     * @param table 
     * @param filters
     * @param model
     */
    public TableUpdateTask(JXTable table, HashMap<String, String> filters, DbRecord model) {
        this.table = table;
        this.filters = filters;
        this.model = model;
    }

    @Override
    public void run() {
        if (table != null && filters != null && model != null) {
            TableModel tableModel = model.getTableModel(filters);
            table.setModel(tableModel);
        }
    }
}