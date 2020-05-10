
package myprojectfxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import static myprojectfxml.mynames.pass;
import static myprojectfxml.mynames.path;
import static myprojectfxml.mynames.place;
import static myprojectfxml.mynames.user;

/**
 * FXML Controller class
 *
 * @author gtb student
 */
public class ManageSubCategoryController implements Initializable {

    String table_c_id=null,table_s_id=null,table_s_name=null;
    
    @FXML
    private ComboBox   catlistbox;
    
 Alert infoalert = new Alert(Alert.AlertType.INFORMATION);
 Alert erralert = new Alert(Alert.AlertType.ERROR);
    @FXML
    private TextField subcatnamebox;
    @FXML
    private Button deletebtn;
    @FXML
    private Button updatebtn;
    @FXML
    private TableView<SubCatCls> subcatTable;
    @FXML
    private TableColumn<SubCatCls,String> catID_box;
    @FXML
    private TableColumn<SubCatCls,String> subcatid_box;
    @FXML
    private TableColumn<SubCatCls,String> subcatname_box;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       fetchcatdata();
         catID_box.setCellValueFactory(new PropertyValueFactory<SubCatCls,String>("catid_c"));
        subcatid_box.setCellValueFactory(new PropertyValueFactory<SubCatCls,String>("subcatid_c"));
        subcatname_box.setCellValueFactory(new PropertyValueFactory<SubCatCls,String>("subcatname_c"));

       //double click on table row and getting username
       subcatTable.setRowFactory(tv -> {
            TableRow<SubCatCls> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    SubCatCls rowData = row.getItem();
                    table_c_id = rowData.getCatid_c();
                    table_s_id=rowData.getSubcatid_c();
                    table_s_name=rowData.getSubcatname_c();
                    
                    fetch_table_data();
                      subcatnamebox.setText(table_s_name);
                    
                    
                    updatebtn.setDisable(false);
                    deletebtn.setDisable(false);
                    
                    
                    
                }
            });
            return row ;
        });
       fetchdata();
    }    
void fetchdata()
{
    try 
        {
         
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from subcategory ";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
                 ResultSet result=st.executeQuery();
                ObservableList<SubCatCls> catlist=FXCollections.observableArrayList();
                 if(result.next())
                 {
                    do
                     {
                     String  ci=result.getString("CatID");
                     String si=result.getString("SubCatID");
                     String n=result.getString("SubCatName");              
                      catlist.add(new SubCatCls(ci, si, n));///adding data into category list
                        
                     } while(result.next());
                     ///get dummy data
                        subcatTable.setItems(catlist);
                    
                 }
                 else
                 {
                     erralert.setTitle("empty data");
                    String s = "no data " + "No Data added in sub category table yet";
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
    
 Alert confalert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private void delsubcat(ActionEvent event) {
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
               String qry="delete from  subcategory where catid=? and SubCatID=? ";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
               
                st.setString(1,table_c_id);
                st.setString(2,table_s_id);
                
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                     infoalert.setTitle("deletion");
                    infoalert.setHeaderText("Success");
                    String s ="Data deletion Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    subcatnamebox.setText("");
                    fetchdata();
                    updatebtn.setDisable(true);
                    deletebtn.setDisable(true);
                    catlistbox.setValue("choose Category"); 
                 }
                 else
                     
                 {infoalert.setTitle("error");
                    infoalert.setHeaderText("UNSuccess");
                    String s ="This Sub Category Doesn't exsits, Check carefully ";
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
    private void AddSubCat(ActionEvent event) {
               try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="insert into subcategory (catid,subCatName) values(?,?)";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               
               String cat=(String) catlistbox.getValue();
               System.out.println("name = "+cat);
               String id=cat.substring(0,cat.indexOf(','));
               
               
                st.setString(1,id);
                st.setString(2,subcatnamebox.getText());
                System.out.println("name = "+subcatnamebox.getText());
                System.out.println("id = "+id);
                
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                     infoalert.setTitle("success");
                    infoalert.setHeaderText("INsertion in SUBCATEGORY ");
                    String s ="Category Addition Operation successful ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    subcatnamebox.setText("");
                    fetchdata();
                    updatebtn.setDisable(true);
                    deletebtn.setDisable(true);
                    catlistbox.setValue("choose Category"); 
                 }
                 else
                     
                 {
                       infoalert.setTitle("unsuccess");
                    infoalert.setHeaderText("obstacle ");
                    String s ="Category Addition Operation Unsuccessful ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    fetchdata();
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
        catch (Exception e) 
        {
             erralert.setTitle("Some Error");
            String s = "Error in querey " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 

    }

    @FXML
    private void updatesubcat(ActionEvent event) 
    {
        try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="update subcategory set CatID=?,SubCatName=? where SubCatID=?";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
               
               String cat=(String) catlistbox.getValue();
               System.out.println("name = "+cat);
               String id=cat.substring(0,cat.indexOf(','));
               
               
                st.setString(1,id);
                st.setString(2,subcatnamebox.getText());
                st.setString(3, table_s_id);
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                     infoalert.setTitle("Updation");
                    infoalert.setHeaderText("Success");
                    String s ="Category Updated Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    subcatnamebox.setText("");
                    updatebtn.setDisable(true);
                    deletebtn.setDisable(true);
                    fetchdata();
                    catlistbox.setValue("choose Category"); 
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

    private void fetch_table_data() 
    {
//        System.out.println("list data "+catlistbox.getItems());
        ObservableList listdata = catlistbox.getItems();
        
        String data;
        for(Object i:listdata)
        {
//            System.out.println("data : "+i);
            data=(String) i;
            String id=data.substring(0,data.indexOf(','));
            if(id.trim().equalsIgnoreCase(table_c_id))
            {
                catlistbox.setValue(data);
                break;
            }
            
        }
      
        
    }

    
}
