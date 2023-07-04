package com.thefirstlineofcode.lithosphere.tutorials.helloactuator.thing;

import com.thefirstlineofcode.sand.client.thing.IThing;
import com.thefirstlineofcode.sand.protocols.actuator.ExecutionException;

public interface ISimpleLight extends IThing {	
	void turnOn();
	void turnOff();
	void flash(int repeat) throws ExecutionException;
}
