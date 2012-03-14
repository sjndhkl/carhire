/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscar.model;

import oscar.persistance.DbRecord;

/**
 *
 * @author sujan
 */
public class BranchModel extends DbRecord {
    
    public BranchModel(){
        super("branch");
    }
    
    public BranchModel(String table){
        super(table);
    }
    
    public String getTable(){
        return this.useTable;
    }
    
    public void setTable(String newTable){
        this.useTable = newTable;
    }

    @Override
    public boolean update(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean add(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
