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
public class List {
    
 private SimpleStringProperty hashName;

    public List(String hashName) {
        this.hashName = new SimpleStringProperty(hashName);
        
    }

    public String getHashName() {
        return hashName.get();
    }

    public void setHashName(String hashName) {
        this.hashName = new SimpleStringProperty(hashName);
    }
 
 




}
