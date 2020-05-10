/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprojectfxml;

import javafx.print.PrinterJob;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
public class SupplierReportController implements Initializable {
    
 Alert infoalert = new Alert(Alert.AlertType.INFORMATION);
 Alert erralert = new Alert(Alert.AlertType.ERROR);
    @FXML
    private Label purIdBox;
    @FXML
    private Label suppnamebox;
    @FXML
    private TableView<supplierReportcls> supplierReprtTable;
    @FXML
    private TableColumn<supplierReportcls, String> pname_col;
    @FXML
    private TableColumn<supplierReportcls, String> price_col;
    @FXML
    private TableColumn<supplierReportcls, String> qty_col;
    @FXML
    private TableColumn<supplierReportcls, String> totalPrice_col;
    @FXML
    private TableColumn<supplierReportcls, String> billDate_col;
    @FXML
    private AnchorPane mainpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pname_col.setCellValueFactory(new PropertyValueFactory<supplierReportcls,String>("pname_c"));
        price_col.setCellValueFactory(new PropertyValueFactory<supplierReportcls,String>("price_c"));
        qty_col.setCellValueFactory(new PropertyValueFactory<supplierReportcls,String>("qty_c"));
        billDate_col.setCellValueFactory(new PropertyValueFactory<supplierReportcls,String>("billDate_c"));
        totalPrice_col.setCellValueFactory(new PropertyValueFactory<supplierReportcls,String>("totalPrice_c"));
        
    }    

    @FXML
    private void printoutreport(ActionEvent event) 
    {
        Printer printer = Printer.getDefaultPrinter();
            Stage dialogStage = new Stage(StageStyle.DECORATED);            
            PrinterJob job = PrinterJob.createPrinterJob(printer);
                if (job != null) 
                {                 
                    
                    boolean showDialog = job.showPageSetupDialog(dialogStage);
                    if (showDialog) 
                    {                        
                        mainpane.setScaleX(0.50);//on giving negitive value it give flippd table as print
                        mainpane.setScaleY(0.50);
                        mainpane.setTranslateX(-200);
                        mainpane.setTranslateY(-70);
                    boolean success = job.printPage(mainpane);
                        if (success) 
                        {
                             job.endJob(); 
                        } 
                        mainpane.setTranslateX(0);
                        mainpane.setTranslateY(0);               
                        mainpane.setScaleX(1.0);
                        mainpane.setScaleY(1.0);                                              
                    }    
                }
    }

    void getpurchId(String x) {
        fetchpurchasedetail(x);
    }
     void fetchpurchasedetail(String pid)
{
    try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
              
               
               String qry="select * from purchasedetail where purchaseid = ?";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               st.setString(1, pid);
                ResultSet result=st.executeQuery();
//                DefaultTableModel model=(DefaultTableModel) jTable1.getModel();
                ObservableList<supplierReportcls> sdata=FXCollections.observableArrayList();
                
                 supplierReprtTable.getItems().clear();
                purIdBox.setText(pid);
                 if(result.next())
                 {
                     suppnamebox.setText(result.getString("supname"));
                     do
                     {
                         String pn=result.getString("productname");
                         String p=result.getString("price");
                         String q=result.getString("qty");
                         String t=result.getString("totalamt");
                         String bd=result.getString("billdate");
                         
                         sdata.add(new supplierReportcls(pn, p, q, t,bd));
                         
                     }while(result.next());
                     supplierReprtTable.setItems(sdata);
                    
                 }
                 else
                 {
                   suppnamebox.setText("no data ");
                 }
           }
           catch(Exception e)
           {
                 erralert.setTitle("Supplier Report");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
        } 
        catch (Exception ex) 
        {
                erralert.setTitle("supplier Report");
            String s = "Error in Query " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 

    
} 
    


    
}
