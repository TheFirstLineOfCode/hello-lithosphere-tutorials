package com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.client;

import java.util.Properties;

import com.thefirstlineofcode.basalt.oxm.coc.CocParserFactory;
import com.thefirstlineofcode.basalt.oxm.coc.CocTranslatorFactory;
import com.thefirstlineofcode.basalt.xmpp.core.IqProtocolChain;
import com.thefirstlineofcode.chalk.core.IChatSystem;
import com.thefirstlineofcode.chalk.core.IPlugin;
import com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.protocol.Hello;

public class HelloXmppPlugin implements IPlugin {

	@Override
	public void init(IChatSystem chatSystem, Properties properties) {
		chatSystem.registerTranslator(Hello.class, new CocTranslatorFactory<>(Hello.class));
		chatSystem.registerParser(new IqProtocolChain(Hello.PROTOCOL),
				new CocParserFactory<>(Hello.class));
		chatSystem.registerApi(ISayHelloToXmpp.class, SayHelloToXmpp.class);
	}

	@Override
	public void destroy(IChatSystem chatSystem) {
		chatSystem.unregisterApi(ISayHelloToXmpp.class);
		chatSystem.unregisterParser(new IqProtocolChain(Hello.PROTOCOL));
		chatSystem.unregisterTranslator(Hello.class);
	}
}
