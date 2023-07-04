package com.thefirstlineofcode.lithosphere.tutorials.helloactuator.protocol;

import java.util.HashMap;
import java.util.Map;

import com.thefirstlineofcode.basalt.xmpp.core.Protocol;
import com.thefirstlineofcode.sand.protocols.thing.SimpleThingModelDescriptor;

public class HatModelDescriptor extends SimpleThingModelDescriptor {
	public static final String MODEL_NAME = "HAT";
	public static final String DESCRIPTION = "Hello acuator thing";
	
	public HatModelDescriptor() {
		super(MODEL_NAME, DESCRIPTION, false, null, null, createSupportedActions());
	}
	
	private static Map<Protocol, Class<?>> createSupportedActions() {
		Map<Protocol, Class<?>> supportedActions = new HashMap<>();
		supportedActions.put(Flash.PROTOCOL, Flash.class);
		supportedActions.put(TurnOn.PROTOCOL, TurnOn.class);
		supportedActions.put(TurnOff.PROTOCOL, TurnOff.class);
		
		return supportedActions;
	}
}
