package com.technicolor.publisher;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class Main {

    private static Publisher publisher;
    private static String server;
    private static String node;
    private static String user;
    private static String pass;
    private static String message;

    private static void print_help() {
        System.out.println("Arguments:");
        System.out.println("\t-s <server>  \t -> URL of the XMPP server");
        System.out.println("\t-u <username>\t -> User name to connect to the XMPP server");
        System.out.println("\t-p <password>\t -> Password to connect to the XMPP server");
        System.out.println("\t-n <node>    \t -> Name of the node to publish");
        System.out.println("\t-m <message> \t -> Message to publish");
        System.out.println("\t-d           \t -> Enable the debugger");
        System.out.println("\t-h           \t -> Show this help");
    }

    public static void main(String[] args) throws XMPPException, InterruptedException {

        for (int i = 0; i < args.length; i++) {
            if ("-h".equals(args[i])) {
                print_help();
                System.exit(0);
            } else if ("-s".equals(args[i]) && i + 1 < args.length) {
                server = args[i + 1];
            } else if ("-u".equals(args[i]) && i + 1 < args.length) {
                user = args[i + 1];
            } else if ("-p".equals(args[i]) && i + 1 < args.length) {
                pass = args[i + 1];
            } else if ("-n".equals(args[i]) && i + 1 < args.length) {
                node = args[i + 1];
            } else if ("-m".equals(args[i]) && i + 1 < args.length) {
                message = args[i + 1];
            } else if ("-d".equals(args[i])) {
                XMPPConnection.DEBUG_ENABLED = true;
            }
        }
        if (server == null || "".equals(server)) {
            System.err.println("Error: no server specified");
            System.exit(-1);
        }
        if (user == null || "".equals(user)) {
            System.err.println("Error: no user specified");
            System.exit(-1);
        }
        if (pass == null || "".equals(pass)) {
            System.err.println("Error: no pasword specified");
            System.exit(-1);
        }
        if (node == null || "".equals(node)) {
            System.err.println("Error: no node specified");
            System.exit(-1);
        }
        if (message == null || "".equals(message)) {
            System.err.println("Error: no message specified");
            System.exit(-1);
        }

        publisher = new Publisher(server, user, pass);
        publisher.send(node, message);

        while (true) {
            Thread.sleep(60 * 1000);
        }


    }
}
