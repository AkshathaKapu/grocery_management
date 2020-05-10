
package myprojectfxml;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static myprojectfxml.mynames.pass;
import static myprojectfxml.mynames.path;
import static myprojectfxml.mynames.place;
import static myprojectfxml.mynames.user;

/**
 * FXML Controller class
 *
 * @author gtb student
 */
public class SellReportController implements Initializable {
    
    
 Alert infoalert = new Alert(Alert.AlertType.INFORMATION);
 Alert erralert = new Alert(Alert.AlertType.ERROR);
 
    @FXML
    private DatePicker startdatebox;
    @FXML
    private DatePicker enddatebox;
    @FXML
    private TableColumn<Reportcls, String> purId_col;
    @FXML
    private TableColumn<Reportcls, String> SuppName_Col;
    @FXML
    private TableColumn<Reportcls, String> billDate_Col;
    @FXML
    private TableColumn<Reportcls, String> billAmt_Col;
    @FXML
    private TableView<Reportcls> reportTableView;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        purId_col.setCellValueFactory(new PropertyValueFactory<Reportcls,String>("purchId_c"));
        SuppName_Col.setCellValueFactory(new PropertyValueFactory<Reportcls,String>("sname_c"));
        billDate_Col.setCellValueFactory(new PropertyValueFactory<Reportcls,String>("billdate_c"));
        billAmt_Col.setCellValueFactory(new PropertyValueFactory<Reportcls,String>("billamt_c"));

       //on double click on table
       //double click on table row and getting username
       reportTableView.setRowFactory(tv -> {
            TableRow<Reportcls> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Reportcls rowData = row.getItem();
                    String x = rowData.getPurchId_c();
                    System.out.println("Double click on: "+x);
                    opensupplierReport(x);
                }
            });
            return row ;
        });
    }    

    @FXML
    private void searchReport(ActionEvent event) 
    {
        try
        {
            com.mysql.jdbc.Connection myconn=null;
           myconn=(com.mysql.jdbc.Connection) DriverManager.getConnection(path+place, user, pass);
            try
            {
               String q="select * from sell where billdate between ? and ?";
               PreparedStatement myst = myconn.prepareStatement(q);
               
               //converting datepicker date into sql date 
                java.sql.Date gettedDatePickerDate1 = java.sql.Date.valueOf(startdatebox.getValue());
                java.sql.Date gettedDatePickerDate2 = java.sql.Date.valueOf(enddatebox.getValue());

                //converting sql date into string
                String stdt=gettedDatePickerDate1.toString();
                String enddt=gettedDatePickerDate2.toString();
                
                System.out.println("start date : "+stdt);
                System.out.println("end date : "+enddt);

                
               myst.setString(1, stdt);
               myst.setString(2, enddt);
               ResultSet res=myst.executeQuery(); 
                ObservableList<Reportcls> rdata=FXCollections.observableArrayList();
               reportTableView.getItems().clear();
               if(res.next())
               {
                  do
                  {
                      String purid = res.getString("sellid");
                      String supp = res.getString("custname");
                      String edate = res.getString("billdate");
                      String amt = res.getString("totalbill");
                      
                      rdata.add(new Reportcls(purid,supp,edate,amt));///adding data into category list
                        
                  }
                  while(res.next());
                  reportTableView.setItems(rdata);
               }
               else
               {
                   infoalert.setTitle("Sale Report ");
                    infoalert.setHeaderText("Empty Table ");
                    String s ="No Data Found";
                    infoalert.setContentText(s);
                    infoalert.show();
               }
             }
            catch(Exception e)
            {
                erralert.setTitle("Report");
            String s = "Error " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
            }
            finally
            {
                myconn.close();
            }
        }
        catch(Exception e)
        {
           erralert.setTitle("Some Error");
            String s = "Error in querey " + e.getMessage();
            erralert.setContentText(s);
            erralert.showAndWait();
        }  
    }


    private void opensupplierReport(String x) 
    {
     try {
         Stage stage1 = (Stage) startdatebox.getScene().getWindow();
         FXMLLoader myloader=new FXMLLoader(getClass().getResource("CustomerReport.fxml"));
         Parent root = myloader.load();
         CustomerReportController fcontroller=(CustomerReportController)myloader.getController();
         fcontroller.getsellId(x);
         Scene scene = new Scene(root);
         Stage stage=new Stage();
         stage.initStyle(StageStyle.UTILITY);
         stage.setScene(scene);
         stage.show();
     } catch (IOException ex) {
         Logger.getLogger(PurchaseReportController.class.getName()).log(Level.SEVERE, null, ex);
     }
        
    }

    
}
