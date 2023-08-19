package com.thefirstlineofcode.lithosphere.tutorials.hellowebrtc.server;

import org.pf4j.Extension;

import com.thefirstlineofcode.sand.protocols.thing.IThingModelDescriptor;
import com.thefirstlineofcode.sand.server.things.IThingModelsProvider;

@Extension
public class ThingModelsProvider implements IThingModelsProvider {

	@Override
	public IThingModelDescriptor[] provide() {
		return new IThingModelDescriptor[] {
			new HwtModelDescriptor(),
		};
	}

}
