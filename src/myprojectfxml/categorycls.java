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
public class categorycls {
    ///this class must contain setter and getter functions
    private SimpleStringProperty catid_c;
    private SimpleStringProperty catname_c;
    
    public categorycls(String id,String name)
    {
        catid_c=new SimpleStringProperty(id);
        catname_c=new SimpleStringProperty(name);
    }

    public String getCatid_c() {
        return catid_c.get();///should use get() ,otherwise it give simplestringProperty not string
    }

    public void setCatid_c(SimpleStringProperty catid_c) {
        this.catid_c = catid_c;
    }

    public String getCatname_c() {
        return catname_c.get();
    }

    public void setCatname_c(SimpleStringProperty catname_c) {
        this.catname_c = catname_c;
    }

    
}
///to automatically call getter and setter functions 
///after making datamembers and constructor 
///right click here and select 'insert code' option 
///then select getter and setter option 
///than all  code will be here
