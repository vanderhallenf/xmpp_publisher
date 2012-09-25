package com.technicolor.publisher;

import java.util.Iterator;
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


        // Get the node
        LeafNode node=null;
     
            //node = (LeafNode) pubSubManager.getNode("Node1");


            // Publish an Item, let service set the id
            //node.send(new Item("abc"));

            SimplePayload payload = new SimplePayload("book", null, "<book xmlns='pubsub:book'><title>1984</title></book>");
            PayloadItem<SimplePayload> payloadItem = new PayloadItem<SimplePayload>(null, payload);
            ((LeafNode) pubSubManager.getNode("Node2")).publish(payloadItem);

    //        Iterator it = (((LeafNode) pubSubManager.getNode("testNode4")).getItems()).iterator();
    //        while (it.hasNext()) {
    //            System.out.println(it.next());
    //        }

    }

    public void addNode(String nodeName) throws XMPPException {
        if (!existNode(nodeName)) {
            ConfigureForm form = new ConfigureForm(FormType.submit);
//            form.setPersistentItems(false);
//            form.setDeliverPayloads(false);
//            form.setNotifyDelete(true);
//            form.setNotifyRetract(true);
//            form.setAccessModel(AccessModel.open);
//            form.setPublishModel(PublishModel.open);
            form.setSubscribe(true);
            
            
      form.setAccessModel(AccessModel.open);
      form.setDeliverPayloads(true);
      form.setNotifyRetract(true);
      form.setPersistentItems(true);
      form.setPublishModel(PublishModel.open);
            
            pubSubManager.createNode(nodeName);
           // LeafNode n = (LeafNode) pubSubManager.createNode("Node1", form);
              System.out.println("added node");
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