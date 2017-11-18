/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eustonleisure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

/**
 * FXML Controller class
 *
 * @author josefbenassi
 */
public class FXMLSmsController extends Message implements Initializable {

    
     
    @FXML      
    TextField sender;
    @FXML
    TextArea mContent ;
    @FXML
    Button sendBtn;
    @FXML
    Label messageID, wordcount;
    
    String fileWrite = "/Users/josefbenassi/Documents/jsonjoe.json";
    String fileRead  = "/Users/josefbenassi/Documents/textwords.csv";
    
    
    
    
    /**
     * initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        GenerateMessageID("S",messageID);
        showWordCount(wordcount, mContent);
    }    
    
    
     
    @FXML
    private void clickSendBtn(ActionEvent event) throws IOException{
      
      int wc = 140;
      String check = "";
      
      if(sender.getText().equals(check)){Alert.disply("Error","Sender Empty"); return;}
      
      if(!isValidMobile(sender.getText())){Alert.disply("Error","Please enter a valid Mobile Number eg/ +447735421616"); return;}
      
      if(mContent.getText().equalsIgnoreCase(check)){Alert.disply("Error","Your have not typed an Email!"); return;}
      
      if(mContent.getText().length() <=5){Alert.disply("Error","Please type a longer message"); return;}
          
          changeSmsOrTweetContent(fileRead,fileWrite, mContent.getText(), messageID, sender); 
          
          Parent _send_message_parent = FXMLLoader.load(getClass().getResource("FXMLSentScreen.fxml"));
          Scene _send_message_scene = new Scene(_send_message_parent,1000,600);
          Stage _app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          _app_stage.setScene(_send_message_scene);
          _app_stage.show();
        
       }
    
     private boolean isValidMobile(String s){      
     String regex="\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";      
     return s.matches(regex);//returns true if input and regex matches otherwise false;
    }

}
    
     

    

