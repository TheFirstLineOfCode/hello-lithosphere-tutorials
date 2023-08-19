package com.thefirstlineofcode.lithosphere.tutorials.hellowebrtc.server;

import com.thefirstlineofcode.sand.protocols.thing.SimpleThingModelDescriptor;

public class HwtModelDescriptor extends SimpleThingModelDescriptor {
	public static final String MODEL_NAME = "HWT";
	public static final String DESCRIPTION = "Hello WebRTC Thing";
	
	public HwtModelDescriptor() {
		super(MODEL_NAME, DESCRIPTION, true, null, null, null);
	}
}
