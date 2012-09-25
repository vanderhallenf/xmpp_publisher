package com.technicolor.publisher;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.*;

public class Publisher {

    PubSubManager mgr;
    Connection con;

    public void Publisher() {
        con = null;
        mgr = null;
    }

    public void login(String username, String password) throws XMPPException {
        ConnectionConfiguration config = new ConnectionConfiguration("cplx129.edegem.eu.thmulti.com");
        con = new XMPPConnection(config);
        con.connect();
        con.login(username, password);
    }

    public void addNode() throws XMPPException {
        mgr = new PubSubManager(con);
        LeafNode leaf = mgr.createNode("testNode");
        ConfigureForm form = new ConfigureForm(FormType.submit);
        form.setAccessModel(AccessModel.open);
        form.setDeliverPayloads(false);
        form.setNotifyRetract(true);
        form.setPersistentItems(true);
        form.setPublishModel(PublishModel.open);
        form.setSubscribe(true);
        form.setPresenceBasedDelivery(false);

        leaf.sendConfigurationForm(form);
    }

    public void publish() throws XMPPException {
        mgr = new PubSubManager(con);
        LeafNode node = (LeafNode) mgr.getNode("testNode");
        node.send(new PayloadItem("test6" + System.currentTimeMillis(), new SimplePayload("book", "pubsub:test6:book", "")));
    }

    public boolean existNode(String nodeName) {
        try {
            mgr.getNode(nodeName);
            return true;
        } catch (XMPPException ex) {
            System.out.println("El nodo" + nodeName + " no existe");
            return false;
        }
    }

    public void logout() {
        con.disconnect();
    }
}
