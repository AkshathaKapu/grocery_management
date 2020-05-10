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
public class productCls {
    private SimpleStringProperty prodid_c;
    private SimpleStringProperty catid_c;
    private SimpleStringProperty subcatid_c;
    private SimpleStringProperty pname_c;
    private SimpleStringProperty pprice_c;
    private SimpleStringProperty stock_c;

    public productCls(String pid,String cid,String sid,String name,String price,String stock) {
      
        this.prodid_c = new SimpleStringProperty(pid);
        this.catid_c = new SimpleStringProperty(cid);
        this.subcatid_c =new SimpleStringProperty(sid);
        this.pname_c = new SimpleStringProperty(name);
        this.pprice_c = new SimpleStringProperty(price);
        this.stock_c = new SimpleStringProperty(stock);
    }

    public String getProdid_c() {
        return prodid_c.get();
    }

    public void setProdid_c(SimpleStringProperty prodid_c) {
        this.prodid_c = prodid_c;
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

    public String getPname_c() {
        return pname_c.get();
    }

    public void setPname_c(SimpleStringProperty pname_c) {
        this.pname_c = pname_c;
    }

    public String getPprice_c() {
        return pprice_c.get();
    }

    public void setPprice_c(SimpleStringProperty pprice_c) {
        this.pprice_c = pprice_c;
    }

    public String getStock_c() {
        return stock_c.get();
    }

    public void setStock_c(SimpleStringProperty stock_c) {
        this.stock_c = stock_c;
    }

    
   
    
}

