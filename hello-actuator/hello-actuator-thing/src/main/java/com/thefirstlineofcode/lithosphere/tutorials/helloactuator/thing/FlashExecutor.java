package com.thefirstlineofcode.lithosphere.tutorials.helloactuator.thing;

import com.thefirstlineofcode.basalt.xmpp.core.ProtocolException;
import com.thefirstlineofcode.basalt.xmpp.core.stanza.Iq;
import com.thefirstlineofcode.basalt.xmpp.core.stanza.error.StanzaError;
import com.thefirstlineofcode.basalt.xmpp.core.stanza.error.UndefinedCondition;
import com.thefirstlineofcode.lithosphere.tutorials.helloactuator.protocol.Flash;
import com.thefirstlineofcode.sand.client.actuator.IExecutor;
import com.thefirstlineofcode.sand.client.thing.ThingsUtils;
import com.thefirstlineofcode.sand.protocols.actuator.ExecutionException;

public class FlashExecutor implements IExecutor<Flash> {
	private ISimpleLight simpleLight;
	
	public FlashExecutor(ISimpleLight simpleLight) {
		this.simpleLight = simpleLight;
	}

	@Override
	public Object execute(Iq iq, Flash flash) throws ProtocolException {
		try {
			simpleLight.flash(flash.getRepeat());
		} catch (ExecutionException e) {
			throw new ProtocolException(new UndefinedCondition(StanzaError.Type.CANCEL,
					ThingsUtils.getExecutionErrorDescription(simpleLight.getThingModel(), e.getErrorNumber())));
		}
		
		return null;
	}

}
