package myprojectfxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static myprojectfxml.mynames.pass;
import static myprojectfxml.mynames.path;
import static myprojectfxml.mynames.place;
import static myprojectfxml.mynames.user;
public class ManageSupplierController implements Initializable {

    String t_sid=null,t_sname=null;
    @FXML
    private TextField suppnamebox;

    
 Alert infoalert = new Alert(Alert.AlertType.INFORMATION);
 Alert erralert = new Alert(Alert.AlertType.ERROR);
    @FXML
    private Button deletebutton;
    @FXML
    private Button updatebtn;
    @FXML
    private TableColumn<supplierCls, String> supid_box;
    @FXML
    private TableColumn<supplierCls, String> supname_box;
    @FXML
    private TableView<supplierCls> stable;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //saying box to get value from class's data member
        supid_box.setCellValueFactory(new PropertyValueFactory<supplierCls,String>("suppid_c"));
        supname_box.setCellValueFactory(new PropertyValueFactory<supplierCls,String>("supname_c"));
       
        ///
  
       //double click on table row and getting username
       stable.setRowFactory(tv -> {
            TableRow<supplierCls> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    supplierCls rowData = row.getItem();
                    t_sid= rowData.getSuppid_c();
                    t_sname=rowData.getSupname_c();
                    
                    updatebtn.setDisable(false);
                    deletebutton.setDisable(false);
                    
                    
                    suppnamebox.setText(t_sname);
                    
                }
            });
            return row ;
        });
        ///
        
        
        fetchdata();
    }    

    @FXML
    private void addsupplier(ActionEvent event) {
         try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="insert into supplier (supname) values(?)";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                st.setString(1,suppnamebox.getText());
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                     infoalert.setTitle("success");
                    infoalert.setHeaderText("INsertion ");
                    String s ="Supplier Addition Operation successful ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    clearscr();
                 }
                 else
                     
                 {
                     infoalert.setTitle("unsuccess");
                    infoalert.setHeaderText("obstacle ");
                    String s ="Supplier Addition Operation Unsuccessful ";
                    infoalert.setContentText(s);
                    infoalert.show();
                 }
               
           }
           catch(Exception e)
           {
               erralert.setTitle("Some Error");
            String s = "Error in querey " + e;
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

 Alert confalert = new Alert(Alert.AlertType.CONFIRMATION);
    @FXML
    private void deletebtn(ActionEvent event) {
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
               String qry="delete from  supplier  where supid=?";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
               
                   st.setString(1, t_sid);
                
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
                 else
                     
                 {infoalert.setTitle("error");
                    infoalert.setHeaderText("UNSuccess");
                    String s ="This Supplier Doesn't exsits ";
                    infoalert.setContentText(s);
                    infoalert.show();
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
    private void updatefunc(ActionEvent event) {
         try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="update supplier set supname=? where supid=?";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
               
                st.setString(1, suppnamebox.getText());
                st.setString(2, t_sid);
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                     infoalert.setTitle("Updation");
                    infoalert.setHeaderText("Success");
                    String s ="Category Updated Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    clearscr();
                 }
                 else
                     
                 {
                     infoalert.setTitle("error");
                    infoalert.setHeaderText("Unsuccessful");
                    String s ="error ";
                    infoalert.setContentText(s);
                    infoalert.show();
                     
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

    private void fetchdata() {
    try 
        {
         
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from supplier ";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
                 ResultSet result=st.executeQuery();
                ObservableList<supplierCls> catlist=FXCollections.observableArrayList();
                 if(result.next())
                 {
                    do
                     {
                     String  i=result.getString("supid");
                     String n=result.getString("supname");
                                     
                      catlist.add(new supplierCls(i,n));///adding data into category list
                        
                     } while(result.next());
                        stable.setItems(catlist);
                    
                 }
                 else
                 {
                     erralert.setTitle("empty data");
                    String s = "no data " + "No Data added in Supplier table yet";
                    erralert.setContentText(s);
                    erralert.showAndWait();
                 }
           }
           catch(Exception e)
           {
               erralert.setTitle("Some Error");
            String s = "Error in querey " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
           
       
        } 
        catch (Exception ex) 
        {
             erralert.setTitle("Some Error");
            String s = "Error in database connection " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
        
        
        
        
            
    }

    private void clearscr() {
        suppnamebox.clear();
        updatebtn.setDisable(true);
        deletebutton.setDisable(true);
        fetchdata();
    }
    
}
