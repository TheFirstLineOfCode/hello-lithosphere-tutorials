package com.thefirstlineofcode.lithosphere.tutorials.hellolora.protocol;

import java.util.HashMap;
import java.util.Map;

import com.thefirstlineofcode.basalt.xmpp.core.Protocol;
import com.thefirstlineofcode.sand.protocols.thing.SimpleThingModelDescriptor;

public class HltModelDescriptor extends SimpleThingModelDescriptor {
	public static final String MODEL_NAME = "HLT";
	public static final String DESCRIPTION = "Hello LoRa Thing";
	
	public HltModelDescriptor() {
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
