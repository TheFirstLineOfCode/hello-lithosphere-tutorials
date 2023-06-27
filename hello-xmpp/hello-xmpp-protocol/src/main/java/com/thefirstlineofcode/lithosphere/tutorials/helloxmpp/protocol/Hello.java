package com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.protocol;

import com.thefirstlineofcode.basalt.oxm.coc.annotations.ProtocolObject;
import com.thefirstlineofcode.basalt.oxm.coc.annotations.TextOnly;
import com.thefirstlineofcode.basalt.oxm.coc.validation.annotations.NotNull;
import com.thefirstlineofcode.basalt.xmpp.core.Protocol;

@ProtocolObject(namespace = "urn:lithosphere:tutorials:hello-xmpp", localName = "hello")
public class Hello {
	public static final Protocol PROTOCOL = new Protocol("urn:lithosphere:tutorials:hello-xmpp", "hello");
	
	private String geologistName;
	
	@NotNull
	@TextOnly
	private String greeting;
	
	public Hello() {}
	
	public Hello(String geologistName) {
		this.geologistName = geologistName;
	}
	
	public String getGeologistName() {
		return geologistName;
	}
	
	public void setGeologistName(String geologistName) {
		this.geologistName = geologistName;
	}
	
	public String getGreeting() {
		return greeting;
	}
	
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
}
