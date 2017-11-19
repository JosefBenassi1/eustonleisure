 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eustonleisure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author josefbenassi
 */
abstract public class JsonReader {
    
 protected    String URLRegex = "\\b((?:https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])";
 protected    String hashRegex = "#\\s*(\\w+)";
 protected    String mentionRegex = "@\\s*(\\w+)";
 protected    String sirRegex= "\\|(.*?)\\|";
 protected    String file = "/Users/josefbenassi/Documents/jsonjoe.json";
    
protected void  getTrendsForList(String file, String jsonName, String regex, String symbol, String trendDescription,TextArea textArea){

        
        JSONObject obj;
        String fileName = file;
        HashMap<String,Integer> list = new HashMap<>();
        ArrayList<String> mine = new ArrayList<>();

        // This will reference one line at a timekjn;k
        String line = null;

        try {
              // FileReader reads text files in the default encoding.
               FileReader fileReader = new FileReader(fileName);

              // Always wrap FileReader in BufferedReader.
               BufferedReader bufferedReader = new BufferedReader(fileReader);

               while((line = bufferedReader.readLine()) != null) {
               
               obj = (JSONObject) new JSONParser().parse(line);
               String findhash = (String)obj.get(jsonName);

               Matcher matcher = Pattern.compile(regex).matcher(findhash);
               while (matcher.find()) {
               mine.add(Arrays.toString(matcher.group(1).split(","))) ;

               }
           } 

               Set<String> unique = new HashSet<>(mine);
               unique.forEach((key) -> {list.put(key,Collections.frequency(mine, key));});
               bufferedReader.close(); 

               Object[] a = list.entrySet().toArray();
               Arrays.sort(a, new Comparator() {public int compare(Object o1, Object o2) {return ((Map.Entry<String, Integer>) o2).getValue()
               .compareTo(((Map.Entry<String, Integer>) o1).getValue());}});
               
               for (Object e : a) {

                String name = symbol+" "+((Map.Entry<String, Integer>) e).getKey() +" " + trendDescription+ ((Map.Entry<String, Integer>) e).getValue()+" times"+"\n"+"\n";
                String replace = name.replace("[","").replace("]","").replace("{", "").replace("}", "");
                textArea.appendText(replace);

               }
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


 protected void selectFile(Label label){
        
       
    FileChooser fc = new FileChooser();
    fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json Files","*.json"));
    File selectedFile = fc.showOpenDialog(null);
    
    if (selectedFile != null) {
    label.setText(selectedFile.getAbsolutePath());
    }else{
    label.setText("File Not valid");
     }
     
   }
 
 
 
 //"\\|(.*?)\\|"
}
