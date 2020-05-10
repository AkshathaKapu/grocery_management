
package myprojectfxml;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import static myprojectfxml.mynames.pass;
import static myprojectfxml.mynames.path;
import static myprojectfxml.mynames.place;
import static myprojectfxml.mynames.user;

public class AddUserController implements Initializable {
    File file=null;
String newfilename="defaultimage.jpg",oldfilename="";
    
 Alert infoalert = new Alert(Alert.AlertType.INFORMATION);
 Alert erralert = new Alert(Alert.AlertType.ERROR);
 Alert confalert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    private TextField unamebox;
    @FXML
    private TextArea addressbox;
    @FXML
    private TextField usernamebox;
    @FXML
    private ComboBox<String> usertype_listbox;
    @FXML
    private DatePicker DobBox;
    @FXML
    private ToggleGroup gendergroup;
    @FXML
    private TextField phnobox;
    @FXML
    private TableColumn<userCls, String> name_col;
    @FXML
    private TableColumn<userCls,String> address_col;
    @FXML
    private TableColumn<userCls,String> gender_col;
    @FXML
    private TableColumn<userCls,String> username_col;
    @FXML
    private TableColumn<userCls,String> type_col;
    @FXML
    private RadioButton malebox;
    @FXML
    private RadioButton femalebox;
    @FXML
    private PasswordField passwordbox;
    @FXML
    private TableView<userCls> usertable;
    @FXML
    private Button del_btn;
    @FXML
    private Button upd_btn;
    @FXML
    private ImageView userImgView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Usertypelistbox data        
        ObservableList<String> userdata=FXCollections.observableArrayList();
        userdata.add("Admin");
        userdata.add("Employee");
        
        usertype_listbox.setValue("Choose User Type");    //to set default value
        usertype_listbox.setItems(userdata);
        
        ///connect table rows with class
       name_col.setCellValueFactory(new PropertyValueFactory<userCls,String>("uname_c"));
       address_col.setCellValueFactory(new PropertyValueFactory<userCls,String>("address_c"));
       gender_col.setCellValueFactory(new PropertyValueFactory<userCls,String>("gender_c"));
       username_col.setCellValueFactory(new PropertyValueFactory<userCls,String>("username_c"));
       type_col.setCellValueFactory(new PropertyValueFactory<userCls,String>("type_c"));
  
       //double click on table row and getting username
       usertable.setRowFactory(tv -> {
            TableRow<userCls> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    userCls rowData = row.getItem();
                    String x = rowData.getUsername_c();
                    System.out.println("Double click on: "+x);
                    fetchdata_for_box(x);
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
               String qry="select * from usertable";
               com.mysql.jdbc.PreparedStatement st=(com.mysql.jdbc.PreparedStatement) conn.prepareStatement(qry);
                 ResultSet result=st.executeQuery();
                 
                ObservableList<userCls> userdata=FXCollections.observableArrayList();
                usertable.getItems().clear();
                 if(result.next())
                 {
                    do
                     {
                     String n=result.getString("name");
                     String a=result.getString("address");
                     String g=result.getString("gender");
                     String un=result.getString("username");
                     String t=result.getString("type");
                     
                     userdata.add(new userCls(n,a,g,un,t));///adding data into category list
                        
                     } while(result.next());
                    
                        usertable.setItems(userdata);
                    
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
             erralert.setTitle("Some Error in fetching data fro table");
            String s = "Error in database connection " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
}

    @FXML
    private void del_btn(ActionEvent event) 
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
            
            if(!oldfilename.equals("defaultimage.jpg"))
            {
                File x=new File("empimages//"+oldfilename);
                x.delete();
            }
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="delete from usertable where username=?";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               
                   st.setString(1, usernamebox.getText());
                
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                     infoalert.setTitle("deletion");
                    infoalert.setHeaderText("Success");
                    String s ="Data deletion Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    clearscr();
                    del_btn.setDisable(true);
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
    private void update_btn(ActionEvent event) 
    {
         if(!newfilename.equals(oldfilename))
        {
            saveimg();
            if(!oldfilename.equals("defaultimage.jpg"))
            {
                File x=new File("empimages//"+oldfilename);
                x.delete();
            }
            
            
        }
      
        try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="update usertable set name=?,address=?,gender=?,password=?,type=?,dob=?,phone=?,pic=? where username=?";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                st.setString(1,unamebox.getText());
                st.setString(2, addressbox.getText());
                if(malebox.isSelected())
                {
                    st.setString(3,malebox.getText());
                }
                else
                {
                    st.setString(3,femalebox.getText());
                }
                 
                 st.setString(4,passwordbox.getText());
                 st.setString(5,usertype_listbox.getValue().toString());
                 
                java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(DobBox.getValue());
                String d3=gettedDatePickerDate.toString();
                st.setString(6, d3); 
                             
                st.setString(7, phnobox.getText());
                st.setString(8,newfilename);
                st.setString(9, usernamebox.getText());
                

                
               
                
                
                 int result=st.executeUpdate();

                 if(result>0)
                 {
                    infoalert.setTitle("Upadation");
                    infoalert.setHeaderText("Success");
                    String s ="Data Updated Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    clearscr();
                 }
                 else
                     
                 {
                     infoalert.setTitle("Upadation");
                    infoalert.setHeaderText("UNSuccess");
                    String s ="Data Updated UnSuccessfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                 }
           }
           catch(Exception e)
           {
               erralert.setTitle("Updation Error");
            String s = "Error in querey " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
           
       
        } 
        catch (Exception ex) 
        {
             erralert.setTitle("Updation Error");
            String s = "Error in databse " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
        
        
    }
Boolean  validateuser()
{
    int flag=0;
    erralert.setTitle("Validation Error");
    String s=" ";
    if(unamebox.getText().length()<3)
    {
        flag=1;
        s ="enter proper name";
    }
    else if(usernamebox.getText().length()<3)
    {
        flag=1;
        s ="enter proper user name";
    }
    else if(passwordbox.getText().length()<5)
    {
        flag=1;
        s ="make your password strong , atleast give five characters";
    }
    else if(addressbox.getText().isEmpty())
    {
        flag=1;
        s ="enter your address";
    }
    else if(malebox.isSelected()==false && femalebox.isSelected()==false)
    {
        flag=1;
        s ="select gender";
    }
    else if(usertype_listbox.getValue().equals("Choose User Type"))
    {
        flag=1;
        s ="choose any user type";
    }
    else if(DobBox.getValue()==null)
    {
        flag=1;
        s ="choose your date of birth";
    }
    else if(phnobox.getText().length()!=10)
    {
        flag=1;
        s ="enter proper phone number ";
        
    }
//    else if(jTextField2.getText().contains("@")&&jTextField2.getText().contains("."))
//    {
//        JOptionPane.showMessageDialog(rootPane,"enter correct email id");
//        return false;
//    }
    else
    {
        flag=2;
    }
    if(flag==1)
    {
        
    erralert.setContentText(s);
    erralert.showAndWait();
    return false;
    }
    else
        
    {
        return true;
    }
    
}
    @FXML
    private void save_btn(ActionEvent event) 
    {
       Boolean ans=validateuser();
      if(ans==true)
      {
          saveimg(); ///database check karo
                    infoalert.setTitle("Saving img");
                    infoalert.setHeaderText("Success");
                    String s2 ="Image Saved Successfully ";
                    infoalert.setContentText(s2);
                    infoalert.show();
        try 
        {
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="insert into usertable values(?,?,?,?,?,?,?,?,?)";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
                st.setString(1,unamebox.getText());
                st.setString(2, addressbox.getText());
                if(malebox.isSelected())
                {
                    st.setString(3,"male");
                }
                else
                {
                    st.setString(3,femalebox.getText());
                }
                 st.setString(4,usernamebox.getText());
                 st.setString(5,passwordbox.getText());
                 st.setString(6, usertype_listbox.getValue().toString());
                 
                java.sql.Date gettedDatePickerDate = java.sql.Date.valueOf(DobBox.getValue());
                String d3=gettedDatePickerDate.toString();
                st.setString(7, d3);       
                st.setString(8, phnobox.getText());   
                st.setString(9,newfilename);

                
                
                int result=st.executeUpdate();

                 if(result>0)
                 {
                     
                     infoalert.setTitle("Saving");
                    infoalert.setHeaderText("Success");
                    String s ="Data Saved Successfully ";
                    infoalert.setContentText(s);
                    infoalert.show();
                    
                    clearscr();
                 }
            
           
              
               
           }
           catch(Exception e)
           {
               erralert.setTitle("Data Saving Error");
            String s = "Error in querey " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
           
       
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("Data Saving Error");
            String s = "Error in database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
    }
      
    }

    @FXML
    private void search_name_btn(ActionEvent event) {
        try 
        {
         
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from usertable where name like ?";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               
                 st.setString(1,unamebox.getText()+"%");

                
                 ResultSet result=st.executeQuery();
                 
                ObservableList<userCls> userdata=FXCollections.observableArrayList();
                usertable.getItems().clear();
                 if(result.next())
                 {
                    do
                     {
                     String n=result.getString("name");
                     String a=result.getString("address");
                     String g=result.getString("gender");
                     String un=result.getString("username");
                     String t=result.getString("type");
                     
                     userdata.add(new userCls(n,a,g,un,t));///adding data into category list
                        
                     } while(result.next());
                    
                        usertable.setItems(userdata);
                    
                 }
                 else
                 {
                     infoalert.setTitle("Searching");
                    infoalert.setHeaderText("No Data Found");
                    String s ="INvalid UserName ";
                    infoalert.setContentText(s);
                    infoalert.show();
                     clearscr();
//                     jButton3.setEnabled(false);
//                     jButton4.setEnabled(false);
                 }
                 
                
               
           }
           catch(Exception e)
           {
               erralert.setTitle("Data searching error");
            String s = "Error in Query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
           
       
        } 
        catch (Exception ex) 
        {
             erralert.setTitle("Data Searching error");
            String s = "Error in database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
        
    }

    @FXML
    private void fetch_username_btn(ActionEvent event) 
    {
     String u = usernamebox.getText();
        fetchdata_for_box(u);//calling function so i don't have to write code twice
    }
    void clearscr() 
    {
        fetchdata();
        unamebox.clear();
        addressbox.clear();
        malebox.selectedProperty().set(false);
        femalebox.selectedProperty().set(false);
        usernamebox.clear();
        passwordbox.clear();
        usertype_listbox.setValue("Choose User Type");
        phnobox.clear();
        DobBox.setValue(null);
        BufferedImage bufferedImage;
        try 
        {
            bufferedImage = ImageIO.read( new File("empimages\\defaultimage.jpg"));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            userImgView.setImage(image);//putting on imageview

        } catch (IOException ex) {
            erralert.setTitle("clearing image error");
                    String s = "default image not found" ;
                    erralert.setContentText(s);
                    erralert.showAndWait();
        }
                
        
    }

    private void fetchdata_for_box(String un) 
    {
        try 
        {
         
           Connection conn=null;
           conn=(Connection) DriverManager.getConnection(path+place, user, pass);
           try
           {
               String qry="select * from usertable where username=?";
               PreparedStatement st=(PreparedStatement) conn.prepareStatement(qry);
               
                 st.setString(1, un);

                
                 ResultSet result=st.executeQuery();

                 if(result.next())
                 {
                     unamebox.setText(result.getString("name"));
                    if(result.getString("gender").equalsIgnoreCase("male"))
                    {
                         malebox.setSelected(true);
                    }
                    else
                    {
                        femalebox.setSelected(true);
                    }
                     passwordbox.setText(result.getString("password"));
                     usertype_listbox.setValue(result.getString("type"));
                     addressbox.setText(result.getString("address"));
                     usernamebox.setText(un);
                     phnobox.setText(result.getString("phone"));
                     //converting date into locale date to set datepicker
                     LocalDate localDate = result.getDate("dob").toLocalDate();
                    //set in DatePicker
                    DobBox.setValue(localDate);
                    String filename=result.getString("pic");
                     
                    BufferedImage bufferedImage = ImageIO.read( new File("empimages\\"+filename));
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    userImgView.setImage(image);//putting on imageview
            
            
                    oldfilename=filename;
                    newfilename=filename;
                     
                     del_btn.setDisable(false);
                     upd_btn.setDisable(false);

                    }
                 else
                 {
                     erralert.setTitle("usertable");
                    String s = "in Valid USername" ;
                    erralert.setContentText(s);
                    erralert.showAndWait();
                    clearscr();
                    del_btn.setDisable(true);
                    upd_btn.setDisable(true);
                 }
                 
                
               
           }
           catch(Exception e)
           {
               erralert.setTitle("usertable");
            String s = "Error in query " + e;
            erralert.setContentText(s);
            erralert.showAndWait();
           }
           
       
        } 
        catch (Exception ex) 
        {
            erralert.setTitle("usertable");
            String s = "Error in database " + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        } 
        
        
        
        
        
        

        
    }

    @FXML
    private void uploadImgBtn(ActionEvent event)
    {
        
//        File file=null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Your Picture");
        System.out.println("filechooser reference = "+fileChooser); 
        
        //Set extension filter(to set choosable extensions)
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        //Show open file dialog
        
        file = fileChooser.showOpenDialog(null);
        System.out.println("file is exists ="+file.exists());
       
        try 
        {
            if(file!=null)
            {
            //getting filename and path
            String myfilepath = file.getPath();//path of selected image
            String myfilename = file.getName();//name of selected image
            System.out.println("file path : "+myfilepath);
            System.out.println("file name : "+myfilename);
            
            //making image
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            userImgView.setImage(image);//putting on imageview
            
            newfilename = new java.util.Date().getTime() + myfilename;//Date.getTime() added for unique file name.
            System.out.println("new filename = "+newfilename);
//          saveimg(file,newfilename);
        }
        } catch (IOException ex) {
             erralert.setTitle("uploading error");
            String s = "Error in image feching" + ex;
            erralert.setContentText(s);
            erralert.showAndWait();
        }
    }
    
void saveimg()
{
    int i;
    FileInputStream rd = null;
    FileOutputStream outs = null;

    try 
    {
        rd = new FileInputStream(file);
        outs = new FileOutputStream("empimages//"+newfilename);//writing the new file in 'Images' folder, in the project
        
    //always remember to put this folder outside 'scr' folder i.e directly under project
        byte[] b = new byte[2048];
        while ((i = rd.read(b)) !=-1) 
        {
        outs.write(b, 0, i); //copying from pictures to empimages byte by byte
       
        }
        
    }
    catch (Exception e)
    {
        erralert.setTitle("uploading error");
        String s = "Error in image feching" + e;
        erralert.setContentText(s);
        erralert.showAndWait();
    }
    finally 
    {
    try 
    {
        rd.close();
        outs.close();

    } 
    catch (Exception e) 
    {
        erralert.setTitle("uploading error");
        String s = "Error in image feching" + e;
        erralert.setContentText(s);
        erralert.showAndWait();
    }
    }
}

   
}
