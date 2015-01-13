/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import SerialCommunication.SmartPlugPacket;
import SerialCommunication.SerialTransceiver;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dan
 */

public class StartSerialCommunication {
    
    
    
    public static void main(String[] args)
    {
           RFIDEventManagerSimple rFIDEventManagerSimple = new RFIDEventManagerSimple();
        //Construct another SerialTransceiver for the RFIDEventManager
     SerialTransceiver rFIDEventManagerTransceiver = new SerialTransceiver(new SmartPlugPacket(), rFIDEventManagerSimple);
    
    rFIDEventManagerSimple.setTransmitter(rFIDEventManagerTransceiver);
            
            try {
                System.out.println("openning port");
                rFIDEventManagerSimple.openPort();  
                System.out.println("after port is openned");
            } catch (TooManyListenersException ex) {
                Logger.getLogger(StartSerialCommunication.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Error");
            }
    }
}
