package com.thefirstlineofcode.lithosphere.tutorials.helloactuator.thing;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.thefirstlineofcode.basalt.xmpp.core.Protocol;
import com.thefirstlineofcode.chalk.core.stream.StandardStreamConfig;
import com.thefirstlineofcode.lithosphere.tutorials.helloactuator.protocol.Flash;
import com.thefirstlineofcode.lithosphere.tutorials.helloactuator.protocol.HatModelDescriptor;
import com.thefirstlineofcode.lithosphere.tutorials.helloactuator.protocol.TurnOff;
import com.thefirstlineofcode.lithosphere.tutorials.helloactuator.protocol.TurnOn;
import com.thefirstlineofcode.sand.client.actuator.ActuatorPlugin;
import com.thefirstlineofcode.sand.client.actuator.IActuator;
import com.thefirstlineofcode.sand.client.actuator.IExecutor;
import com.thefirstlineofcode.sand.client.actuator.IExecutorFactory;
import com.thefirstlineofcode.sand.client.edge.AbstractEdgeThing;
import com.thefirstlineofcode.sand.client.thing.ThingsUtils;
import com.thefirstlineofcode.sand.protocols.actuator.ExecutionException;

public class HelloActuatorThing extends AbstractEdgeThing implements ISimpleLight {
	public static final String THING_MODEL = HatModelDescriptor.MODEL_NAME;
	public static final String SOFTWARE_VERSION = "0.0.1-RELEASE";
	
	private IActuator actuator;
	private GpioController gpio;
	private GpioPinDigitalOutput ledPin;
	
	public HelloActuatorThing() {
		this(null);
	}
	
	public HelloActuatorThing(StandardStreamConfig streamConfig) {
		super(THING_MODEL, streamConfig, true);
		
		configureGpio();
	}
	
	@Override
	public String getSoftwareVersion() {
		return SOFTWARE_VERSION;
	}
	
	@Override
	protected void registerIotPlugins() {
		chatClient.register(ActuatorPlugin.class);
	}
	
	@Override
	protected void startIotComponents() {
		startActuator();
	}
	
	private void configureGpio() {
		gpio = GpioFactory.getInstance();
		ledPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08, "MyLED", PinState.LOW);
		ledPin.setShutdownOptions(true, PinState.LOW);
	}

	private void startActuator() {
		if (actuator == null) {			
			actuator = chatClient.createApi(IActuator.class);
			registerExecutors(actuator);
		}
		
		actuator.start();
	}
	
	private void registerExecutors(IActuator actuator) {
		actuator.registerExecutor(TurnOn.class, TurnOnExecutor.class, this);
		actuator.registerExecutor(TurnOff.class, TurnOffExecutor.class, this);
		actuator.registerExecutorFactory(createFlashExecutorFactory());
	}
	
	private IExecutorFactory<?> createFlashExecutorFactory() {
		return new IExecutorFactory<Flash>() {
			@Override
			public Protocol getProtocol() {
				return Flash.PROTOCOL;
			}
			
			@Override
			public Class<Flash> getActionType() {
				return Flash.class;
			}
			
			@Override
			public IExecutor<Flash> create() {
				return new FlashExecutor(HelloActuatorThing.this);
			}
		};
	}
	
	@Override
	protected void stopIotComponents() {
		if (actuator != null) {			
			actuator.stop();
			actuator = null;
		}
	}
	
	@Override
	protected String loadThingId() {
		return THING_MODEL + "-" + ThingsUtils.generateRandomId(8);
	}
	
	@Override
	protected String loadRegistrationKey() {
		return "abcdefghijkl";
	}
	
	@Override
	public void turnOn() {
		ledPin.high();
	}

	@Override
	public void turnOff() {
		ledPin.low();
	}

	@Override
	public void flash(int repeat) throws ExecutionException {
		if (repeat <= 0 || repeat > 8)
			throw new ExecutionException(-1);
		
		for (int i = 0; i < repeat; i++) {			
			flash();
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new ExecutionException(-2);
			}
		}
	}
	
	private void flash() throws ExecutionException {
		turnOn();
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			throw new ExecutionException(-2);
		}
		
		turnOff();
	}
	
	@Override
	public void start() {
		try {
			turnOff();
			
			Thread.sleep(2000);
			
			super.start();
		} catch (Exception e) {
			throw new RuntimeException("Failed to start thing.", e);
		}

	}
}
