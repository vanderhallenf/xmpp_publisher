package com.technicolor.publisher;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Main {

    public static void main(String[] args) throws XMPPException {

        //XMPPConnection.DEBUG_ENABLED = true;

        String user = "admin";
        String pwd = "oyente";
        String server = "cplx129.edegem.eu.thmulti.com";
        String nodeName = "Node3";
        Publisher publisher = new Publisher(server,user,pwd);

        //publisher.checkAndAdd("Node2");
        publisher.send("Node3","Casi naa");

    }
}
