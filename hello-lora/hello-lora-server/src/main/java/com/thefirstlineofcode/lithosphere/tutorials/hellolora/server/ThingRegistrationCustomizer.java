package com.thefirstlineofcode.lithosphere.tutorials.hellolora.server;

import org.pf4j.Extension;

import com.thefirstlineofcode.sand.server.things.ThingRegistrationCustomizerAdapter;

@Extension
public class ThingRegistrationCustomizer extends ThingRegistrationCustomizerAdapter {
	private static final String HARD_CODED_REGISTRATION_CODE = "abcdefghijkl";
	
	@Override
	public boolean isUnregisteredThing(String thingId, String registrationCode) {
		if (!super.isUnregisteredThing(thingId, registrationCode))
			return false;
		
		return HARD_CODED_REGISTRATION_CODE.equals(registrationCode);
	}
	
	@Override
	public boolean isAuthorizationRequired() {
		return false;
	}
	
	@Override
	public boolean isConfirmationRequired() {
		return false;
	}
}
