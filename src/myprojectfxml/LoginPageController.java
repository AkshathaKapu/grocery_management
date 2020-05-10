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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import static myprojectfxml.mynames.pass;
import static myprojectfxml.mynames.path;
import static myprojectfxml.mynames.place;
import static myprojectfxml.mynames.user;

public class LoginPageController implements Initializable {

    public String Utype="";
    Alert infoalert = new Alert(Alert.AlertType.INFORMATION);
    Alert erralert = new Alert(Alert.AlertType.ERROR);
    @FXML
    private TextField usernamebox;
    @FXML
    private PasswordField passwordbox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void check_login(ActionEvent event) 
    {
         try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from usertable where username=? and password=? ";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               
                st.setString(1,usernamebox.getText());
                st.setString(2,passwordbox.getText());
                ResultSet result=st.executeQuery();
                
                 if(result.next())
                 {
                    Utype=result.getString("type");
                    Stage stage1 = (Stage) usernamebox.getScene().getWindow();
                    stage1.close();
                    FXMLLoader myloader=new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
                    Parent root = myloader.load();
                    FXMLDocumentController fcontroller=(FXMLDocumentController)myloader.getController();
                    fcontroller.getusertype(usernamebox.getText(),Utype);
                    Scene scene = new Scene(root);
                    Stage stage=new Stage();//new stage for next window
                    stage.setScene(scene);
                    stage.getIcons().add(new Image("images/logo6.png"));
                    stage.setTitle("Grocery Management");
                    stage.setMaximized(true);
                    stage.show();
                 }
                 else
                 {
                    infoalert.setTitle("in login page Erorr");
                    infoalert.setHeaderText("Unsuccessfull");
                    String s ="Invalid Username Or Password ";
                    infoalert.setContentText(s);
                    infoalert.show();
                 }
           }
           catch(Exception e)
           {
               erralert.setTitle("Error");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("Error");
            String s = "Error in database connection " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
    }
}
