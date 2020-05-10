
package myprojectfxml;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 *
 * @author gtb student
 */
public class FXMLDocumentController implements Initializable {

    
    Alert confalert = new Alert(Alert.AlertType.CONFIRMATION);
    @FXML
    private TabPane miantabpane;
    int cattab=0,subcattab=0,supptab=0,prodtab=0,purtab=0,usertab=0,reporttab=0,selltab=0,selreporttab=0;
    @FXML
    private AnchorPane myanchorpane;
    @FXML
    private ImageView mybackimageview;
    String loginUserName="";
    @FXML
    private MenuItem categorymenubtn;
    @FXML
    private MenuItem prodmenubtn;
    @FXML
    private MenuBar mymenubar;
    @FXML
    private Pane Pane;
    @FXML
    private Menu employeemenu;
    
   public void getusertype(String un,String type)
   {
       loginUserName=un;
//       System.out.println("username =  =  "+loginUserName);
       
       if(!type.equalsIgnoreCase("Admin"))
       {
           categorymenubtn.setDisable(true);
           employeemenu.setVisible(false);
       }
   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        miantabpane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        miantabpane.prefWidthProperty().bind(Pane.widthProperty());
        miantabpane.prefHeightProperty().bind(Pane.heightProperty());
        mybackimageview.fitHeightProperty().bind(Pane.heightProperty());
        mybackimageview.fitWidthProperty().bind(Pane.widthProperty());
    }    

    @FXML
    private void openMgCategory(ActionEvent event) throws IOException
    {
        if(cattab==0)
        {
            Tab menutab=new Tab();
            menutab.setText("Category");
            menutab.setClosable(Boolean.TRUE);
            menutab.setContent(FXMLLoader.load(getClass().getResource("ManageCategory.fxml")));
            miantabpane.getTabs().add(menutab);
            miantabpane.getSelectionModel().select(menutab);
            cattab=1;
            menutab.setOnCloseRequest(e->{cattab=0;});
        }    
    }
    @FXML
    private void openMgSubCategory(ActionEvent event) throws IOException
    {
        if(subcattab==0)
        {
            Tab menutab=new Tab();
            menutab.setText("Sub Category");
            menutab.setClosable(Boolean.TRUE);
            
            menutab.setContent(FXMLLoader.load(getClass().getResource("ManageSubCategory.fxml")));
            miantabpane.getTabs().add(menutab);
            miantabpane.getSelectionModel().select(menutab);
           
            
            subcattab=1;
            menutab.setOnCloseRequest(e->{subcattab=0;});
        }    
    }

    @FXML
    private void openMgProduct(ActionEvent event) throws IOException
    {
        if(prodtab==0)
        {
            Tab menutab=new Tab();
            menutab.setText("Product");
            menutab.setClosable(Boolean.TRUE);
            menutab.setContent(FXMLLoader.load(getClass().getResource("ProductAdd.fxml")));
            miantabpane.getTabs().add(menutab);
            miantabpane.getSelectionModel().select(menutab);
            prodtab=1;
            menutab.setOnCloseRequest(e->{prodtab=0;});
        }    
    }

    @FXML
    private void openMgSupplier(ActionEvent event) throws IOException 
    {
        if(supptab==0)
        {
            Tab menutab=new Tab();
            menutab.setText("Supplier");
            menutab.setClosable(Boolean.TRUE);
            menutab.setContent(FXMLLoader.load(getClass().getResource("ManageSupplier.fxml")));
            miantabpane.getTabs().add(menutab);
            miantabpane.getSelectionModel().select(menutab);
            supptab=1;
            menutab.setOnCloseRequest(e->{supptab=0;});
        }    
    }

    @FXML
    private void openMgPurchase(ActionEvent event) throws IOException 
    {
        if(purtab==0)
        {
            Tab menutab=new Tab();
            menutab.setText("Purchase");
            menutab.setClosable(Boolean.TRUE);
            menutab.setContent(FXMLLoader.load(getClass().getResource("Purchase.fxml")));
            miantabpane.getTabs().add(menutab);
            miantabpane.getSelectionModel().select(menutab);
            purtab=1;
            menutab.setOnCloseRequest(e->{purtab=0;});
        }    
    }

    @FXML
    private void openAddUser(ActionEvent event) throws IOException 
    {
        if(usertab==0)
        {
            Tab menutab=new Tab();
            menutab.setText("Add User");
            menutab.setClosable(Boolean.TRUE);
            menutab.setContent(FXMLLoader.load(getClass().getResource("AddUser.fxml")));
            miantabpane.getTabs().add(menutab);
            miantabpane.getSelectionModel().select(menutab);
            usertab=1;
            menutab.setOnCloseRequest(e->{usertab=0;});
            
        } 
    }

    @FXML
    private void openPurReport(ActionEvent event) throws IOException 
    {
        if(reporttab==0)
        {
            Tab menutab=new Tab();
            menutab.setText("Purchase Report");
            menutab.setClosable(Boolean.TRUE);
            menutab.setContent(FXMLLoader.load(getClass().getResource("PurchaseReport.fxml")));
            miantabpane.getTabs().add(menutab);
            miantabpane.getSelectionModel().select(menutab);
            reporttab=1;
            menutab.setOnCloseRequest(e->{reporttab=0;});
        } 
    }

    @FXML
    private void logoutbtn(ActionEvent event) {
        
        confalert.setTitle("Logout");
        confalert.setHeaderText("confirmation ");
        String s2 ="Do you really  want to Logout ?? ";
        confalert.setContentText(s2);
        
        Optional<ButtonType> cresult = confalert.showAndWait();
        if (cresult.get() == ButtonType.OK)
        {
            Stage stage1;
            stage1 = (Stage) myanchorpane.getScene().getWindow();
            stage1.close();
        }
    }

    @FXML
    private void openMgsell(ActionEvent event) throws IOException {
        if(selltab==0)
        {
            Tab menutab=new Tab();
            menutab.setText("Sale");
            menutab.setClosable(Boolean.TRUE);
            menutab.setContent(FXMLLoader.load(getClass().getResource("Sell.fxml")));
            miantabpane.getTabs().add(menutab);
            miantabpane.getSelectionModel().select(menutab);
            selltab=1;
            menutab.setOnCloseRequest(e->{selltab=0;});
        }  
    }

    @FXML
    private void opensellReport(ActionEvent event) throws IOException {
        if(selreporttab==0)
        {
            Tab menutab=new Tab();
            menutab.setText("Sell Report");
            menutab.setClosable(Boolean.TRUE);
            menutab.setContent(FXMLLoader.load(getClass().getResource("SellReport.fxml")));
            miantabpane.getTabs().add(menutab);
            miantabpane.getSelectionModel().select(menutab);
            selreporttab=1;
            menutab.setOnCloseRequest(e->{selreporttab=0;});
        }  
    }

}
