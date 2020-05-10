package myprojectfxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static myprojectfxml.mynames.pass;
import static myprojectfxml.mynames.path;
import static myprojectfxml.mynames.place;
import static myprojectfxml.mynames.user;

public class ProductAddController implements Initializable {

    String t_prodid=null,t_cid=null,t_sid=null,t_pname=null,t_price=null,t_stock=null;
    @FXML
    private Label prodidbox;
    @FXML
    private ComboBox<String> catlistbox;
    @FXML
    private ComboBox<String> subcatlistbox;
    @FXML
    private TextField prodnamebox;
    @FXML
    private TextField prodpricebox;
    @FXML
    private TextField prodqtybox;

 Alert infoalert = new Alert(Alert.AlertType.INFORMATION);
 Alert erralert = new Alert(Alert.AlertType.ERROR);
    @FXML
    private TableColumn<productCls,String> prodId_box;
    @FXML
    private TableColumn<productCls,String> catId_box;
    @FXML
    private TableColumn<productCls,String> subcatid_box;
    @FXML
    private TableColumn<productCls,String> pname_box;
    @FXML
    private TableColumn<productCls,String> pprice_box;
    @FXML
    private TableColumn<productCls,String> stock_box;
    @FXML
    private TableView<productCls> ptable;
    @FXML
    private Button deletebtn;
    @FXML
    private Button updatetbn;
    @FXML
    private Button addbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ///connect table rows with class
       prodId_box.setCellValueFactory(new PropertyValueFactory<productCls,String>("prodid_c"));
       catId_box.setCellValueFactory(new PropertyValueFactory<productCls,String>("catid_c"));
       subcatid_box.setCellValueFactory(new PropertyValueFactory<productCls,String>("subcatid_c"));
       pname_box.setCellValueFactory(new PropertyValueFactory<productCls,String>("pname_c"));
       pprice_box.setCellValueFactory(new PropertyValueFactory<productCls,String>("pprice_c"));
       stock_box.setCellValueFactory(new PropertyValueFactory<productCls,String>("stock_c"));
       
       
       //double click on table row and getting username
       ptable.setRowFactory(tv -> {
            TableRow<productCls> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    productCls rowData = row.getItem();
                    t_prodid= rowData.getProdid_c();
                    t_cid=rowData.getCatid_c();
                    t_sid=rowData.getSubcatid_c();
                    t_pname=rowData.getPname_c();
                    t_price=rowData.getPprice_c();
                    t_stock=rowData.getStock_c();
                    get_data_of_table();
                }
            });
            return row ;
        });
        
        fetchcatdata();
        fetchid();
        fetch_data();
    }    
    void clearscr()
    {
        fetchid();
        fetch_data();
        
        prodnamebox.clear();
        prodpricebox.clear();
        prodqtybox.setText("0");
        catlistbox.setValue("choose Category"); 
        subcatlistbox.setValue("choose Sub Category");  
    }
    void fetchid()
{
    try 
        {
         
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from product order by prodid desc";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                ResultSet result=st.executeQuery();
                 if(result.next())
                 {
                    
                     int i=result.getInt("prodid");
                     i+=1;
                     
                     String id=String.valueOf(i);
                     System.out.println("id = "+id);
                     prodidbox.setText(id);
                    
                 }
                 else
                 {
                     System.out.println("id = 5001");
                    prodidbox.setText("5001");
                     
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
        
        
        
        
        
        
        
        
}


void fetchcatdata()
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
                
                ObservableList<String> catdata=FXCollections.observableArrayList();
                 if(result.next())
                 {
                    do
                     {
                     String i=result.getString("catid");
                     String n=result.getString("catname");
                     String item=i+","+n;
                     catdata.add(item);
                     
                     } while(result.next());
                    catlistbox.setValue("choose Category");    //to set default value
                    catlistbox.setItems(catdata);
                 }
                 else
                 {
                     catlistbox.getItems().clear(); 
                     catlistbox.setValue("No Category"); 
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
        catch (Exception e) 
        {
             erralert.setTitle("Some Error");
            String s = "Error in database Connection " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
}
    @FXML
    private void addprodfunc(ActionEvent event) {
         try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="insert into product (CatID,SubCatId,ProdName,Price,qty) values(?,?,?,?,?)";
               
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               
               String cat=catlistbox.getValue().toString();
               String id=cat.substring(0,cat.indexOf(','));
                st.setString(1,id);
                
               cat=subcatlistbox.getValue().toString();
               id=cat.substring(0,cat.indexOf(','));
               st.setString(2,id);
                
                
                
                st.setString(3,prodnamebox.getText());
                st.setString(4,prodpricebox.getText());
                st.setString(5,prodqtybox.getText());
                
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                      infoalert.setTitle("success");
                    infoalert.setHeaderText("Added ");
                    String s ="Product Added Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                     clearscr();
                   
                 }
                 else
                     
                 {
                      infoalert.setTitle("success");
                    infoalert.setHeaderText("Added ");
                    String s ="Product Addition Operation UnSuccessfully ";
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

    @FXML
    private void oncatitemselected(ActionEvent event) {
       String cat=catlistbox.getValue();
        if(cat.equalsIgnoreCase("choose Category"))
        {
            subcatlistbox.getItems().clear(); 
            subcatlistbox.setValue("No Sub Category"); 
        }
        else
            
        {
        try 
        {
         
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from subcategory where catid= ? ";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               
               
               //System.out.println("name = "+jComboBox1.getSelectedItem());
               String id=cat.substring(0,cat.indexOf(','));
               
                st.setString(1,id);
                ResultSet result=st.executeQuery();
                 
                ObservableList<String> scatdata=FXCollections.observableArrayList();
                 if(result.next())
                 {
                    // jComboBox2.removeAllItems();
                    do
                     {
                     String i=result.getString("subcatid");
                     String n=result.getString("subcatname");
                     String item=i+","+n;
                   
                      scatdata.add(item);
                     
                     } while(result.next());
                    subcatlistbox.setValue("choose Sub Category");    //to set default value
                    subcatlistbox.setItems(scatdata);
                     
                    
                 }
                 else
                 {
                    subcatlistbox.getItems().clear(); 
                     subcatlistbox.setValue("No Sub Category"); 
                     
                     
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
            String s = "Error in database" + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
        }
    }

    private void fetch_data() {
    try 
        {
         
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from product";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
                 ResultSet result=st.executeQuery();
                 
                 
                 /// (,,,,)
                 
                 
                ObservableList<productCls> userdata=FXCollections.observableArrayList();
                ptable.getItems().clear();
                 if(result.next())
                 {
                    do
                     {
                     String a1=result.getString("prodid");
                     String a2=result.getString("CatID");
                     String a3=result.getString("SubCatId");
                     String a4=result.getString("ProdName");
                     String a5=result.getString("Price");
                     String a6=result.getString("qty");
                     
                     userdata.add(new productCls(a1,a2,a3,a4,a5,a6));///adding data into category list
                        
                     } while(result.next());
                    
                        ptable.setItems(userdata);
                    
                 }
           }
           catch(Exception e)
           {
               erralert.setTitle("Some Error in fetching data for table");
            String s = "Error in querey " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
           
       
        } 
        catch (Exception ex) 
        {
             erralert.setTitle("Some Error in fetching data from table");
            String s = "Error in database connection " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        }     
    
    }

    private void get_data_of_table()
    {
        
        ///category combobox
        ObservableList listdata = catlistbox.getItems();
        String data;
        for(Object i:listdata)
        {
            data=(String) i;
            String id=data.substring(0,data.indexOf(','));
            if(id.trim().equalsIgnoreCase(t_cid))
            {
                catlistbox.setValue(data);
                break;
            }
        }
        
        prodidbox.setText(t_prodid);
        prodnamebox.setText(t_pname);
        prodpricebox.setText(t_price);
        prodqtybox.setText(t_stock);
          ///Subcategory combobox
        ObservableList sublistdata = subcatlistbox.getItems();
        String sdata;
        for(Object i:sublistdata)
        {
            sdata=(String) i;
            String id=sdata.substring(0,sdata.indexOf(','));
            if(id.trim().equalsIgnoreCase(t_sid))
            {
                subcatlistbox.setValue(sdata);
                break;
            }
        }
           deletebtn.setDisable(false);
          updatetbn.setDisable(false);
        
        
     
    }

 Alert confalert = new Alert(Alert.AlertType.CONFIRMATION);
    @FXML
    private void deletefunc(ActionEvent event) {
        confalert.setTitle("deletion");
        confalert.setHeaderText("confirmation ");
        String s2 ="Do you really  want to delete ?? ";
        confalert.setContentText(s2);
        
        Optional<ButtonType> cresult = confalert.showAndWait();
        if (cresult.get() == ButtonType.OK){
    // ... user chose OK

        try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="delete from product where prodid=?";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               
                   st.setString(1, prodidbox.getText());
                
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                     infoalert.setTitle("deletion");
                    infoalert.setHeaderText("Success");
                    String s ="Data deletion Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    clearscr();
                 }
           }
           catch(Exception e)
           {
               erralert.setTitle("Deletion Error");
            String s = "Error in querey " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("deletion Error");
            String s = "Error in database connection " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
    }
    }

    @FXML
    private void updatefunc(ActionEvent event) 
    {
           try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="update product set CatID=?,SubCatId=?,ProdName=?,Price=?,qty=? where prodid=?";
                      
               
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               
               String cat=catlistbox.getValue().toString();
               String id=cat.substring(0,cat.indexOf(','));
                st.setString(1,id);
                
               cat=subcatlistbox.getValue().toString();
               id=cat.substring(0,cat.indexOf(','));
               st.setString(2,id);
                
                
                
                st.setString(3,prodnamebox.getText());
                st.setString(4,prodpricebox.getText());
                st.setString(5,prodqtybox.getText());
                st.setString(6, prodidbox.getText());
                
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                      infoalert.setTitle("success");
                    infoalert.setHeaderText("Updatation ");
                    String s ="Product Updated Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                     clearscr();
                   
                 }
                 else
                     
                 {
                      infoalert.setTitle("success");
                    infoalert.setHeaderText("Updation ");
                    String s ="Product Updation Operation UnSuccessfully ";
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
    
}
