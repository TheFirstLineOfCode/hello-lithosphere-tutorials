package com.thefirstlineofcode.lithosphere.tutorials.helloactuator.thing;

import com.thefirstlineofcode.basalt.xmpp.core.ProtocolException;
import com.thefirstlineofcode.basalt.xmpp.core.stanza.Iq;
import com.thefirstlineofcode.lithosphere.tutorials.helloactuator.protocol.TurnOn;
import com.thefirstlineofcode.sand.client.actuator.IExecutor;
import com.thefirstlineofcode.sand.client.actuator.IThingControllerAware;

public class TurnOnExecutor implements IExecutor<TurnOn>, IThingControllerAware<ISimpleLight> {
	private ISimpleLight simpleLight;

	@Override
	public Object execute(Iq iq, TurnOn turnOn) throws ProtocolException {
		simpleLight.turnOn();
		
		return null;
	}

	@Override
	public void setThingController(ISimpleLight simpleLight) {
		this.simpleLight = simpleLight;
	}

}
