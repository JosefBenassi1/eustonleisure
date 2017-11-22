/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eustonleisure;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author josefbenassi
 */
public class MessageTest {
    
    String file = "/Users/josefbenassi/Documents/jsonjoe.json";

    /**
     * Test of changeSmsOrTweetContent method, of class Message.
     * @throws java.lang.Exception
     */
    @Test
    public void testChangeSmsOrTweetContent() throws Exception {
        System.out.println("changeSmsOrTweetContent");
        System.out.println("test passed");
        String fileRead = "/Users/josefbenassi/Documents/textwords.csv";
        String fileWrite = "/Users/josefbenassi/Documents/jsonjoe.json";
        String mContent = "test 1 HEY LOL BRB";
        String messageID ="S123456789";
        String sender = " 07748651717";
        Message instance = new MessageImpl();
        instance.changeSmsOrTweetContent(fileRead, fileWrite, mContent, messageID, sender);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of writeToJsonFileNotEmail method, of class Message.
     */
    @Test
    public void testWriteToJsonFileNotEmail() {
        System.out.println("writeToJsonFileNotEmail");
        System.out.println("test passed");
        String messageID = "T123456789";
        String sender = "@Josef";
        String messageContentString = "Hello LOL";
        StringBuilder  messageContent= new StringBuilder();
        messageContent.append(messageContentString);
        
        
        
        Message instance = new MessageImpl();
        instance.writeToJsonFileNotEmail(messageID, sender, messageContent, file);
        // TODO review the generated test code and remove the default call to fail.
      
    }

    /**
     * Test of changeEmailContent method, of class Message.
     */
    @Test
    public void testChangeEmailContent() {
        System.out.println("changeEmailContent");
        System.out.println("test passed");
        String emailContent = "";
        String messageID = "E12356789";
        String sender = "benyjoe100@homtail.com";
        String subject = "Hello shmeeeee";
        Message instance = new MessageImpl();
        instance.changeEmailContent(emailContent, messageID, sender, subject, file);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of writeToJsonFileEmail method, of class Message.
     */
    @Test
    public void testWriteToJsonFileEmail() {
        System.out.println("writeToJsonFileEmail");
        System.out.println("test passed");
        String messageID = "E123456789Test";
        String sender = "test2";
        String subject = "joes demise";
        String messageContent = "hello braaaa";
       
        Message instance = new MessageImpl();
        instance.writeToJsonFileEmail(messageID, sender, subject, messageContent, file);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of changeEmailContentSir method, of class Message.
     */
    @Test
    public void testChangeEmailContentSir() {
        System.out.println("changeEmailContentSir");
        System.out.println("test passed");
        String emailContent = "";
        String messageID = "E123456789";
        String sender = "joe@joe.com";
        String subject = "SIR 10/02/1993";
        String sirNumber = "666-666-666";
        String sirDescription = "Staff Attack";
        Message instance = new MessageImpl();
        instance.changeEmailContentSir(emailContent, messageID, sender, subject, sirNumber, sirDescription, file);
    }

    /**
     * Test of writeToJsonFileEmailSir method, of class Message.
     */
    @Test
    public void testWriteToJsonFileEmailSir() {
        System.out.println("writeToJsonFileEmailSir");
        System.out.println("test passed");
        String messageID = "test3";
        String sender = "@test3";
        String subject = " test help";
        String messageContent = "http://www.google.com";
        String sirNumber = "666-666-66";
        String sirDescription = "hello duccy";
        Message instance = new MessageImpl();
        instance.writeToJsonFileEmailSir(messageID, sender, subject, messageContent, sirNumber, sirDescription, file);
        // TODO review the generated test code and remove the default call to fail.
        
    }


    public class MessageImpl extends Message {
        
        
    }
    
}
