package com.thefirstlineofcode.lithosphere.tutorials.hellolora.gateway;

import java.util.Collections;
import java.util.Map;

import com.thefirstlineofcode.lithosphere.tutorials.hellolora.protocol.HlgModelDescriptor;
import com.thefirstlineofcode.lithosphere.tutorials.hellolora.protocol.HltModelDescriptor;
import com.thefirstlineofcode.sand.client.actuator.IActuator;
import com.thefirstlineofcode.sand.client.edge.AbstractEdgeThing;
import com.thefirstlineofcode.sand.client.lora.gateway.ChangeWorkingModeExecutor;
import com.thefirstlineofcode.sand.client.lora.gateway.ILoraGateway;
import com.thefirstlineofcode.sand.client.lora.gateway.LoraGatewayPlugin;
import com.thefirstlineofcode.sand.client.lpwanconcentrator.ILpwanConcentrator;
import com.thefirstlineofcode.sand.client.pi.ashining.As32Ttl100LoraCommunicator;
import com.thefirstlineofcode.sand.client.thing.ThingsUtils;
import com.thefirstlineofcode.sand.client.thing.commuication.ICommunicator;
import com.thefirstlineofcode.sand.protocols.lora.gateway.ChangeWorkingMode;
import com.thefirstlineofcode.sand.protocols.thing.lora.LoraAddress;

public class HelloLoraGateway extends AbstractEdgeThing {
	public static final String THING_MODEL = HlgModelDescriptor.MODEL_NAME;
	public static final String SOFTWARE_VERSION = "1.0.0-BETA3";
	
	private static final String ATTRIBUTE_NAME_COMMUNICATOR_HAS_CONFIGURED = "communicator_has_configured";
	
	private ICommunicator<LoraAddress, LoraAddress, byte[]> communicator;
	private ILoraGateway loraGateway;
	
	public HelloLoraGateway() {
		super(THING_MODEL, null, true);
		
		communicator = createLoraCommunicator();
	}

	@Override
	public String getSoftwareVersion() {
		return SOFTWARE_VERSION;
	}

	@Override
	protected boolean doProcessAttributes(Map<String, String> attributes) {
		return initializeAndConfigureCommunicator(attributes);
	}
	
	private boolean initializeAndConfigureCommunicator(Map<String, String> attributes) {
		if (!communicator.isInitialized())
			communicator.initialize();
		
		if (!"true".equals(attributes.get(ATTRIBUTE_NAME_COMMUNICATOR_HAS_CONFIGURED))) {
			communicator.configure();
			
			attributes.put(ATTRIBUTE_NAME_COMMUNICATOR_HAS_CONFIGURED, "true");
			return true;
		}
		
		return false;
	}

	@Override
	protected void registerIotPlugins() {
		chatClient.register(LoraGatewayPlugin.class);
	}

	@Override
	protected void startIotComponents() {
		if (loraGateway == null) {
			loraGateway = chatClient.createApi(ILoraGateway.class);
			
			loraGateway.setDownlinkCommunicator(communicator);
			loraGateway.setUplinkCommunicators(Collections.singletonList(communicator));
			
			configureConcentrator(loraGateway);
		}	
		
		loraGateway.start();
	}
	
	private void configureConcentrator(ILoraGateway gateway) {
		ILpwanConcentrator concentrator = gateway.getLpwanConcentrator();
		
		concentrator.registerLanThingModel(new HltModelDescriptor());
		regiserExecutors(concentrator, gateway);
	}

	private ICommunicator<LoraAddress, LoraAddress, byte[]> createLoraCommunicator() {
		As32Ttl100LoraCommunicator communicator = new As32Ttl100LoraCommunicator();
		communicator.setMd0Pin(2);
		communicator.setMd1Pin(3);
		communicator.setAuxPin(4);
		
		return communicator;
	}

	private void regiserExecutors(IActuator actuator, final ILoraGateway loraGateway) {
		actuator.registerExecutor(ChangeWorkingMode.class, ChangeWorkingModeExecutor.class, loraGateway);
	}

	@Override
	protected void stopIotComponents() {
		loraGateway.stop();
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
