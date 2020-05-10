
package myprojectfxml;

import javafx.beans.property.SimpleStringProperty;
public class supplierCls {
    private SimpleStringProperty suppid_c;
    private SimpleStringProperty supname_c;

    public supplierCls(String id,String name) {
        this.suppid_c = new SimpleStringProperty(id);
        this.supname_c = new SimpleStringProperty(name);
    }

    public String getSuppid_c() {
        return suppid_c.get();
    }

    public void setSuppid_c(SimpleStringProperty suppid_c) {
        this.suppid_c = suppid_c;
    }

    public String getSupname_c() {
        return supname_c.get();
    }

    public void setSupname_c(SimpleStringProperty supname_c) {
        this.supname_c = supname_c;
    }
    
}
