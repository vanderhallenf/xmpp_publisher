package com.technicolor.publisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Main {

    public static void main(String[] args) throws XMPPException, IOException {
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String str;
        
        //XMPPConnection.DEBUG_ENABLED = true;
        Publisher publisher = new Publisher();
        publisher.login("admin", "oyente");
        System.out.println("Dame el item");
        str = buff.readLine();
        
        
        //publisher.addNode("Nodo1");
        //publisher.deleteNode("Peliculas");
        publisher.publish(str);
        
//        for (int i=1;i<6;i++){
//            publisher.deleteNode(("Nodo"+i));
//        }


    }
}
