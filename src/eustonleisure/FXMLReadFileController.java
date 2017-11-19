/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template storeFile, choose Tools | Templates
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
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author josefbenassi
 */
public class FXMLReadFileController extends JsonReader implements Initializable {

    
     @FXML private Label fileName;
     @FXML private TableView  <Table>         tableView;
     @FXML private TableColumn<Table, String> messageID;
     @FXML private TableColumn<Table, String> sender;
     @FXML private TableColumn<Table, String> subject;
     @FXML private TableColumn<Table, String> message;
     @FXML private TextArea textArea, textArea2, textArea3,textArea4;
    
    
     @Override
     public void initialize(URL url, ResourceBundle rb) {
        
         
    }  
    
    
     @FXML
     private void chooseFileBtnClick(ActionEvent event) throws FileNotFoundException{
     
        selectFile();
        File storeFile = new File("/Users/josefbenassi/Documents/storetest.json"); 
         
        try (PrintWriter writer = new PrintWriter(storeFile)) {
             writer.print("");
         }
        
    }
    
     @FXML
     private void translateClick(ActionEvent event) throws ParseException{
    
           
    changeFileFormat(fileName.getText());
    translateAndSaveFile(fileName.getText());
        
    messageID.setCellValueFactory(  new PropertyValueFactory<Table, String> ("messageID"));
    sender.setCellValueFactory   (  new PropertyValueFactory<Table, String> ("sender"   ));
    subject.setCellValueFactory  (  new PropertyValueFactory<Table, String> ("subject"  ));
    message.setCellValueFactory  (  new PropertyValueFactory<Table, String> ("message"  ));
        
        
        try {
            //load dummy data
            tableView.setItems(showFile());
        } catch (ParseException ex) {
            Logger.getLogger(FXMLReadController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       getTrendsForList(fileName.getText(), "Message", hashRegex, "#", "has been tweeted: ", textArea);
       getTrendsForList(fileName.getText(), "Message", mentionRegex, "@", "has been mentiond:  ", textArea2);
       getTrendsForList(fileName.getText(), "Message", URLRegex, "URL", "has been found and removed:  ", textArea3);
       getTrendsForList(fileName.getText(), "Message", sirRegex, "SIR code & Nature", "has been emailed:  ", textArea4);
        
    
    }
    
     private void selectFile(){
        
       
    FileChooser fc = new FileChooser();
    fc.getExtensionFilters().addAll(new ExtensionFilter("Json Files","*.json"));
    File selectedFile = fc.showOpenDialog(null);
    
    if (selectedFile != null) {
    fileName.setText(selectedFile.getAbsolutePath());
    }else{
     fileName.setText("File Not valid");
     }
     
   } 
     
     private void changeFileFormat(String file){
         
         
    // The name of the file to open.
    String fileToChange = file;
    String line= null;
    String replaceme =null;
    StringBuilder sb = new StringBuilder();
    StringBuilder sb2 = new StringBuilder();
               
    try {
         FileReader fileReader = new FileReader(fileToChange);
         BufferedReader bufferedReader = new BufferedReader(fileReader); 
         
         while ((line = bufferedReader.readLine()) != null) {
         
            sb2 = sb.append(line);
            replaceme = sb2.toString().replaceAll("\\}", "}\n");
         }  
                    
          
            bufferedReader.close();
            PrintWriter writer = new PrintWriter(fileToChange);
            writer.print(replaceme);
            writer.close();
      }
                
        catch(FileNotFoundException ex) {
        System.out.println("Unable to open file '" + fileToChange + "'");                
    }   catch(IOException ex) {
        System.out.println("Error reading file '" + fileToChange + "'");                  
        // Or we could just do this: 
        // ex.printStackTrace();
    }
 }
    
     private  ObservableList<Table> showFile () throws ParseException{
    
    ObservableList<Table> jsonFile = FXCollections.observableArrayList();    
    ArrayList<JSONObject> json=new ArrayList<JSONObject>();
    JSONObject obj;
    // The name of the storeFile to open.
    String fileToTranslate = "/Users/josefbenassi/Documents/storetest.json";

    // This will reference one line at a time
    String line = null;

    try {
        // FileReader reads text files in the default encoding.
        FileReader fileReader = new FileReader(fileToTranslate);

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
        
        obj = (JSONObject) new JSONParser().parse(line);
        json.add(obj);
        jsonFile.add(new Table((String)obj.get("MID"),(String)obj.get("Sender"),(String)obj.get("Subject"),(String)obj.get("Message")));
           // System.out.println((String)obj.get("Email ID")+":"+ (String)obj.get("Message"));
        }
        // Always close files.
        bufferedReader.close();         
    }
    catch(FileNotFoundException ex) {
        System.out.println("Unable to open file '" + fileToTranslate + "'");                
    }catch(IOException ex) {
        System.out.println("Error reading file '" + fileToTranslate + "'");                  
        // Or we could just do this: 
        // ex.printStackTrace();
    }
        
    
    return jsonFile;
 }
    
     private void translateAndSaveFile(String file) throws ParseException{
 
        
 JSONObject obj;   
 String jsonFile = file;
 String csvFile = "/Users/josefbenassi/Documents/textwords.csv";
 BufferedReader br = null;
 String line2 = "";
 String cvsSplitBy = ",";
 HashMap<String,String> textwords = new HashMap<>();
 String line = null;
 StringBuilder sbSMS = new StringBuilder();
 String replace1;
 String replace2;
 String replace3;
 
 


// This will reference one line at a time


     try {
        // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(jsonFile);

        // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            obj = (JSONObject) new JSONParser().parse(line);
            String findMessageType = (String)obj.get("MID");
            String findSubjectType = (String)obj.get("Subject");
            
            if(findMessageType.contains("S")){
                
            br = new BufferedReader(new FileReader(csvFile));
            
                while ((line2 = br.readLine()) != null) {
                   
                   String[] csv_file = line2.split(cvsSplitBy); 
                   textwords.put(csv_file[0],csv_file[1]);
                 }
                   
           StringTokenizer st = new StringTokenizer((String)obj.get("Message")," \t\n\r\f,.:;?![]'",true);
           
           
           while(st.hasMoreTokens())
           {
               String token = st.nextToken();
               
               sbSMS.append(textwords.getOrDefault(token,token)).append("");   
            }
            JSONObject object = new JSONObject();
            object.put("MID", (String)obj.get("MID"));
            object.put("Sender", (String)obj.get("Sender"));
            object.put("Subject", (String)obj.get("Subject"));
            object.put("Message", sbSMS);
       
      try{ 
       
            File storeFile = new File("/Users/josefbenassi/Documents/storetest.json"); 

            if (!storeFile.exists()) {
            storeFile.createNewFile();
            }

            try (FileWriter fw = new FileWriter(storeFile.getAbsoluteFile(),true); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.append(object.toJSONString()+"\r");
                bw.flush();
            }
            System.out.println("Successfully Copied JSON Object to File...");

            } catch (IOException e) {
        }
               
               System.out.println(sbSMS);
               
            }else if(findMessageType.contains("E")){
            
            String replace = (String)obj.get("Message").toString().replaceAll("\\b((?:https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])", "<URL Quarintined> ");
               
            JSONObject object = new JSONObject();
            object.put("MID", (String)obj.get("MID"));
            object.put("Sender", (String)obj.get("Sender"));
            object.put("Subject", (String)obj.get("Subject"));
            object.put("Message", replace);
       
      try{ 
       
            File storeFile = new File("/Users/josefbenassi/Documents/storetest.json"); 

            if (!storeFile.exists()) {
            storeFile.createNewFile();
            }

            try (FileWriter fw = new FileWriter(storeFile.getAbsoluteFile(),true); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.append(object.toJSONString()+"\r");
                bw.flush();
            }
            System.out.println("Successfully Copied JSON Object to File...");

            } catch (IOException e) {
        }
          
            }else if(findMessageType.contains("F")){
            
            br = new BufferedReader(new FileReader(csvFile));
            
                while ((line2 = br.readLine()) != null) {
                   
                   String[] csv_file = line2.split(cvsSplitBy); 
                   textwords.put(csv_file[0],csv_file[1]);
                 }
                   
           StringTokenizer st = new StringTokenizer((String)obj.get("Message")," \t\n\r\f,.:;?![]'",true);
           
           
           while(st.hasMoreTokens())
           {
               String token = st.nextToken();
               
               sbSMS.append(textwords.getOrDefault(token,token)).append("");   
            }
            JSONObject object = new JSONObject();
            object.put("MID", (String)obj.get("MID"));
            object.put("Sender", (String)obj.get("Sender"));
            object.put("Subject", (String)obj.get("Subject"));
            object.put("Message", sbSMS);
       
      try{ 
       
            File storeFile = new File("/Users/josefbenassi/Documents/storetest.json"); 

            if (!storeFile.exists()) {
            storeFile.createNewFile();
            }

            try (FileWriter fw = new FileWriter(storeFile.getAbsoluteFile(),true); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.append(object.toJSONString()+"\r");
                bw.flush();
            }
            System.out.println("Successfully Copied JSON Object to File...");

            } catch (IOException e) {
        }
               
               System.out.println(sbSMS);
            
            
            
            }else{
                
            JSONObject object = new JSONObject();
            object.put("MID", (String)obj.get("MID"));
            object.put("Sender", (String)obj.get("Sender"));
            object.put("Subject", (String)obj.get("Subject"));
            object.put("Message", (String)obj.get("Message"));
       
      try{ 
       
            File storeFile = new File("/Users/josefbenassi/Documents/storetest.json"); 

            if (!storeFile.exists()) {
            storeFile.createNewFile();
            }

            try (FileWriter fw = new FileWriter(storeFile.getAbsoluteFile(),true); BufferedWriter bw = new BufferedWriter(fw)) {
                bw.append(object.toJSONString()+"\r");
                bw.flush();
            }
            System.out.println("Successfully Copied JSON Object to File...");

            } catch (IOException e) {
        }
      
      }
            
   } 
        
}     
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + jsonFile + "'");                
        }catch(IOException ex) {
            System.out.println("Error reading file '" + jsonFile + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }
  
 }
