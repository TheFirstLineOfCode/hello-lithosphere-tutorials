package com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.client;

import com.thefirstlineofcode.basalt.xmpp.core.stanza.Iq;
import com.thefirstlineofcode.basalt.xmpp.core.stanza.error.StanzaError;
import com.thefirstlineofcode.chalk.core.IChatServices;
import com.thefirstlineofcode.chalk.core.IUnidirectionalStream;
import com.thefirstlineofcode.chalk.core.TaskAdapter;
import com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.protocol.Hello;

public class SayHelloToXmpp implements ISayHelloToXmpp {
	private IChatServices chatServices;
	
	private IHelloListener helloListener;
	
	@Override
	public void sayHello(final String geologistName) {
		chatServices.getTaskService().execute(new TaskAdapter<Iq>() {

			@Override
			public void trigger(IUnidirectionalStream<Iq> stream) {
				Iq iq = new Iq(Iq.Type.SET, new Hello(geologistName));
				stream.send(iq);
			}

			@Override
			public void processResponse(IUnidirectionalStream<Iq> stream, Iq response) {
				if (helloListener != null) {					
					Hello hello = response.getObject();
					helloListener.greetingReceived(hello.getGreeting());
				}
			}
			
			@Override
			public boolean processError(IUnidirectionalStream<Iq> stream, StanzaError error) {
				if (helloListener != null) {					
					helloListener.errorReceived(error);
					return true;
				}
				
				return false;
			}
			
			@Override
			public boolean processTimeout(IUnidirectionalStream<Iq> stream, Iq request) {
				if (helloListener != null) {					
					helloListener.timedOut();
					
					return true;
				}
				
				return false;
			}
		});
	}

	@Override
	public void setHelloListener(IHelloListener helloListener) {
		this.helloListener = helloListener;
	}

}
