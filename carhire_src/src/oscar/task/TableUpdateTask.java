package oscar.task;

import java.util.HashMap;
import java.util.TimerTask;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;
import oscar.MVC.DbRecord;

/**
 *
 * @author schiodin
 */
public class TableUpdateTask extends TimerTask {
    private JXTable table;
    private HashMap<String,String> filters;
    private DbRecord model;

    public TableUpdateTask(JXTable table, HashMap<String, String> filters, DbRecord model) {
        this.table = table;
        this.filters = filters;
        this.model = model;
    }

    @Override
    public void run() {
        TableModel tableModel = model.getTableModel(filters);
        table.setModel(tableModel);
    }
}