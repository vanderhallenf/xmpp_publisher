package com.technicolor.publisher;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;


public class Main {


    public static void main(String[] args) throws XMPPException {
        
        XMPPConnection.DEBUG_ENABLED = true;
        Publisher publisher = new Publisher();
        publisher.login("admin", "oyente");
        
       // publisher.addNode("Node8");
       publisher.publish("Node7", "Lin Noeninckx");
       //publisher.getSubscriptions();

        
    }
}