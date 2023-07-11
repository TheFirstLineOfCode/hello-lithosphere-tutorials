package com.thefirstlineofcode.lithosphere.tutorials.helloactuator.thing;

import com.thefirstlineofcode.basalt.xmpp.core.stanza.Iq;
import com.thefirstlineofcode.lithosphere.tutorials.helloactuator.protocol.TurnOff;
import com.thefirstlineofcode.sand.client.actuator.IExecutor;
import com.thefirstlineofcode.sand.client.actuator.IThingControllerAware;

public class TurnOffExecutor implements IExecutor<TurnOff>, IThingControllerAware<ISimpleLight> {
	private ISimpleLight simpleLight;

	@Override
	public Object execute(Iq iq, TurnOff turnOff) {
		simpleLight.turnOff();
		
		return null;
	}

	@Override
	public void setThingController(ISimpleLight simpleLight) {
		this.simpleLight = simpleLight;
	}
}
