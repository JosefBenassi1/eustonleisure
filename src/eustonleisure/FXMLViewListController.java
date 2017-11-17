/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eustonleisure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author josefbenassi
 */
public class FXMLViewListController implements Initializable {

 @FXML
 TextArea textArea,textArea2,textArea3;
 @FXML
 Button backButton;
 
  
    
    
 @Override
 public void initialize(URL url, ResourceBundle rb) {
        
     try {
         getHashtag();
         getMentions();
         getURL();
     } catch (ParseException ex) {
         Logger.getLogger(FXMLViewListController.class.getName()).log(Level.SEVERE, null, ex);
     }
        
    } 
        
 private void getMentions(){
        
 //String yourString = "hi #how are #and #hello you you";
 JSONObject obj;
 String fileName = "/Users/josefbenassi/Documents/jsonjoe.json";
 HashMap<String,Integer> list = new HashMap<>();
 ArrayList<String> mine = new ArrayList<>();

// This will reference one line at a time
String line = null;

     try {
        // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

        // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            obj = (JSONObject) new JSONParser().parse(line);
            String findhash = (String)obj.get("Message");
            
            Matcher matcher = Pattern.compile("@\\s*(\\w+)").matcher(findhash);
            while (matcher.find()) {
            mine.add(Arrays.toString(matcher.group(1).split(","))) ;
            
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
            System.out.println("Unable to open file '" + fileName + "'");                
        }catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(FXMLViewListController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    
    
    
    
    }
        
 private void getHashtag(){
    
    
       //String yourString = "hi #how are #and #hello you you";
        JSONObject obj;
        String fileName = "/Users/josefbenassi/Documents/jsonjoe.json";
        HashMap<String,Integer> list = new HashMap<>();
        ArrayList<String> mine = new ArrayList<>();

            // This will reference one line at a time
            String line = null;

        try {
        // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

        // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            obj = (JSONObject) new JSONParser().parse(line);
            String findhash = (String)obj.get("Message");
            
            Matcher matcher = Pattern.compile("#\\s*(\\w+)").matcher(findhash);
            while (matcher.find()) {
            mine.add(Arrays.toString(matcher.group(1).split(","))) ;
            
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
            
//            String formattedString2  = list.keySet().toString().replace("[","").replace("]","").replace("{", "").replace("}", "");
//            String formattedString3  = list.values().toString().replace("[","").replace("]","").replace("{", "").replace("}", "");
//            System.out.print(formattedString +" ");
//            sb.append(formattedString).reverse();
                
    }
    
    
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(FXMLViewListController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    
    
    
    
    
    }
 
 private void getURL() throws ParseException{
     
        JSONObject obj;
        String fileName = "/Users/josefbenassi/Documents/jsonjoe.json";
        HashMap<String,Integer> list = new HashMap<>();
        ArrayList<String> mine = new ArrayList<>();
        
         String line = null;
         try {
        // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

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
            System.out.println("Unable to open file '" + fileName + "'");                
        }catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    
     
     }
        
             
@FXML
private void backButtonClick(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene tableViewScene = new Scene(tableViewParent,1000,600);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }  
       
    
    
 }
    
    
    

