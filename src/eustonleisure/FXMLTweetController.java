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
import org.json.simple.parser.JSONParser;

/**
 * FXML Controller class
 *
 * @author josefbenassi
 */
public class FXMLTweetController implements Initializable {

    @FXML      
    TextField sender;
    @FXML
    TextArea mContent ;
    @FXML
    Button   sendBtn;
    @FXML
    Label    messageID, wordcount;
    @Override
    
    
    public void initialize(URL url, ResourceBundle rb) {
       
        GenerateMessageId(messageID);
        
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
    private void clickSendBtn(ActionEvent event) throws IOException{
      
      int wc = 140;
      String check = "";
      
        
      if(check.equals(mContent.getText()) || mContent.getText().length() >= wc){
      
      Alert.disply("Error", "You either have no conent in your message or have exceeded 140 character limit");
   
      }
      
    
      if(!mContent.getText().equals(check) && mContent.getText().length() <= wc ){
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
       
        label.setText("T"+Integer.toString(finalSeed));
       
    }
       
    
    private void writeToJsonFile(StringBuilder sb) throws IOException{
         
        
           
        JSONObject obj = new JSONObject();
        obj.put("MID", messageID.getText());
        obj.put("Sender",sender.getText());
        obj.put("Subject","n/a");
        obj.put("Message",sb.toString());
        
       
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
       
           
    private void messageContains(String messageContent) throws IOException{
     
        String csvFile = "/Users/josefbenassi/Documents/textwords.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        //String example = " Hello charles LOL, LTM, LTNS LYLAS";
       
        
                
         HashMap<String,String> textwords = new HashMap<>();
         
         
       
         br = new BufferedReader(new FileReader(csvFile));
            
             while ((line = br.readLine()) != null) {
                   
                   String[] csv_file = line.split(cvsSplitBy); 
                   textwords.put(csv_file[0],csv_file[1]);
                 
             }
            
         
           
           StringTokenizer st = new StringTokenizer(messageContent," \t\n\r\f,.:;?![]'",true);
           
           StringBuilder sb = new StringBuilder();
           while(st.hasMoreTokens())
           {
               String token = st.nextToken();
               
               sb.append(textwords.getOrDefault(token,token)).append("");   
            }
               
               System.out.println(sb);
               writeToJsonFile(sb);
           }
    

}

