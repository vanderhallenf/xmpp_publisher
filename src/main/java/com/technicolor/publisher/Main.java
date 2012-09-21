package com.technicolor.publisher;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.PubSubManager;

public class Main {


    public static void main(String[] args) throws XMPPException {
        Publisher publisher = new Publisher();
        publisher.login("admin", "oyente");
        publisher.addNode("Mua");
        publisher.publish();
       
 
        //publisher.configure();

    }
}
