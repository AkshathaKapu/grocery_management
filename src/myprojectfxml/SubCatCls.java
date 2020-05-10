/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprojectfxml;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author gtb student
 */
public class SubCatCls {
    ///this class must contain setter and getter functions
    private SimpleStringProperty catid_c;
    private SimpleStringProperty subcatid_c;
    private SimpleStringProperty subcatname_c;

    public SubCatCls(String id,String sid,String name) {
        catid_c=new SimpleStringProperty(id);
        this.subcatid_c = new SimpleStringProperty(sid);
        this.subcatname_c = new SimpleStringProperty(name);
    }

    public String getCatid_c() {
        return catid_c.get();
    }

    public void setCatid_c(SimpleStringProperty catid_c) {
        this.catid_c = catid_c;
    }

    public String getSubcatid_c() {
        return subcatid_c.get();
    }

    public void setSubcatid_c(SimpleStringProperty subcatid_c) {
        this.subcatid_c = subcatid_c;
    }

    public String getSubcatname_c() {
        return subcatname_c.get();
    }

    public void setSubcatname_c(SimpleStringProperty subcatname_c) {
        this.subcatname_c = subcatname_c;
    }
    
   
    
}

