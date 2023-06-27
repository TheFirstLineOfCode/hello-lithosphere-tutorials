package com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.app;

import com.thefirstlineofcode.basalt.xmpp.core.stanza.error.StanzaError;
import com.thefirstlineofcode.chalk.core.AuthFailureException;
import com.thefirstlineofcode.chalk.core.IChatClient;
import com.thefirstlineofcode.chalk.core.StandardChatClient;
import com.thefirstlineofcode.chalk.core.stream.StandardStreamConfig;
import com.thefirstlineofcode.chalk.core.stream.UsernamePasswordToken;
import com.thefirstlineofcode.chalk.network.ConnectionException;
import com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.client.HelloXmppPlugin;
import com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.client.ISayHelloToXmpp;
import com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.client.ISayHelloToXmpp.IHelloListener;

public class Main implements IHelloListener {
	private static final String USER_NAME = "geologist";
	private static final String USER_PASSWORD = "I like lithosphere.";
	
	public static void main(String[] args) {
		new Main().run();
	}
	
	private void run() {
		IChatClient chatClient = createChatClient();
		sayHelloToXmpp(chatClient);
		
		if (chatClient != null && chatClient.isConnected())
			chatClient.close();
	}

	private void sayHelloToXmpp(IChatClient chatClient) {
		try {
			chatClient.connect(new UsernamePasswordToken(USER_NAME, USER_PASSWORD));
		} catch (ConnectionException e) {
			throw new RuntimeException("Can't connect to server.", e);
		} catch (AuthFailureException e) {
			throw new RuntimeException("????", e);
		}
			
		ISayHelloToXmpp sayHelloToXmpp = chatClient.createApi(ISayHelloToXmpp.class);
		sayHelloToXmpp.setHelloListener(this);
		sayHelloToXmppAsAnonymous(sayHelloToXmpp);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sayHelloToXmppAsDongger(sayHelloToXmpp);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void sayHelloToXmppAsDongger(ISayHelloToXmpp sayHelloToXmpp) {
		System.out.println();
		System.out.println();
		System.out.println("Say hello to XMPP as Dongger.");
		sayHelloToXmpp.sayHello("Dongger");
		System.out.println();
	}

	private static void sayHelloToXmppAsAnonymous(ISayHelloToXmpp sayHelloToXmpp) {
		System.out.println();
		System.out.println();
		System.out.println("Say hello to XMPP as anonymous.");
		System.out.println();
		sayHelloToXmpp.sayHello(null);
	}

	private static IChatClient createChatClient() {
		StandardStreamConfig streamConfig = new StandardStreamConfig("192.168.1.80", 5222);
		streamConfig.setTlsPreferred(false);
		streamConfig.setResource("my_desktop");
		IChatClient chatClient = new StandardChatClient(streamConfig);
		
		chatClient.register(HelloXmppPlugin.class);
		
		return chatClient;
	}

	@Override
	public void greetingReceived(String greeting) {
		System.out.println(greeting);
	}

	@Override
	public void errorReceived(StanzaError error) {
		System.out.println(String.format("Received an stanza error: %s." , error));
	}

	@Override
	public void timedOut() {
		System.out.println("No response returned.");
	}
}