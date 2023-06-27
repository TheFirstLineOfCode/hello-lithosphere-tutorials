package com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.server;

import org.pf4j.Extension;

import com.thefirstlineofcode.basalt.xmpp.core.IqProtocolChain;
import com.thefirstlineofcode.granite.framework.core.pipeline.stages.IPipelineExtendersConfigurator;
import com.thefirstlineofcode.granite.framework.core.pipeline.stages.PipelineExtendersConfigurator;
import com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.protocol.Hello;

@Extension
public class PipelineExtendersContributor extends PipelineExtendersConfigurator {
	private static final IqProtocolChain PROTOCOL_CHAIN_HELLO = new IqProtocolChain(Hello.PROTOCOL);

	@Override
	protected void configure(IPipelineExtendersConfigurator configurator) {
		configurator.registerCocParser(PROTOCOL_CHAIN_HELLO, Hello.class);
		configurator.registerCocTranslator(Hello.class);
		
		configurator.registerSingletonXepProcessor(PROTOCOL_CHAIN_HELLO, new HelloProcessor());
	}

}
