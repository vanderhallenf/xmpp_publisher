package com.technicolor.publisher;

import org.jivesoftware.smack.XMPPException;

public class Main {

    public static void main(String[] args) throws XMPPException {

        //XMPPConnection.DEBUG_ENABLED = true;

        String user = "isabel";
        String pwd = "oyente";
        String server = "cplx129.edegem.eu.thmulti.com";
        String nodeName = "Node4";
        Publisher publisher = new Publisher(server,user,pwd);

        
        
        publisher.send("NodeIsabel","Publicacion al nodo isabel");
        
        //publisher.deleteNode("Node5");
        //publisher.deleteNode("NodeFrank");
        
        //publisher.checkAndAdd("NodeFrank");
        

    }
}
