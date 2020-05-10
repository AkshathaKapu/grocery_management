/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprojectfxml;

import javafx.beans.property.SimpleStringProperty;

public class supplierReportcls 
{
    private SimpleStringProperty pname_c;
    private SimpleStringProperty price_c;
    private SimpleStringProperty qty_c;
    private SimpleStringProperty totalPrice_c;
    private SimpleStringProperty billDate_c;
    

    public supplierReportcls(String pname,String price_c, String qty_c,String totalPrice_c,String billDate_c) 
    {
        this.pname_c = new SimpleStringProperty(pname);
        this.price_c = new SimpleStringProperty(price_c);
        this.qty_c = new SimpleStringProperty(qty_c);
        this.totalPrice_c = new SimpleStringProperty(totalPrice_c);
        this.billDate_c = new SimpleStringProperty(billDate_c);
    }

    public String getPname_c() {
        return pname_c.get();
    }

    public void setPname_c(SimpleStringProperty pname_c) {
        this.pname_c = pname_c;
    }

    public String getPrice_c() {
        return price_c.get();
    }

    public void setPrice_c(SimpleStringProperty price_c) {
        this.price_c = price_c;
    }

    public String getQty_c() {
        return qty_c.get();
    }

    public void setQty_c(SimpleStringProperty qty_c) {
        this.qty_c = qty_c;
    }

    public String getTotalPrice_c() {
        return totalPrice_c.get();
    }

    public void setTotalPrice_c(SimpleStringProperty totalPrice_c) {
        this.totalPrice_c = totalPrice_c;
    }

    public String getBillDate_c() {
        return billDate_c.get();
    }

    public void setBillDate_c(SimpleStringProperty billDate_c) {
        this.billDate_c = billDate_c;
    }
    
}
