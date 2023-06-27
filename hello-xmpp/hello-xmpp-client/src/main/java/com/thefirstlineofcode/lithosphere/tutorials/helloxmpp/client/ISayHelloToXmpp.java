package com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.client;

import com.thefirstlineofcode.basalt.xmpp.core.stanza.error.StanzaError;

public interface ISayHelloToXmpp {
	public interface IHelloListener {
		void greetingReceived(String greeting);
		void errorReceived(StanzaError error);
		void timedOut();
	}
	
	void setHelloListener(IHelloListener helloListener);
	void sayHello(String geologistName);
}
