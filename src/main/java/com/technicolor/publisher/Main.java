package com.technicolor.publisher;

import org.jivesoftware.smack.XMPPException;


public class Main {


    public static void main(String[] args) throws XMPPException {
        
        Publisher publisher = new Publisher();
        publisher.login("admin", "oyente");
        
        publisher.addNode("Node1");
        publisher.publish("Node1", "libro");

        
    }
}
