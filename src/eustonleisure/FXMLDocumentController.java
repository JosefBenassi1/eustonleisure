/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eustonleisure;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author josefbenassi
 */
public class FXMLDocumentController implements Initializable {
    
   
    
    @FXML
    private void clickSendMessage(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent _send_message_parent = FXMLLoader.load(getClass().getResource("FXMLMessageType.fxml"));
        Scene _send_message_scene = new Scene(_send_message_parent,1000,600);
        Stage _app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        _app_stage.setScene(_send_message_scene);
        _app_stage.show();
    }
    
     
    @FXML
    private void clickViewMessages(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("FXMLRead.fxml"));
        Scene tableViewScene = new Scene(tableViewParent,1000,600);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    @FXML
    private void clickViewList(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("FXMLViewList.fxml"));
        Scene tableViewScene = new Scene(tableViewParent,1000,600);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
     
    @FXML
    private void clickTranslateFile(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("FXMLReadFile.fxml"));
        Scene tableViewScene = new Scene(tableViewParent,1000,600);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
