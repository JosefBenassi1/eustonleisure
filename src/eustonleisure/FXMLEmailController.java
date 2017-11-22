/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eustonleisure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
 import org.json.simple.JSONObject;


    /**
 * FXML Controller class
 *
 * @author josefbenassi
 */
    public class FXMLEmailController extends Message implements Initializable{

    @FXML      
    TextField subject ,sender,sirNumber,sirDescription;
    @FXML TextArea mContent ;
    @FXML
    Button sendBtn;
    @FXML
    Label messageID, wordcount;
    @FXML
    CheckBox sirCheckBox;
    
    String fileName = "/Users/josefbenassi/Documents/jsonjoe.json";
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        GenerateMessageID("E", messageID);
        sirNumber.setEditable(false);
        sirDescription.setEditable(false);
        
        showWordCount(wordcount, mContent);
       
 }  
  
    @FXML
    private void sirCheckBoxClick(ActionEvent event) throws IOException{
        subject.setText("");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        
        
        sirNumber.setEditable(true);
        sirDescription.setEditable(true);
        subject.appendText("SIR: "+dateFormat.format(date));
        subject.setEditable(false);
        
        if(!sirCheckBox.isSelected()){
        
        sirNumber.setEditable(false);
        sirDescription.setEditable(false);
        sirNumber.setText("");
        sirDescription.setText("");
        subject.setText("");
        subject.setEditable(true);
     
        }
    }
   
    @FXML
    private void clickSendBtn(ActionEvent event) throws IOException{
        
   int wc = 1028; 
   String check = "";

    String natureOfIncident = 
    "Theft of Properties\n" +
    "Staff Attack\n" +
    "Device Damage\n" +
    "Raid\n" +
    "Customer Attack\n" +
    "Staff Abuse\n" +
    "Bomb Threat\n" +
    "Terrorism\n" +
    "Suspicious Incident\n" +
    "Sport Injury\n" +
    "Personal Info Leak ";
  
    
   if(!sirCheckBox.isSelected()){
      
      if(sender.getText().equals(check)){Alert.disply("Error","Sender Empty"); return;}
      
      if(!isValidEmail(sender.getText())){Alert.disply("Error","Email is not valid. Example: josefbenassi@gmail.com"); return;}
      
      if(subject.getText().equals(check)){Alert.disply("Error","Subject empty"); return;}
      
      if (!isValidSubject(subject.getText())){Alert.disply("Error","Subject empty or contains numbers"); return;}
      
      if(mContent.getText().equalsIgnoreCase(check)){Alert.disply("Error","Your have not typed an Email!"); return;}
      
      if(mContent.getText().length() <=5){Alert.disply("Error","Please type a longer message"); return;}
      
      changeEmailContent(mContent.getText(), messageID.getText(), sender.getText(),subject.getText(), fileName);
    
   }
   if(sirCheckBox.isSelected()){
        
        if(sender.getText().equals(check)){Alert.disply("Error","Sender Empty"); return;}
      
        if(!isValidEmail(sender.getText())){Alert.disply("Error","Email is not valid. Example: josefbenassi@gmail.com"); return;}
        
        if(!isValidCenterCode(sirNumber.getText())){Alert.disply("Error","Please enter a valid cosde. Example: 66-222-555"); return;}
        
        if(!sirDescription.getText().equalsIgnoreCase("Theft of Properties")&&!sirDescription.getText().equalsIgnoreCase("Staff Attack")&&!sirDescription.getText().equalsIgnoreCase("Device Damage")
         &&!sirDescription.getText().equalsIgnoreCase("Raid")&&!sirDescription.getText().equalsIgnoreCase("Customer Attack")&&!sirDescription.getText().equalsIgnoreCase("Staff Abuse")
         &&!sirDescription.getText().equalsIgnoreCase("Terrorism")&&!sirDescription.getText().equalsIgnoreCase("Bomb Attack")&&!sirDescription.getText().equalsIgnoreCase("Suspicious Incident")&&!sirDescription.getText().equalsIgnoreCase("Sport Injury")
         &&!sirDescription.getText().equalsIgnoreCase("Personal Info Leak")){Alert.disply("Error","Nature of Incident must be one of the following:"+natureOfIncident); return; }
        
        if(mContent.getText().equalsIgnoreCase(check)){Alert.disply("Error","Your have not typed an Email!"); return;}
      
        if(mContent.getText().length() <=5){Alert.disply("Error","Please type a longer message"); return;}
        
        changeEmailContentSir(mContent.getText(), messageID.getText(), sender.getText(), subject.getText(), sirNumber.getText(), sirDescription.getText(), fileName);
   }
    
      
          
          
         
          
          Parent _send_message_parent = FXMLLoader.load(getClass().getResource("FXMLSentScreen.fxml"));
          Scene _send_message_scene = new Scene(_send_message_parent,1000,600);
          Stage _app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          _app_stage.setScene(_send_message_scene);
          _app_stage.show();
    
  }
 
    private boolean isValidSubject(String s){      
     String regex="[A-Za-z\\s]+";      
     return s.matches(regex);//returns true if input and regex matches otherwise false;
    }
    
    private boolean isValidEmail(String s){
     
     String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
     return s.matches(regex);
     
     }
   
    private boolean isValidCenterCode(String s){
     
     String regex = "(\\d+)-(\\d+)-(\\d+)";
     return s.matches(regex);
     
     }
     
   
 
  }
         
         
            

     

       
            
        
 
            
     
    
    
    
    
    
     
    

