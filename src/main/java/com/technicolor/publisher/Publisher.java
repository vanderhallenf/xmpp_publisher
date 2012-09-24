package com.technicolor.publisher;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;

public class Publisher {

    XMPPConnection connection;
    PubSubManager pubSubManager;

    public Publisher() {
        ConnectionConfiguration config = new ConnectionConfiguration("cplx129.edegem.eu.thmulti.com");
        connection = new XMPPConnection(config);
        pubSubManager = new PubSubManager(connection);


    }

    protected void login(String userName, String password) throws XMPPException {
        
        connection.connect();
        
        System.out.println("Is it connected? " + connection.isConnected());
        System.out.println("Is it authenticated? " + connection.isAuthenticated());
        connection.login(userName, password);
        System.out.println("Is it authenticated? " + connection.isAuthenticated());
    }

    public void publish() throws XMPPException {

//        SimplePayload payload = new SimplePayload("Gladiator", "Peliculas", "Es una pelicula sobre un gladiador");
//
        
         SimplePayload payload = new SimplePayload("book","pubsub:test:book", "<book xmlns='pubsub:test:book'><title>Lord of the Rings</title></book>");
        PayloadItem<SimplePayload> payloadItem = new PayloadItem<SimplePayload>(null, payload);
         ((LeafNode) pubSubManager.getNode("Musica")).publish(payloadItem);
        
        
//        Item item = new Item("Kill Bill");
//        ((LeafNode)pubSubManager.getNode("Peliculas")).publish(item);
        
             
       System.out.println(((LeafNode) pubSubManager.getNode("Peliculas")).getItems().size());

    }

    public void addNode(String nodeName) throws XMPPException {
        if (!existNode(nodeName)) {
            ConfigureForm form = new ConfigureForm(FormType.submit);
            form.setPersistentItems(false);
            form.setDeliverPayloads(true);
            form.setNotifyDelete(true);
            form.setNotifyRetract(true);
            form.setAccessModel(AccessModel.open);
            form.setPublishModel(PublishModel.open);
            form.setSubscribe(true);
            pubSubManager.createNode(nodeName);
            LeafNode n = (LeafNode) pubSubManager.createNode("Trabalenguas", form);
        }
    }

    public boolean existNode(String nodeName) {
        try {
            pubSubManager.getNode(nodeName);
            return true;
        } catch (XMPPException ex) {
            System.out.println("El nodo" + nodeName + " no existe");
            return false;
        }
    }

    public void logout() {
        connection.disconnect();
    }
}