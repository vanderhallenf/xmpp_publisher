/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.technicolor.publisher;

import org.jivesoftware.smack.XMPPException;

/**
 *
 * @author pardogonzalezj
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws XMPPException {
        Publisher publisher = new Publisher();
        publisher.login("admin", "oyente");
        publisher.configure();

    }
}
