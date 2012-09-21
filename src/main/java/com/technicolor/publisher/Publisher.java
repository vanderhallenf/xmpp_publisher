package com.technicolor.publisher;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;


public class Publisher {

	XMPPConnection connection;
	PubSubManager pubSubManager;

	public void login(String userName, String password) throws XMPPException
	{
		XMPPConnection.DEBUG_ENABLED = true; 
		ConnectionConfiguration config = new ConnectionConfiguration("cplx129.edegem.eu.thmulti.com");
		connection = new XMPPConnection(config);
		connection.connect();
		System.out.println("Is it conected? " + connection.isConnected());
		System.out.println("Is it authenticated? " + connection.isAuthenticated());
		connection.login(userName, password);
		System.out.println("Is it authenticated? " + connection.isAuthenticated());
	}

	public void configure() throws XMPPException{
		ConfigureForm form = new ConfigureForm(FormType.submit);
		form.setPersistentItems(false);
		form.setDeliverPayloads(true);
		form.setAccessModel(AccessModel.open);
		form.setPublishModel(PublishModel.open);
		form.setSubscribe(true);

		PubSubManager mgr = new PubSubManager(connection);
		LeafNode n = (LeafNode) mgr.createNode("Musica", form);
			 
		 
		SimplePayload payload = new SimplePayload("musica","pubsub:test:music", "Coldplay");

		PayloadItem<SimplePayload> payloadItem = new PayloadItem<SimplePayload>(null, payload);
		((LeafNode)mgr.getNode("Musica")).publish(payloadItem);

	}

	public static void main(String[] args) throws XMPPException {		

		Publisher publisher = new Publisher();
		publisher.login("admin","oyente");
		publisher.configure();

	}

}