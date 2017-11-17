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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author josefbenassi
 */
public class FXMLMessageTypeController implements Initializable {

    
     @FXML
    private void clickTypeEmail(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent _send_message_parent = FXMLLoader.load(getClass().getResource("FXMLEmail.fxml"));
        Scene _send_message_scene = new Scene(_send_message_parent,1000,600);
        Stage _app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        _app_stage.setScene(_send_message_scene);
        _app_stage.show();
        
    }

     @FXML
    private void clickTypeSms(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent _send_message_parent = FXMLLoader.load(getClass().getResource("FXMLSms.fxml"));
        Scene _send_message_scene = new Scene(_send_message_parent,1000,600);
        Stage _app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        _app_stage.setScene(_send_message_scene);
        _app_stage.show();
        
    }
    
      @FXML
        private void clickTypeTweet(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent _send_message_parent = FXMLLoader.load(getClass().getResource("FXMLTweet.fxml"));
        Scene _send_message_scene = new Scene(_send_message_parent,1000,600);
        Stage _app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        _app_stage.setScene(_send_message_scene);
        _app_stage.show();
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
