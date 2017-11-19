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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author josefbenassi
 */
public class FXMLReadController implements Initializable {

        //configure the table
    @FXML private TableView<Table>           tableView;
    @FXML private TableColumn<Table, String> messageID;
    @FXML private TableColumn<Table, String> sender;
    @FXML private TableColumn<Table, String> subject;
    @FXML private TableColumn<Table, String> message;
  
    
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //set up the columns in the table
        messageID.setCellValueFactory(new PropertyValueFactory<Table, String>("messageID"));
        sender.setCellValueFactory(new PropertyValueFactory<Table, String>("sender"));
        subject.setCellValueFactory(new PropertyValueFactory<Table, String>("subject"));
        message.setCellValueFactory(new PropertyValueFactory<Table, String>("message"));
        
        
        try {
            //load dummy data
            tableView.setItems(getTableContent());
        } catch (ParseException | IOException ex) {
            Logger.getLogger(FXMLReadController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }    
    
 
    public ObservableList<Table>  getTableContent() throws ParseException, FileNotFoundException, IOException
    {
             
    ObservableList<Table> tableContent = FXCollections.observableArrayList();    
    ArrayList<JSONObject> json=new ArrayList<>();
    JSONObject obj;
    // The name of the file to open.
    String fileName = "/Users/josefbenassi/Documents/jsonjoe.json";

    // This will reference one line at a time
    String line = null;

    try {
        // FileReader reads text files in the default encoding.
        FileReader fileReader = new FileReader(fileName);

        try ( // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                obj = (JSONObject) new JSONParser().parse(line);
                json.add(obj);
                tableContent.add(new Table((String)obj.get("MID"),(String)obj.get("Sender"),(String)obj.get("Subject"),(String)obj.get("Message").toString().replaceAll("\\b((?:https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])", "<URl Quarintined>")));
                // System.out.println((String)obj.get("Email ID")+":"+ (String)obj.get("Message"));
            }
            // Always close files.
        }
    }
    catch(FileNotFoundException ex) {
        System.out.println("Unable to open file '" + fileName + "'");                
    }catch(IOException ex) {
        System.out.println("Error reading file '" + fileName + "'");                  
  }
  return tableContent;
    
    }  

}
