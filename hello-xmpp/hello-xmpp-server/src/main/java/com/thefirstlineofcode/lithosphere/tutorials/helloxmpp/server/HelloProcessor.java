package com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.server;

import com.thefirstlineofcode.basalt.xmpp.core.ProtocolException;
import com.thefirstlineofcode.basalt.xmpp.core.stanza.Iq;
import com.thefirstlineofcode.basalt.xmpp.core.stanza.error.BadRequest;
import com.thefirstlineofcode.granite.framework.core.config.IConfiguration;
import com.thefirstlineofcode.granite.framework.core.config.IConfigurationAware;
import com.thefirstlineofcode.granite.framework.core.pipeline.stages.processing.IProcessingContext;
import com.thefirstlineofcode.granite.framework.core.pipeline.stages.processing.IXepProcessor;
import com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.protocol.Hello;

public class HelloProcessor implements IXepProcessor<Iq, Hello>, IConfigurationAware {
	private static final String CONFIGURTION_KEY_XMPP_SERVER_NAME = "xmpp.server.name";
	
	private String xmppServerName;

	@Override
	public void process(IProcessingContext context, Iq iq, Hello hello) {
		if (hello.getGeologistName() == null) {
			throw new ProtocolException(new BadRequest("Null geologist name."));
		}
		
		Hello helloFromXmppServer = new Hello();
		helloFromXmppServer.setGreeting(String.format("Hello! %s. I'm %s.", hello.getGeologistName(), xmppServerName));
		Iq result = Iq.createResult(iq, helloFromXmppServer);
		
		context.write(result);
	}

	@Override
	public void setConfiguration(IConfiguration configuration) {
		xmppServerName = configuration.getString(CONFIGURTION_KEY_XMPP_SERVER_NAME, "Granite XMPP Server");
	}

}
