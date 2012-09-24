package com.technicolor.publisher;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.Item;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;

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

    public void publish(String nombre) throws XMPPException {
        
      LeafNode node = (LeafNode)pubSubManager.getNode("Nodo1");
      node.send(new Item());
      node.send(new Item(nombre));
              
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
         
            LeafNode n = (LeafNode) pubSubManager.createNode(nodeName, form);
            
            
            
        }else{
            System.out.println("El nodo ya existe");
        }
    }

    public boolean existNode(String nodeName) {
        try {
            pubSubManager.getNode(nodeName);
            return true;
        } catch (XMPPException ex) {
            System.out.println("Node " + nodeName + " didn't exist, it's been created");
            return false;
        }
    }

    public void deleteNode(String id) throws XMPPException {
        pubSubManager.deleteNode(id);
    }

    public void logout() {
        connection.disconnect();
    }
    
       
    
}