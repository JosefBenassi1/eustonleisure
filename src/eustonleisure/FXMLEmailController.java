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
public class FXMLEmailController  implements Initializable{

    @FXML      
    TextField subject ,sender,sirNumber,sirDescription;
    @FXML TextArea mContent ;
    @FXML
    Button sendBtn;
    @FXML
    Label messageID, wordcount;
    @FXML
    CheckBox sirCheckBox;
    
   
    
    
    
     
    // method that does shit when email padge is loaded.
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        GenerateMessageId(messageID);
        sirNumber.setEditable(false);
        sirDescription.setEditable(false);
        
        
        int wc =   mContent.getText().length();
        wordcount.setText(Integer.toString(wc));
        
        mContent.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
                String oldValue, String newValue) {

            wordcount.setText(Integer.toString(newValue.length()).trim());
        }
    });
       
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
       String check = " ";
        
       if(check.equals(mContent.getText()) || mContent.getText().length() >= wc){
      
          Alert.disply("Error", "You either have no conent in your message or have exceeded 1028 character limit");
   
        }if(!sender.getText().contains("@")&& !sender.getText().contains(".com")){
      
      
          Alert.disply("Error", "please enter a proper email address");
      
      }if(sirCheckBox.isSelected() && sirNumber.getText().equals(check)&& sirDescription.getText().equals(check)&& !sender.getText().contains("@")&& !sender.getText().contains(".com")){
      
           Alert.disply("Error", "SIR email type has been selected. Make sure all boxes are not empty and email is in correct format. We only except .com emails");
      
      }if(!sirCheckBox.isSelected() && subject.getText().equals(check)){
         Alert.disply("Error", "please fill in subject feild");

      }if(!sirCheckBox.isSelected() && sender.getText().contains("@")&& !sender.getText().contains(".com")){
         Alert.disply("Error", "only accept .com emails");

      }
      
      if(!sirCheckBox.isSelected() && subject.getText().equals(check)&&!sender.getText().contains("@")&& !sender.getText().contains(".com")){
      
             Alert.disply("Error", "please fill in subject and make sure emial is in correct format. We only accept .com emails");
      }
      
       
      
    
      if(!mContent.getText().equals(check) && mContent.getText().length() >5 && !subject.getText().equals(check) && sender.getText().contains("@")&& sender.getText().contains(".com") ){
          
          messageContains(mContent.getText());
          
          Parent _send_message_parent = FXMLLoader.load(getClass().getResource("FXMLSentScreen.fxml"));
          Scene _send_message_scene = new Scene(_send_message_parent,1000,600);
          Stage _app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          _app_stage.setScene(_send_message_scene);
          _app_stage.show();
        }
      }
   
       
       private void GenerateMessageId(Label label){
       
           
        long timeSeed = System.nanoTime(); 

        double randSeed = Math.random() * 1000; 

        long midSeed = (long) (timeSeed * randSeed);
        
        String s = midSeed + "";
        String subStr = s.substring(0, 9);

        int finalSeed = Integer.parseInt(subStr);    // integer value   
       
        label.setText("E"+Integer.toString(finalSeed));
       
    }
       
       private void writeToJsonFile(String email) throws IOException{
         
        
           
        JSONObject obj = new JSONObject();
        obj.put("MID", messageID.getText());
        obj.put("Sender", sender.getText());
        obj.put("Subject", subject.getText());
        obj.put("Message", sirNumber.getText()+""+sirDescription.getText()+""+email);
       
      try{ 
       
            File file = new File("/Users/josefbenassi/Documents/jsonjoe.json"); 

            if (!file.exists()) {
            file.createNewFile();
            }

            try (FileWriter fw = new FileWriter(file.getAbsoluteFile(),true); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.append(obj.toJSONString()+"\r");
                bw.flush();
            }
            System.out.println("Successfully Copied JSON Object to File...");

            } catch (IOException e) {
        }

   }
       
    /**
     *
     * @param emailContent
     * @return
     * @throws IOException
     */
   private String messageContains(String emailContent) throws IOException{
     
        String replace = emailContent.replaceAll("www.[^www.]*.com", "<Quarintined> ");
        String replaceAll = replace.replaceAll("www.[^www.]*.co.uk", "<Quarintined> ");
        System.out.println(replaceAll);
               
               System.out.println(replaceAll);
               writeToJsonFile(replaceAll);
               return replaceAll;
     }
     
}
         
         
            

     

       
            
        
 
            
     
    
    
    
    
    
     
    

