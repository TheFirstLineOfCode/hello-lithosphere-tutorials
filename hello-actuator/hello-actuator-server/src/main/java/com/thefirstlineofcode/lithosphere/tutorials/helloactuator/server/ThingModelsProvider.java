package com.thefirstlineofcode.lithosphere.tutorials.helloactuator.server;

import org.pf4j.Extension;

import com.thefirstlineofcode.lithosphere.tutorials.helloactuator.protocol.HatModelDescriptor;
import com.thefirstlineofcode.sand.protocols.thing.IThingModelDescriptor;
import com.thefirstlineofcode.sand.server.things.IThingModelsProvider;

@Extension
public class ThingModelsProvider implements IThingModelsProvider {

	@Override
	public IThingModelDescriptor[] provide() {
		return new IThingModelDescriptor[] {
			new HatModelDescriptor()
		};
	}

}
