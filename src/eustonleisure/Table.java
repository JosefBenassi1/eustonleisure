/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eustonleisure;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author josefbenassi
 */
public class Table {
    
    
       private SimpleStringProperty messageID, sender, subject , message;

        Table(String messageID, String sender, String subject, String message) {
       
        this.messageID = new SimpleStringProperty  (messageID);
        this.sender    =new SimpleStringProperty  (sender);
        this.subject =   new SimpleStringProperty  (subject);
        this.message =   new SimpleStringProperty  (message);
       
    }
   
    
  
    
    public String getMessageID() {
        return messageID.get();
    }

    public void setMessageID(String messageID) {
        this.messageID = new SimpleStringProperty(messageID);
      
    }

    public String getSender() {
        return sender.get();
    }

    public void setSender(String sender) {
        this.sender = new SimpleStringProperty(sender);
    }

    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject = new SimpleStringProperty(subject);
    }

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String message) {
        this.message = new SimpleStringProperty(message);
    }

    
    @Override
    public String toString()
    {
        return String.format("%s %s %s %s", messageID ,sender ,subject ,message);
    }
    
}
