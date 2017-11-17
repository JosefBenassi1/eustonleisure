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
public class FXMLReadFileController implements Initializable {

    
     @FXML private Label fileName;
     @FXML private TableView  <Table>         tableView;
     @FXML private TableColumn<Table, String> messageID;
     @FXML private TableColumn<Table, String> sender;
     @FXML private TableColumn<Table, String> subject;
     @FXML private TableColumn<Table, String> message;
     @FXML private TextArea textArea, textArea2, textArea3;
    
    
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
    
    getURL(fileName.getText());          
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
        
        getMentions();
        getHashtag();
        
        
    
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
            
            String replace = (String)obj.get("Message").toString().replaceAll("www.[^www.]*.com", "<Quarintined> ");
            replace1 = replace.replaceAll("www.[^www.]*.co.uk", "<Quarintined> ");
            replace2 = replace1.replaceAll("http://www.[^http://www.]*.co.uk", "<Quarintined>");
            replace3 = replace2.replaceAll("http://www.[^http://www.]*.com", "<Quarintined>");
               
            JSONObject object = new JSONObject();
            object.put("MID", (String)obj.get("MID"));
            object.put("Sender", (String)obj.get("Sender"));
            object.put("Subject", (String)obj.get("Subject"));
            object.put("Message", replace3);
       
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
               
            }else if(findMessageType.contains("E")&&findSubjectType.contains("SIR")){
                
              
                
                
            String replace = (String)obj.get("Message").toString().replaceAll("www.[^www.]*.com", "<Quarintined> ");
            replace1 = replace.replaceAll("www.[^www.]*.co.uk", "<Quarintined> ");
            replace2 = replace1.replaceAll("http://www.[^http://www.]*.co.uk", "<Quarintined>");
            replace3 = replace2.replaceAll("http://www.[^http://www.]*.com", "<Quarintined>");
               
            JSONObject object = new JSONObject();
            object.put("MID", (String)obj.get("MID"));
            object.put("Sender", (String)obj.get("Sender"));
            object.put("Subject", (String)obj.get("Subject"));
            object.put("Message", replace3);
       
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
  
     private void getMentions(){
        
 //String yourString = "hi #how are #and #hello you you";
 JSONObject obj;
 String getMentions = "/Users/josefbenassi/Documents/storetest.json";
 HashMap<String,Integer> list = new HashMap<>();
 ArrayList<String> mine = new ArrayList<>();

// This will reference one line at a time
String line = null;

     try {
        // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(getMentions);

        // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            obj = (JSONObject) new JSONParser().parse(line);
            String findmention = (String)obj.get("Message");
            
            Matcher matches = Pattern.compile("@\\s*(\\w+)").matcher(findmention);
            while (matches.find()) {
            mine.add(Arrays.toString(matches.group(1).split(","))) ;
            
            }
            
            if(matches.find()== false){
            
            textArea2.setText("No Mentions found...");
            
            
            }
            
            
        } 
        
            Set<String> unique = new HashSet<>(mine);
            unique.forEach((key) -> {
                list.put(key,Collections.frequency(mine, key));
            });
            bufferedReader.close(); 
            
            StringBuilder sb = new StringBuilder();
            String hash = null;
            
            //String formattedString = list.toString().replace("[","").replace("]","").replace("{", "").replace("}", "");
            Object[] a = list.entrySet().toArray();
            Arrays.sort(a, new Comparator() {public int compare(Object o1, Object o2) {return ((Map.Entry<String, Integer>) o2).getValue()
            .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            
            }
                 });
            
                   for (Object e : a) {
                    
                    String name = "@"+((Map.Entry<String, Integer>) e).getKey() + " has been mentioned "+ ((Map.Entry<String, Integer>) e).getValue()+" times"+"\n"+"\n";
                    String replace = name.replace("[","").replace("]","").replace("{", "").replace("}", "");
                    
                    textArea2.appendText(replace);
                
                    }
            
//            String formattedString2  = list.keySet().toString().replace("[","").replace("]","").replace("{", "").replace("}", "");
//            String formattedString3  = list.values().toString().replace("[","").replace("]","").replace("{", "").replace("}", "");
//            System.out.print(formattedString +" ");
//            sb.append(formattedString).reverse();
                
    }
    
    
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + getMentions + "'");                
        }catch(IOException ex) {
            System.out.println("Error reading file '" + getMentions + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(FXMLViewListController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
     
     private void getHashtag(){
    
    
       //String yourString = "hi #how are #and #hello you you";
        JSONObject obj;
        String getHash = "/Users/josefbenassi/Documents/storetest.json";
        HashMap<String,Integer> list = new HashMap<>();
        ArrayList<String> mine = new ArrayList<>();

            // This will reference one line at a time
            String line = null;

        try {
        // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(getHash);

        // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            obj = (JSONObject) new JSONParser().parse(line);
            String findhash = (String)obj.get("Message");
            
            Matcher matcher = Pattern.compile("#\\s*(\\w+)").matcher(findhash);
            while (matcher.find()) {
            mine.add(Arrays.toString(matcher.group(1).split(","))) ;
            
            }
            
             if(!matcher.find()){
            
            textArea.setText("No Hashtags found...");
            
            
            }
        } 
        
            Set<String> unique = new HashSet<>(mine);
            unique.forEach((key) -> {
                list.put(key,Collections.frequency(mine, key));
            });
            bufferedReader.close(); 
            
            StringBuilder sb = new StringBuilder();
            String hash = null;
            
            //String formattedString = list.toString().replace("[","").replace("]","").replace("{", "").replace("}", "");
            Object[] a = list.entrySet().toArray();
            Arrays.sort(a, new Comparator() {public int compare(Object o1, Object o2) {return ((Map.Entry<String, Integer>) o2).getValue()
            .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            
            }
                 });
                   for (Object e : a) {
                    
                    String name = "#"+((Map.Entry<String, Integer>) e).getKey() + " has been tagged "+ ((Map.Entry<String, Integer>) e).getValue()+" times"+"\n"+"\n";
                    String replace = name.replace("[","").replace("]","").replace("{", "").replace("}", "");
                    textArea.appendText(replace);
                
                    }
     
                
    }
    
    
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + getHash + "'");                
        }catch(IOException ex) {
            System.out.println("Error reading file '" + getHash + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(FXMLViewListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
     
     private void getURL(String file) throws ParseException{
     
        JSONObject obj;
        String getURL = file;
        HashMap<String,Integer> list = new HashMap<>();
        ArrayList<String> mine = new ArrayList<>();
        
         String line = null;
         try {
        // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(getURL);

        // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            obj = (JSONObject) new JSONParser().parse(line);
            String findURL = (String)obj.get("Message");
        
            Matcher matcher = Pattern.compile("\\b((?:https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])").matcher(findURL);
            while (matcher.find()) {
            mine.add(Arrays.toString(matcher.group(1).split(","))) ;
        }
        
     }
        Set<String> unique = new HashSet<>(mine);
        for (String key : unique) {
            list.put(key,Collections.frequency(mine, key));
        }
        
            
            StringBuilder sb = new StringBuilder();
            String hash = null;
            
            //String formattedString = list.toString().replace("[","").replace("]","").replace("{", "").replace("}", "");
            Object[] a = list.entrySet().toArray();
            Arrays.sort(a, new Comparator() {public int compare(Object o1, Object o2) {return ((Map.Entry<String, Integer>) o2).getValue()
            .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            
            }
                 });
                   for (Object e : a) {
                    
                    String name = "URL "+((Map.Entry<String, Integer>) e).getKey() + " has been found an removed "+ ((Map.Entry<String, Integer>) e).getValue()+" times"+"\n"+"\n";
                    String replaceme = name.replace("[","").replace("]","").replace("{", "").replace("}", "");
                    textArea3.appendText(replaceme);
                }
       }          
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + getURL + "'");                
        }catch(IOException ex) {
            System.out.println("Error reading file '" + getURL + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    
     
     }
}
