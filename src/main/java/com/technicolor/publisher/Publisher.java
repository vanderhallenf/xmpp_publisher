package com.technicolor.publisher;

import java.util.List;
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

    public void addNode(String nodeName) throws XMPPException {
        mgr = new PubSubManager(con);
        LeafNode leaf = mgr.createNode(nodeName);
        ConfigureForm form = new ConfigureForm(FormType.submit);
        form.setAccessModel(AccessModel.open);
        form.setDeliverPayloads(true);
        form.setNotifyRetract(true);
        form.setPersistentItems(true);
        form.setPublishModel(PublishModel.open);
        form.setSubscribe(true);
        form.setPresenceBasedDelivery(false);
        form.setPresenceBasedDelivery(false);

        leaf.sendConfigurationForm(form);
    }

    public void publish(String nodeName, String toBePublished) throws XMPPException {
        mgr = new PubSubManager(con);
        LeafNode node = (LeafNode) mgr.getNode(nodeName);
        node.send(new PayloadItem(toBePublished + "_" + System.currentTimeMillis(), new SimplePayload("book3", "pubsub:" + toBePublished + ":book", "<book>Human Resources</book>")));
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

    protected void deleteNode(String nodeName) throws XMPPException {
        PubSubManager mgr = new PubSubManager(con);
        mgr.deleteNode(nodeName);
    }

    public void logout() {
        con.disconnect();
    }

    public void getSubscriptions() {
        try {
            PubSubManager manager = new PubSubManager(con);
            List<Subscription> listSubs = manager.getSubscriptions();
            for (int i = 0; i < listSubs.size(); i++) {
                System.out.println(listSubs.get(i).getId());
                System.out.println(listSubs.get(i).getJid());
                System.out.println(listSubs.get(i).toXML());
            }
        } catch (XMPPException e) {
            System.out.println(e.getMessage());
        }
    }
}
