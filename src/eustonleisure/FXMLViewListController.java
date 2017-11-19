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
public class FXMLViewListController extends JsonReader implements Initializable {

    @FXML
    TextArea textArea,textArea2,textArea3,textArea4;
    @FXML
    Button backButton;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       getTrendsForList(file, "Message", hashRegex, "#", "has been tweeted: ", textArea);
       getTrendsForList(file, "Message", mentionRegex, "@", "has been mentiond:  ", textArea2);
       getTrendsForList(file, "Message", URLRegex, "URL", "has been found and removed:  ", textArea3);
       getTrendsForList(file, "Message", sirRegex, "SIR code & Nature", "has been emailed:  ", textArea4);
        
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
    
    
    

