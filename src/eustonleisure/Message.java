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
import java.util.HashMap;
import java.util.StringTokenizer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

/**
 *
 * @author josefbenassi
 */
abstract public class Message {
    
    
    protected void changeSmsOrTweetContent(String fileRead, String fileWrite, String mContent, Label messageID, TextField sender) throws FileNotFoundException, IOException{
    
    
        
    String csvFile = fileRead;
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";
    
    HashMap<String,String> textwords = new HashMap<>();
         
    br = new BufferedReader(new FileReader(csvFile));
       
    while ((line = br.readLine()) != null) {
                   
          String[] csv_file = line.split(cvsSplitBy); 
                   textwords.put(csv_file[0],csv_file[1]);
               }
           
          StringTokenizer st = new StringTokenizer(mContent," \t\n\r\f,.:;?![]'",true);
          StringBuilder   sb = new StringBuilder();
        
          while(st.hasMoreTokens()){
               String token = st.nextToken();
               sb.append(textwords.getOrDefault(token,token)).append("");   
            }
        
          writeToJsonFileNotEmail(messageID,sender, sb, fileWrite);
        
    }
    
    protected void writeToJsonFileNotEmail(Label messageID, TextField sender,StringBuilder messageContent, String file){
    
     
    JSONObject obj = new JSONObject();  
    obj.put("MID", messageID.getText());
    obj.put("Sender",sender.getText());
    obj.put("Subject","n/a");
    obj.put("Message",messageContent.toString());
    
        
       
      try{ 
       
            File fileJson = new File(file); 

            if (!fileJson.exists()) {
            fileJson.createNewFile();
            }

            try (FileWriter fw = new FileWriter(fileJson.getAbsoluteFile(),true); BufferedWriter bw = new BufferedWriter(fw)) {
               
                bw.append(obj.toJSONString()+"\r");
                bw.flush();
            }
            System.out.println("Successfully Copied JSON Object to File...");

            } catch (IOException e) {
        }
    
    
   
   }

    protected void GenerateMessageID(String msgInitial,Label label){
    
        long timeSeed = System.nanoTime(); 

        double randSeed = Math.random() * 1000; 

        long midSeed = (long) (timeSeed * randSeed);
        
        String s = midSeed + "";
        String subStr = s.substring(0, 9);

        int finalSeed = Integer.parseInt(subStr);    // integer value   
       
        label.setText(msgInitial+Integer.toString(finalSeed));            
    }

    protected void changeEmailContent(String emailContent,Label messageID, TextField sender, TextField subject, String file){
            
        String replace = emailContent.replaceAll("poo.[^poo]*.cocm", "<URL Quarintined> ");
        String replaceAll = replace.replaceAll("zhttp:c//wccww.[^htcctp:cc.]*.co.uck", "<URL Quarintined> ");
        
        writeToJsonFileEmail(messageID,sender, subject,replaceAll,file);
       
    
    
    
    }
 
    protected void writeToJsonFileEmail(Label messageID, TextField sender,TextField subject,String messageContent, String file){
    
   
        
    JSONObject obj = new JSONObject();  
    obj.put("MID", messageID.getText());
    obj.put("Sender",sender.getText());
    obj.put("Subject",subject.getText());
    obj.put("Message",messageContent);
    
        
       
      try{ 
       
            File fileJson = new File(file); 

            if (!fileJson.exists()) {
            fileJson.createNewFile();
            }

            try (FileWriter fw = new FileWriter(fileJson.getAbsoluteFile(),true); BufferedWriter bw = new BufferedWriter(fw)) {
               
                bw.append(obj.toJSONString()+"\r");
                bw.flush();
            }
            System.out.println("Successfully Copied JSON Object to File...");

            } catch (IOException e) {
        }
    
    
   
   }
    
    protected void changeEmailContentSir(String emailContent,Label messageID, TextField sender, TextField subject, TextField sirNumber, TextField sirDescription, String file){
            
        String replace = emailContent.replaceAll("poo.[^poo]*.cocm", "<URL Quarintined> ");
        String replaceAll = replace.replaceAll("zhttp:c//wccww.[^htcctp:cc.]*.co.uck", "<URL Quarintined> ");
        
        writeToJsonFileEmailSir(messageID,sender, subject,replaceAll,sirNumber,sirDescription,file);
       
    
    
    
    }
    
    protected void writeToJsonFileEmailSir(Label messageID, TextField sender,TextField subject,String messageContent,TextField sirNumber, TextField sirDescription, String file){
    
   
        
    JSONObject obj = new JSONObject();  
    obj.put("MID", messageID.getText());
    obj.put("Sender",sender.getText());
    obj.put("Subject",subject.getText());
    obj.put("Message","|"+sirNumber.getText()+" "+sirDescription.getText()+"|"+" "+messageContent);
    
        
       
      try{ 
       
            File fileJson = new File(file); 

            if (!fileJson.exists()) {
            fileJson.createNewFile();
            }

            try (FileWriter fw = new FileWriter(fileJson.getAbsoluteFile(),true); BufferedWriter bw = new BufferedWriter(fw)) {
               
                bw.append(obj.toJSONString()+"\r");
                bw.flush();
            }
            System.out.println("Successfully Copied JSON Object to File...");

            } catch (IOException e) {
        }
    
    
   
   }
  
    
    protected void showWordCount(Label label, TextArea mContent){
    
        int wc =   mContent.getText().length();
        label.setText(Integer.toString(wc));
        mContent.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
        String oldValue, String newValue) {
        label.setText(Integer.toString(newValue.length()).trim());
        }
    
        });
    
    
    
    }

}
