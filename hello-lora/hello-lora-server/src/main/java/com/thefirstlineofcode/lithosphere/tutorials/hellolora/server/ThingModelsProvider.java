package com.thefirstlineofcode.lithosphere.tutorials.hellolora.server;

import org.pf4j.Extension;

import com.thefirstlineofcode.lithosphere.tutorials.hellolora.protocol.HlgModelDescriptor;
import com.thefirstlineofcode.lithosphere.tutorials.hellolora.protocol.HltModelDescriptor;
import com.thefirstlineofcode.sand.protocols.thing.IThingModelDescriptor;
import com.thefirstlineofcode.sand.server.things.IThingModelsProvider;

@Extension
public class ThingModelsProvider implements IThingModelsProvider {

	@Override
	public IThingModelDescriptor[] provide() {
		return new IThingModelDescriptor[] {
			new HlgModelDescriptor(),
			new HltModelDescriptor()
		};
	}

}
