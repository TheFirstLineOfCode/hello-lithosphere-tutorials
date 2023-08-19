package com.thefirstlineofcode.lithosphere.tutorials.hellowebrtc.thing;

import com.thefirstlineofcode.sand.client.edge.AbstractEdgeThing;
import com.thefirstlineofcode.sand.client.thing.ThingsUtils;
import com.thefirstlineofcode.sand.client.webcam.IWebcam;
import com.thefirstlineofcode.sand.client.webcam.Webcam;
import com.thefirstlineofcode.sand.client.webcam.WebcamPlugin;

public class HelloWebrtcThing extends AbstractEdgeThing {
	public static final String THING_MODEL = "HWT";
	public static final String SOFTWARE_VERSION = "1.0.0-BETA3";
	
	private IWebcam.Capability requestedCapability;
	private Webcam webcam;
	
	public HelloWebrtcThing() {
		this(new IWebcam.Capability(800, 600, 30));
	}
	
	public HelloWebrtcThing(IWebcam.Capability requestedCapability) {
		super(THING_MODEL, null, true);
		
		this.requestedCapability = requestedCapability;
	}

	@Override
	public String getSoftwareVersion() {
		return SOFTWARE_VERSION;
	}

	@Override
	protected void registerIotPlugins() {
		chatClient.register(WebcamPlugin.class);
	}

	@Override
	protected void startIotComponents() {
		if (webcam == null)
			webcam = chatClient.createApiImpl(Webcam.class);
		
		webcam.setRequestedCapability(requestedCapability);
		webcam.start();
	}

	@Override
	protected void stopIotComponents() {
		webcam.stop();
	}

	@Override
	protected String loadThingId() {
		return THING_MODEL + "-" + ThingsUtils.generateRandomId(8);
	}

	@Override
	protected String loadRegistrationCode() {
		return "abcdefghijkl";
	}
}
