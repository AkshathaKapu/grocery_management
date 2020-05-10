
package myprojectfxml;

import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static myprojectfxml.mynames.pass;
import static myprojectfxml.mynames.path;
import static myprojectfxml.mynames.place;
import static myprojectfxml.mynames.user;

public class ManageCategoryController implements Initializable {

    String table_c_id=null,table_c_name=null;
    @FXML
    private TextField catname;
 Alert infoalert = new Alert(AlertType.INFORMATION);
 Alert erralert = new Alert(AlertType.ERROR);
 
 
    @FXML
    private TableView<categorycls> cattable;///categorycls class should be made first
    @FXML
    private TableColumn<categorycls, String> catidbox;
    @FXML
    private TableColumn<categorycls, String> catnamebox;
    @FXML
    private Button deletebutton;
    @FXML
    private Button updatebtn;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        //saying box to get value from class's data member
        catidbox.setCellValueFactory(new PropertyValueFactory<categorycls,String>("catid_c"));
        catnamebox.setCellValueFactory(new PropertyValueFactory<categorycls,String>("catname_c"));
       
        ///
  
       //double click on table row and getting username
       cattable.setRowFactory(tv -> {
            TableRow<categorycls> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    categorycls rowData = row.getItem();
                    table_c_id = rowData.getCatid_c();
                    table_c_name=rowData.getCatname_c();
                    updatebtn.setDisable(false);
                    deletebutton.setDisable(false);
                    catname.setText(table_c_name);
                    
                }
            });
            return row ;
        });
        ///
        
        
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
               String qry="select * from category ";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
                 ResultSet result=st.executeQuery();
                ObservableList<categorycls> catlist=FXCollections.observableArrayList();
                 if(result.next())
                 {
                    do
                     {
                     String  i=result.getString("CatID");
                     String n=result.getString("CatName");
                                     
                      catlist.add(new categorycls(i,n));///adding data into category list
                        
                     } while(result.next());
                     ///get dummy data
                        cattable.setItems(catlist);
                    
                 }
                 else
                 {
                     System.out.println("***********E4************");
                     erralert.setTitle("empty data");
                    String s = "no data " + "No Data added in category table yet";
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

    @FXML
    private void addCatName(ActionEvent event) 
    {
        try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="insert into category (CatName) values(?)";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                st.setString(1,catname.getText());
                System.out.println("cat name = "+catname.getText());
                 
                
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                     infoalert.setTitle("success");
                    infoalert.setHeaderText("Added ");
                    String s ="Category Added Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    fetchdata();
                    catname.setText("");
                    updatebtn.setDisable(true);
                    deletebutton.setDisable(true);
                   
                 }
                 else
                     
                 {
                     infoalert.setTitle("unsuccess");
                    infoalert.setHeaderText("obstacle ");
                    String s ="Category Addition Operation Unsuccessful ";
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
        catch (Exception e) 
        {
             erralert.setTitle("Some Error");
            String s = "Error in database " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
      
        
        
        
        
        
        
        
    }

 Alert confalert = new Alert(Alert.AlertType.CONFIRMATION);
    @FXML
    private void deletebtn(ActionEvent event) 
    {
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
               String qry="delete from  category where CatID=?";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
               
                   st.setString(1, table_c_id);
                
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                     infoalert.setTitle("deletion");
                    infoalert.setHeaderText("Success");
                    String s ="Data deletion Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    catname.setText("");
                    updatebtn.setDisable(true);
                    deletebutton.setDisable(true);
                    fetchdata();
                 }
                 else
                     
                 {
                     infoalert.setTitle("error");
                    infoalert.setHeaderText("UNSuccess");
                    String s ="This Category Doesn't exsits ";
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
    private void updatecatname(ActionEvent event) 
    {
        try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="update category set CatName=? where CatID=?";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
               
                st.setString(1, catname.getText());
                st.setString(2, table_c_id);
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                     infoalert.setTitle("Updation");
                    infoalert.setHeaderText("Success");
                    String s ="Category Updated Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    catname.setText("");
                    updatebtn.setDisable(true);
                    deletebutton.setDisable(true);
                    fetchdata();
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

}
