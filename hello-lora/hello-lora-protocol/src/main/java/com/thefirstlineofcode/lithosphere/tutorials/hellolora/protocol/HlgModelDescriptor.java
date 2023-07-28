package com.thefirstlineofcode.lithosphere.tutorials.hellolora.protocol;

import java.util.HashMap;
import java.util.Map;

import com.thefirstlineofcode.basalt.xmpp.core.Protocol;
import com.thefirstlineofcode.sand.protocols.lora.gateway.ChangeWorkingMode;
import com.thefirstlineofcode.sand.protocols.thing.SimpleThingModelDescriptor;

public class HlgModelDescriptor extends SimpleThingModelDescriptor {
	public static final String MODEL_NAME = "HLG";
	public static final String DESCRIPTION = "Hello LoRa gateway";
	
	public HlgModelDescriptor() {
		super(MODEL_NAME, DESCRIPTION, true, null, null, createSupportedActions());
	}
	
	private static Map<Protocol, Class<?>> createSupportedActions() {
		Map<Protocol, Class<?>> supportedActions = new HashMap<>();
		supportedActions.put(ChangeWorkingMode.PROTOCOL, ChangeWorkingMode.class);
		
		return supportedActions;
	}
}
