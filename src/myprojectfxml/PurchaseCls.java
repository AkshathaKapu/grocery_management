package myprojectfxml;

import javafx.beans.property.SimpleStringProperty;

public class PurchaseCls
{
    private SimpleStringProperty prodName_c;
    private SimpleStringProperty prodPrice_c;
    private SimpleStringProperty prodQty_c;
    private SimpleStringProperty totalPrice_c;

    public PurchaseCls(String prodName_c, String prodPrice_c, String prodQty_c,String totalPrice_c) 
    {
        this.prodName_c =new SimpleStringProperty( prodName_c);
        this.prodPrice_c = new SimpleStringProperty( prodPrice_c);
        this.prodQty_c = new SimpleStringProperty( prodQty_c);
        this.totalPrice_c = new SimpleStringProperty( totalPrice_c);
    }

    public String getProdName_c() {
        return prodName_c.get();
    }

    public void setProdName_c(SimpleStringProperty prodName_c) {
        this.prodName_c = prodName_c;
    }

    public String getProdPrice_c() {
        return prodPrice_c.get();
    }

    public void setProdPrice_c(SimpleStringProperty prodPrice_c) {
        this.prodPrice_c = prodPrice_c;
    }

    public String getProdQty_c() {
        return prodQty_c.get();
    }

    public void setProdQty_c(SimpleStringProperty prodQty_c) {
        this.prodQty_c = prodQty_c;
    }

    public String getTotalPrice_c() {
        return totalPrice_c.get();
    }

    public void setTotalPrice_c(SimpleStringProperty totalPrice_c) {
        this.totalPrice_c = totalPrice_c;
    }
    
    
}
