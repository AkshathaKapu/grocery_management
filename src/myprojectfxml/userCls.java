
package myprojectfxml;

import javafx.beans.property.SimpleStringProperty;

public class userCls 
{
    private SimpleStringProperty uname_c;
    private SimpleStringProperty address_c;
    private SimpleStringProperty gender_c;
    private SimpleStringProperty username_c;
    private SimpleStringProperty type_c;

    public userCls(String uname_c,String address_c,String gender_c,String username_c,String type_c) 
    {
        this.uname_c = new SimpleStringProperty(uname_c);
        this.address_c = new SimpleStringProperty(address_c);
        this.gender_c = new SimpleStringProperty(gender_c);
        this.username_c = new SimpleStringProperty(username_c);
        this.type_c = new SimpleStringProperty(type_c);
    }

    public String getUname_c() {
        return uname_c.get();
    }

    public void setUname_c(SimpleStringProperty uname_c) {
        this.uname_c = uname_c;
    }

    public String getAddress_c() {
        return address_c.get();
    }

    public void setAddress_c(SimpleStringProperty address_c) {
        this.address_c = address_c;
    }

    public String getGender_c() {
        return gender_c.get();
    }

    public void setGender_c(SimpleStringProperty gender_c) {
        this.gender_c = gender_c;
    }

    public String getUsername_c() {
        return username_c.get();
    }

    public void setUsername_c(SimpleStringProperty username_c) {
        this.username_c = username_c;
    }

    public String getType_c() {
        return type_c.get();
    }

    public void setType_c(SimpleStringProperty type_c) {
        this.type_c = type_c;
    }
    
    
    

    



    
}
