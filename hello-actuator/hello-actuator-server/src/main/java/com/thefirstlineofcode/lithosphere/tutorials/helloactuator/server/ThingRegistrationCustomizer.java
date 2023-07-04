package com.thefirstlineofcode.lithosphere.tutorials.helloactuator.server;

import org.pf4j.Extension;

import com.thefirstlineofcode.sand.server.things.AbstractThingRegistrationCustomizer;

@Extension
public class ThingRegistrationCustomizer extends AbstractThingRegistrationCustomizer {
	private static final String HARD_CODED_REGISTRATION_KEY = "abcdefghijkl";
	
	@Override
	public boolean isUnregisteredThing(String thingId, String registrationKey) {
		if (!super.isUnregisteredThing(thingId, registrationKey))
			return false;
		
		return HARD_CODED_REGISTRATION_KEY.equals(registrationKey);
	}
}
