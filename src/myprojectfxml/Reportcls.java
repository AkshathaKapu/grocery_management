/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprojectfxml;

import javafx.beans.property.SimpleStringProperty;

public class Reportcls 
{
    private SimpleStringProperty purchId_c;
    private SimpleStringProperty sname_c;
    private SimpleStringProperty billdate_c;
    private SimpleStringProperty billamt_c;

    public Reportcls(String purchId_c, String sname_c,String billdate_c, String billamt_c) 
    {
        this.purchId_c = new SimpleStringProperty(purchId_c);
        this.sname_c = new SimpleStringProperty(sname_c);
        this.billdate_c = new SimpleStringProperty(billdate_c);
        this.billamt_c = new SimpleStringProperty(billamt_c);
    }

    public String getPurchId_c() {
        return purchId_c.get();
    }

    public void setPurchId_c(SimpleStringProperty purchId_c) {
        this.purchId_c = purchId_c;
    }

    public String getSname_c() {
        return sname_c.get();
    }

    public void setSname_c(SimpleStringProperty sname_c) {
        this.sname_c = sname_c;
    }

    public String getBilldate_c() {
        return billdate_c.get();
    }

    public void setBilldate_c(SimpleStringProperty billdate_c) {
        this.billdate_c = billdate_c;
    }

    public String getBillamt_c() {
        return billamt_c.get();
    }

    public void setBillamt_c(SimpleStringProperty billamt_c) {
        this.billamt_c = billamt_c;
    }
    
    

    
}
