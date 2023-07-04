package com.thefirstlineofcode.lithosphere.tutorials.helloactuator.thing;

import com.thefirstlineofcode.chalk.core.stream.StandardStreamConfig;
import com.thefirstlineofcode.chalk.logger.LogConfigurator;
import com.thefirstlineofcode.chalk.logger.LogConfigurator.LogLevel;
import com.thefirstlineofcode.lithosphere.tutorials.helloactuator.protocol.HatModelDescriptor;

public class Main {
	private HelloActuatorThing simpleLight;
	
	public static void main(String[] args) {
		new Main().run(args);
	}

	private void run(String[] args) {
		if (args.length == 1 && args[0].equals("--help")) {
			printUsage();
			
			return;
		}
		
		String host = null;
		Integer port = null;
		Boolean tlsPreferred = null;
		
		for (int i = 0; i < args.length; i++) {
			if (!args[i].startsWith("--")) {
				throw new IllegalArgumentException("Illegal option format.");
			}
			
			int equalSignIndex = args[i].indexOf('=');
			if (equalSignIndex == 2 ||
					equalSignIndex == (args[i].length() - 1)) {
				throw new IllegalArgumentException("Illegal option format.");
			}
			
			String name, value;
			if (equalSignIndex == -1) {
				name = args[i].substring(2,  args[i].length());
				value = "TRUE";
			} else {
				name = args[i].substring(2, equalSignIndex).trim();
				value = args[i].substring(equalSignIndex + 1, args[i].length()).trim();
			}
			
			if ("help".equals(name)) {
				throw new IllegalArgumentException("Illegal option format.");
			} else if ("host".equals(name)) {
				host = value;
			} else if ("port".equals(name)) {
				port = Integer.parseInt(value);
			} else if ("tls-preferred".equals(name)) {
				tlsPreferred = Boolean.parseBoolean(value);
			} else {
				throw new IllegalArgumentException(String.format("Unknown option: %s.", name));				
			}
		}
		
		new LogConfigurator().configure(HatModelDescriptor.MODEL_NAME, LogLevel.INFO);
		
		try {
			simpleLight = new HelloActuatorThing();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			printUsage();
			
			return;
		}
		
		StandardStreamConfig streamConfig = simpleLight.getStreamConfig();
		simpleLight.setStreamConfig(mergeStreamConfig(streamConfig, host, port, tlsPreferred));
		
		simpleLight.start();
	}
	
	private StandardStreamConfig mergeStreamConfig(StandardStreamConfig streamConfig, String host, Integer port,
			Boolean tlsPreferred) {
		if (streamConfig == null) {
			if (host == null)
				host = "localhost";
			
			if (port == null)
				port = 6222;
			
			if (tlsPreferred == null)
				tlsPreferred = false;
			
			return new StandardStreamConfig(host, port, tlsPreferred);
		}
		
		if (host != null)
			streamConfig.setHost(host);
		
		if (port != null)
			streamConfig.setPort(port);
		
		if (tlsPreferred != null)
			streamConfig.setTlsPreferred(tlsPreferred);
		
		return streamConfig;
	}

	private void printUsage() {
		System.out.println("Usage: java -jar hello-actuator-thing--${VERSION}.jar [OPTIONS]");
		System.out.println("OPTIONS:");
		System.out.println("--help                            Display help information.");
		System.out.println("--host=HOST                       Specify host name of server.");
		System.out.println("--port=PORT                       Specify server port.");
		System.out.println("--tls-preferred                   Specify whether TLS is preferred when connecting to server.");
	}
}
