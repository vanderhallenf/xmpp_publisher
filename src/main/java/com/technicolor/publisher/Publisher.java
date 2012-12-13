/*
   Copyright 2012 Technicolor

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package com.technicolor.publisher;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.jivesoftware.smackx.pubsub.*;

//jdbc:hsqldb:/opt/openfire/embedded-db/openfire
public class Publisher {

    private final Connection con;
    private final PubSubManager mgr;
    private final String jid;
    private final Map<String, Subscription> nodesRegistered;

    public Publisher(String server, String user, String pass) throws XMPPException {


        System.out.println("Conneting to " + server);
        ConnectionConfiguration config = new ConnectionConfiguration(server);
        con = new XMPPConnection(config);
        con.connect();
        System.out.println("Login as " + user);
        try {
            try {
                java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
                con.login(user, pass, localMachine.getHostName());
            } catch (java.net.UnknownHostException ex) {
                con.login(user, pass);
            }
        } catch (XMPPException ex) {
            System.err.println("User or password doesn't exist");
        }

        jid = con.getUser();
        System.out.println("JID: " + jid);

        mgr = new PubSubManager(con);
        nodesRegistered = new HashMap<String, Subscription>();
    }

    public void checkAndAdd(String nodename) throws XMPPException {
        DiscoverItems discoverNodes = mgr.discoverNodes(null); //get all nodes
        Iterator<DiscoverItems.Item> items = discoverNodes.getItems();
        Map<String, DiscoverItems.Item> nodes = new HashMap<String, DiscoverItems.Item>();
        while (items.hasNext()) {
            DiscoverItems.Item item = items.next();
            nodes.put(item.getNode(), item);
        }
        if (nodes.containsKey(nodename)) {
            System.out.println("Node " + nodename + " already created");
        } else {
            System.out.println("Creating node " + nodename);
            ConfigureForm form = new ConfigureForm(FormType.submit);
            form.setAccessModel(AccessModel.open);
            form.setDeliverPayloads(true);
            form.setNotifyRetract(true);
            form.setSubscribe(true);
            form.setPersistentItems(true);
            form.setPublishModel(PublishModel.open);
            mgr.createNode(nodename, form);
        }
    }

    public void send(String nodename, String idToBePublished) throws XMPPException {
        checkAndAdd(nodename);
        System.out.println("Sending (" + jid + ")");
        // Get the node
        LeafNode node = (LeafNode) mgr.getNode(nodename);

        // Publish an Item with payload
        SimplePayload payload = new SimplePayload("book", "pubsub:test:book",
                "<book xmlns='pubsub:test:book'><title>" + idToBePublished + "</title></book>");
        String id = "Message_" + System.currentTimeMillis();
        System.out.println("Sending id " + id);
        PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>(id, payload);
        try {
            Thread.sleep(100); //allow sysout to print
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        node.send(item);

    }

    public void deleteNodes() throws XMPPException {
        DiscoverItems discoverNodes = mgr.discoverNodes(null);
        Iterator<DiscoverItems.Item> items = discoverNodes.getItems();
        while (items.hasNext()) {
            DiscoverItems.Item item = items.next();
            System.out.println("ITEM: " + item.getNode() + " -- " + item.toXML());
            mgr.deleteNode(item.getNode());
        }
    }

    public void deleteNode(String nodeName) throws XMPPException {
        System.out.println("Deleting node: " + nodeName);
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
