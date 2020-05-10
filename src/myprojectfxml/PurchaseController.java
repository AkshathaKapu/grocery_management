/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprojectfxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static myprojectfxml.mynames.pass;
import static myprojectfxml.mynames.path;
import static myprojectfxml.mynames.place;
import static myprojectfxml.mynames.user;

public class PurchaseController implements Initializable {

    int totalamt=0,totalbill=0,gst=0;
    
 Alert infoalert = new Alert(Alert.AlertType.INFORMATION);
 Alert erralert = new Alert(Alert.AlertType.ERROR);
 
    @FXML
    private Label puridbox;
    @FXML
    private ComboBox<String> suplistbox;
    @FXML
    private ComboBox<String> catlistbox;
    @FXML
    private ComboBox<String> subcatlistbox;
    @FXML
    private ComboBox<String> prodlistbox;
    @FXML
    private DatePicker currdatebox;
    @FXML
    private TextField qtybox;
    @FXML
    private Label pricebox;
    @FXML
    private TableView<PurchaseCls> purchaseTable;
    @FXML
    private TableColumn<PurchaseCls,String> prodNameCol;
    @FXML
    private TableColumn<PurchaseCls,String> priceCol;
    @FXML
    private TableColumn<PurchaseCls,String> qtyCol;
    @FXML
    private TableColumn<PurchaseCls,String> totalPriceCol;
    @FXML
    private Label totalamtbox;
    @FXML
    private Label gstbox;
    @FXML
    private Label totalbillbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //to connect table columns with class data memebers
        prodNameCol.setCellValueFactory(new PropertyValueFactory<PurchaseCls,String>("prodName_c"));
        priceCol.setCellValueFactory(new PropertyValueFactory<PurchaseCls,String>("prodPrice_c"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<PurchaseCls,String>("prodQty_c"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<PurchaseCls,String>("totalPrice_c"));
        fetchCategory();
        fetchSupplier();
        purchaseid();
        
        currdatebox.setValue(LocalDate.now());
        
        //to let user to select  multiple rows for deletion 
        purchaseTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
//        TableView.setPlaceholder(new Label("no Product is selected Yet")); 


           
        
    }    
ObservableList<PurchaseCls> purlist=FXCollections.observableArrayList();
    @FXML
    private void addPurToTable(ActionEvent event) {
        
    
//    DefaultTableModel model=(DefaultTableModel) jTable1.getModel();
    String cat=prodlistbox.getValue().toString();
    String name=cat.substring(cat.indexOf(',')+1);
    String n=name;
    String p=pricebox.getText();
    String q=qtybox.getText();
    int t=Integer.parseInt(p)*Integer.parseInt(q);
    String t1=String.valueOf(t);
    
    
    
    purlist.add(new PurchaseCls(n, p,q,t1));///adding data into category list
    purchaseTable.setItems(purlist);
    
    totalamt=t+totalamt;
    int g=(t*5)/100;
    gst=gst+g;
    totalbill=totalbill+(t+g);
    System.out.println("total amt = "+totalamt);
    System.out.println("gst = "+gst);
    System.out.println("total bill= "+totalbill);
    totalamtbox.setText(String.valueOf(totalamt));
    gstbox.setText(String.valueOf(gst));
    totalbillbox.setText(String.valueOf(totalbill));
    
    }

    @FXML
    private void makepurchase(ActionEvent event) {
         addpurchase();
        addpurchasedetail();
       clearscr();
    }
    
    
    
    private void purchaseid()
{
    try 
        {
         
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from purchase order by purchaseid desc";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                ResultSet result=st.executeQuery();
                 if(result.next())
                 {
                    
                     int i=result.getInt("purchaseid");
                     i+=1;
                     
                     String id=String.valueOf(i);
                     puridbox.setText(id);
                    
                 }
                 else
                 {
                     puridbox.setText("1");
                     
                 }
           }
           catch(Exception e)
           {
               erralert.setTitle("Purchase ID");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
           
       
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("Purchase Id");
            String s = "Error in database" + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
}
    private   void fetchCategory()
{
    try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from category ";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                ResultSet result=st.executeQuery();
                ObservableList<String> catlist=FXCollections.observableArrayList();
                 if(result.next())
                 {
                    do
                     {
                     String i=result.getString("catid");
                     String n=result.getString("catname");
                     String item=i+","+n;
                    catlist.add(item);
                     
                     } while(result.next());
                    catlistbox.setValue("choose Category"); 
                    catlistbox.setItems(catlist);
                 }
                 else
                 {
                     catlistbox.getItems().clear(); 
                     catlistbox.setValue("no Category");
                    
                 }
           }
           catch(Exception e)
           {
               erralert.setTitle("Category");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("Category");
            String s = "Error in database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
}
    private void fetchSupplier()
{
    try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from supplier ";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                ResultSet result=st.executeQuery();
                ObservableList<String>supdata=FXCollections.observableArrayList();
                 if(result.next())
                 {
                    do
                     {
                     String i=result.getString("supid");
                     String n=result.getString("supname");
                     String item=i+","+n;
                     supdata.add(item);
                     
                     } while(result.next());
                    suplistbox.setValue("choose Supplier"); 
                    suplistbox.setItems(supdata);
                    
                 }
                 else
                 {
                    suplistbox.getItems().clear(); 
                     suplistbox.setValue("no Supplier");
                 }
           }
           catch(Exception e)
           {
               erralert.setTitle("Supplier");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("Supplier");
            String s = "Error in database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
}
    
@FXML
    private void fetchsubCategory()
{
        int itm=catlistbox.getSelectionModel().getSelectedIndex();
    if(itm>=0)
    {
        
    try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String cat=catlistbox.getValue().toString();
               String id=cat.substring(0,cat.indexOf(','));
               
               
               String qry="select * from subcategory where catid = ?";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               st.setString(1, id);
                ResultSet result=st.executeQuery();
                
                subcatlistbox.getItems().clear(); //must do it ,otherwise unexpected error
                ObservableList <String> subcatdata=FXCollections.observableArrayList();
                 if(result.next())
                 {
                    do
                     {
                     String i=result.getString("subcatid");
                     String n=result.getString("subcatname");
                     String item=i+","+n;
                     subcatdata.add(item);
                     
                     } while(result.next());
                    subcatlistbox.setValue("choose Sub CATegory"); 
                    subcatlistbox.setItems(subcatdata);
                 }
                 else
                 {
                     
                     subcatlistbox.setValue("no Sub category");
                 }
           }
           catch(Exception e)
           {
           
           }
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("Sub Category");
            String s = "Error in Database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
}
   else
                 {
                     subcatlistbox.getItems().clear(); 
                     subcatlistbox.setValue("no Sub category");
                 }
}
    
@FXML
    private void fetchproduct()
{

        int itm=subcatlistbox.getSelectionModel().getSelectedIndex();
    if(itm>=0)
    {
    try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String cat=subcatlistbox.getValue().toString();
               String id=cat.substring(0,cat.indexOf(','));               
               
               String qry="select * from product where subcatid = ?";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               st.setString(1, id);
                ResultSet result=st.executeQuery();
                prodlistbox.getItems().clear(); 
                ObservableList <String> proddata=FXCollections.observableArrayList();
                 if(result.next())
                 {
                    do
                     {
                     String i=result.getString("prodid");
                     String n=result.getString("prodname");
                     String item=i+","+n;
                     proddata.add(item);
                     
                     } while(result.next());
                   prodlistbox.setValue("choose Product"); 
                    prodlistbox.setItems(proddata);
                 }
                 else
                 {
                     
                     prodlistbox.setValue("no Product");
                 }
           }
         catch(Exception e)
           {
               erralert.setTitle("Product");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("Product");
            String s = "Error in Database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
}
   else
                 {
                     prodlistbox.getItems().clear(); 
                     prodlistbox.setValue("no Product");
                 } 
} 
@FXML
    private  void fetchprice()
{
    int itm=prodlistbox.getSelectionModel().getSelectedIndex();
    
    if(itm>=0)
    {
    try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String cat=prodlistbox.getValue().toString();
               String id=cat.substring(0,cat.indexOf(','));
               
               String qry="select * from product where prodid = ?";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               st.setString(1, id);
                ResultSet result=st.executeQuery();
                 if(result.next())
                 {
                     String i=result.getString("price");
                     pricebox.setText(i);
                    
                 }
                 else
                 {
                    pricebox.setText("no price");
                 }
           }
        catch(Exception e)
           {
               erralert.setTitle("Price");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("Price");
            String s = "Error in Database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
}
    else
                 {
                    pricebox.setText("..........");
                 }
    
} 
     
    
        
  void addpurchasedetail()
    {
        try 
        {
            ObservableList<PurchaseCls> data =purchaseTable.getItems();
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               if(data.isEmpty()==false)
               {
                   
                for(PurchaseCls yd : data) 
                  {
                    System.out.println("yd : "+yd);
                    System.out.println("name = "+yd.getProdName_c());
                    System.out.println("price = "+yd.getProdPrice_c());
                    System.out.println("qty = "+yd.getProdQty_c());
                    System.out.println("Total price = "+yd.getTotalPrice_c());
                    
                    String qry="insert into purchasedetail (purchaseid,supname,productname,price,qty,totalamt,billdate)values(?,?,?,?,?,?,?)";
                    PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                    String q=puridbox.getText();
                    st.setString(1,q);

                    String cat=suplistbox.getValue().toString();
                    String name=cat.substring(cat.indexOf(',')+1);
                    System.out.println("name = "+name);
                    st.setString(2,name);
              
                    String pn= yd.getProdName_c();

                    String pr=yd.getProdPrice_c();
                    String qt=yd.getProdQty_c();
                    String ta=yd.getTotalPrice_c();
                    st.setString(3,pn);
                    st.setString(4,pr);
                    st.setString(5,qt);
                    st.setString(6,ta);

                    //converting date into sql date 
                    java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(currdatebox.getValue());
                    //converting sql date into string
                    String d3=gettedDatePickerDate.toString();
                    st.setString(7, d3);

                    int result=st.executeUpdate();
                    updateproduct_stock(pn, qt);
                    if(result>0)
                    {
                        
                        System.out.println("*****saved********");

                    }
                    else
                    {
                        System.out.println("******error********");
                    } 
                   }
                }   
               else
               {
                   erralert.setTitle("Purchase Detail ");
                    String s = "Empty data" + "Purchase some items first";
                    erralert.setContentText(s);
                    erralert.showAndWait();
                   
               }
           }
          catch(Exception e)
           {
               erralert.setTitle("Purchase Detail");
            String s = "Purchase Detail Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }

        } 
        catch (Exception ex) 
        {
            erralert.setTitle("Purchase Detail");
            String s = "Error in Database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
    }
  int fetch_pro_stock(String name)
{
    int i=0;
    try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from product where prodname=?";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               st.setString(1,name);
                ResultSet result=st.executeQuery();
                 if(result.next())
                 {
                    i=Integer.parseInt(result.getString("qty"));
                 }
           }
           catch(Exception e)
           {
                erralert.setTitle("Some Error");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
           
       
        } 
        catch (Exception ex) 
        {
              erralert.setTitle("Some Error");
            String s = "Error in database connection" + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
        
        
    System.out.println("fetch product stock");
    System.out.println("name"+name);
    System.out.println("stock"+i);

        
        
        
        return i;
        
        
}


    void updateproduct_stock(String name,String qty)
    {
        int stock;
        stock=fetch_pro_stock(name);
        int newstock=0;
        newstock=stock+(Integer.parseInt(qty));
         try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               
               String qry="update product set qty=? where prodname=?";
                      
               
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                st.setString(1,String.valueOf(newstock));
                st.setString(2,name);
                
                 int result=st.executeUpdate();

                 if(!(result>0))
                 {
                      infoalert.setTitle("Unsuccessfull");
                    infoalert.setHeaderText("Updation ");
                    String s ="Stock  Updation Operation UnSuccessfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                 }
               
           }
           catch(Exception e)
           {
               erralert.setTitle("Some Error");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
        } 
        catch (Exception ex) 
        {
             erralert.setTitle("Some Error");
            String s = "Error in database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
           
       
        
   
    }
    
    
    
    
    
    void addpurchase()
    {
        try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               //purchaseid	supid 	billid  billdate totalamt gst totalbill
               String qry="insert into purchase values(?,?,?,?,?,?)";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                st.setString(1,puridbox.getText());
                
                String cat=suplistbox.getValue().toString();
                String id=cat.substring(0,cat.indexOf(','));
                st.setString(2,id);
                
                //converting datepicker date into sql date 
                java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(currdatebox.getValue());
                //converting sql date into string
                String d3=gettedDatePickerDate.toString();
                st.setString(3, d3);

                
                
                 st.setString(4,totalamtbox.getText());
                 st.setString(5,gstbox.getText());
                 st.setString(6,totalbillbox.getText());
                 
                
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                    infoalert.setTitle("Purcahse");
                    infoalert.setHeaderText("Added  ");
                    String s ="Purchase details Added Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                
                 }
            
           
              
               
           }
           catch(Exception e)
           {
               erralert.setTitle("Purchase");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("Purcahse");
            String s = "Error in Database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        }  
    }

    @FXML
    private void delete_row(ActionEvent event) {
        ObservableList<PurchaseCls> selectedItems = purchaseTable.getSelectionModel().getSelectedItems();
        purchaseTable.getItems().removeAll(selectedItems);
        update_bill_on_deletion();
    }
    private void update_bill_on_deletion()
    {
        ObservableList<PurchaseCls> data =purchaseTable.getItems();
        int tp=0,g=0;
        if(data.isEmpty()==false)
            {    
                for(PurchaseCls yd : data) 
                {
                   tp = tp+Integer.parseInt(yd.getTotalPrice_c());
                   g=g+(tp*5)/100;
                }
                totalamt=tp;
                gst=g;
                totalbill=totalamt+gst;
            }
            else
            {
                totalamt=tp;
                gst=g;
                totalbill=totalamt+gst;
            }
            totalamtbox.setText(String.valueOf(totalamt));
            gstbox.setText(String.valueOf(gst));
            totalbillbox.setText(String.valueOf(totalbill));
    }
    void clearscr()
    {
        purchaseid();
        catlistbox.setValue("choose Category"); 
        subcatlistbox.getItems().clear(); 
        subcatlistbox.setValue("no Sub category");
        prodlistbox.getItems().clear(); 
        prodlistbox.setValue("no Product");
        suplistbox.setValue("choose Supplier"); 
        currdatebox.setValue(LocalDate.now());
        totalamtbox.setText("--------");
        gstbox.setText("--------");
        totalbillbox.setText("---------");
        qtybox.clear();
        purchaseTable.getItems().clear();
        purchaseTable.refresh();
        
            
    }
    

}
        
        
   