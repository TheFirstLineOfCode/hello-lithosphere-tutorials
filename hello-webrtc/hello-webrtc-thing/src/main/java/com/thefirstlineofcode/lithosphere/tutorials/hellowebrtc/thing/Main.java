package com.thefirstlineofcode.lithosphere.tutorials.hellowebrtc.thing;

import java.util.StringTokenizer;

import com.thefirstlineofcode.chalk.core.stream.StandardStreamConfig;
import com.thefirstlineofcode.chalk.logger.LogConfigurator;
import com.thefirstlineofcode.chalk.logger.LogConfigurator.LogLevel;
import com.thefirstlineofcode.sand.client.webcam.IWebcam.Capability;

public class Main {
	private HelloWebrtcThing thing;
	
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
		String requestedWebcamCapability = null;
		String logLevel = null;
		
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
			} else if ("requested-webcam-capability".equals(name)) {
				requestedWebcamCapability = value;
			} else if ("log-level".equals(name)) {
				logLevel = value;
			} else {
				throw new IllegalArgumentException(String.format("Unknown option: %s.", name));				
			}
		}
		
		if (logLevel == null)
			logLevel = "info";
		
		LogConfigurator.configure(HelloWebrtcThing.THING_MODEL, getLogLevel(logLevel));
		
		if (requestedWebcamCapability != null) {
			thing = new HelloWebrtcThing(getRequestedWebcamCapability(requestedWebcamCapability));
		} else {			
			thing = new HelloWebrtcThing();
		}
		
		StandardStreamConfig mergedStreamConfig = mergeStreamConfig(thing.getStreamConfig(), host, port, tlsPreferred);
		thing.setStreamConfig(mergedStreamConfig);
		
		thing.start();
	}
	
	private static Capability getRequestedWebcamCapability(String sRequestedWebcamCapability) {
		if (sRequestedWebcamCapability == null)
			return null;
		
		StringTokenizer st = new StringTokenizer(sRequestedWebcamCapability, ",");
		if (st.countTokens() != 3)
			throw new IllegalArgumentException("Illegal webcam capability format. Capability format: WIDTH,HEIGHT,MAX_FPS.");
		
		try {
			int width = Integer.parseInt(st.nextToken());
			int height = Integer.parseInt(st.nextToken());
			int maxFps = Integer.parseInt(st.nextToken());
			
			return new Capability(width, height, maxFps);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Illegal webcam capability format.", e);
		}
	}
	
	private LogLevel getLogLevel(String sLogLevel) {
		if ("info".equals(sLogLevel))
			return LogLevel.INFO;
		else if ("debug".equals(sLogLevel))
			return LogLevel.DEBUG;
		else if ("trace".equals(sLogLevel)) {
			return LogLevel.TRACE;
		} else {
			throw new IllegalArgumentException(String.format("Illegal log level: %s.", sLogLevel));
		}
	}

	private StandardStreamConfig mergeStreamConfig(StandardStreamConfig streamConfig, String host, Integer port,
			Boolean tlsPreferred) {
		if (streamConfig == null) {
			if (host == null)
				return null;
			
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
		System.out.println("Usage: java hello-lora-gateway--${VERSION}.jar [OPTIONS]");
		System.out.println("OPTIONS:");
		System.out.println("--help                            Display help information.");
		System.out.println("--host=HOST                       Specify host name of server.");
		System.out.println("--port=PORT                       Specify server port.");
		System.out.println("--tls-preferred                   Specify whether TLS is preferred when connecting to server.");
	}
}
