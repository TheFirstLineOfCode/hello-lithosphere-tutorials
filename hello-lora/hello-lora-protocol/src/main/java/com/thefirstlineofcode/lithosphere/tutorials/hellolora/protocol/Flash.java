package com.thefirstlineofcode.lithosphere.tutorials.hellolora.protocol;

import com.thefirstlineofcode.basalt.oxm.coc.annotations.ProtocolObject;
import com.thefirstlineofcode.basalt.xmpp.core.Protocol;

@ProtocolObject(namespace="urn:leps:things:simple-light", localName="flash")
public class Flash {
	public static final Protocol PROTOCOL = new Protocol("urn:leps:things:simple-light", "flash");
	
	private int repeat;
	
	public Flash() {
		this(1);
	}
	
	public Flash(int repeat) {
		setRepeat(repeat);
	}
	
	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		if (repeat < 1)
			throw new IllegalArgumentException("Attribute repeat must be a non-zero positive integer.");
		
		this.repeat = repeat;
	}
	
	@Override
	public String toString() {
		return String.format("Flash[repeat=%d]", repeat);
	}
}
