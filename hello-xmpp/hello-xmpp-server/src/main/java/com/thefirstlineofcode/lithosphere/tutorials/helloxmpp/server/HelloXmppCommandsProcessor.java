package com.thefirstlineofcode.lithosphere.tutorials.helloxmpp.server;

import org.pf4j.Extension;

import com.thefirstlineofcode.granite.framework.core.annotations.BeanDependency;
import com.thefirstlineofcode.granite.framework.core.auth.IAccountManager;
import com.thefirstlineofcode.granite.framework.core.console.AbstractCommandsProcessor;
import com.thefirstlineofcode.granite.framework.core.console.IConsoleSystem;

@Extension
public class HelloXmppCommandsProcessor extends AbstractCommandsProcessor {
	private static final String COMMAND_GROUP_SAND_DEMO = "hello-xmpp";
	private static final String COMMANDS_GROUP_INTRODUCTION = "Commands for hello XMPP tutorial.";
	private static final String USER_NAME = "geologist";
	private static final String USER_PASSWORD = "I like lithosphere.";
	
	@BeanDependency
	private IAccountManager accountManager;

	@Override
	public String getGroup() {
		return COMMAND_GROUP_SAND_DEMO;
	}

	@Override
	public String[] getCommands() {
		return new String[] {"help", "create-test-user"};
	}

	@Override
	public String getIntroduction() {
		return COMMANDS_GROUP_INTRODUCTION;
	}

	@Override
	public void printHelp(IConsoleSystem consoleSystem) {
		consoleSystem.printTitleLine(String.format("%s Available commands:", getIntroduction()));
		consoleSystem.printContentLine("hello-xmpp help - Display the help information for hello XMPP tutorial command group.");
		consoleSystem.printContentLine("hello-xmpp create-test-user - Create a test user for hello XMPP tutorial.");
	}
	
	public void processCreateTestUser(IConsoleSystem consoleSystem) {
		if (accountManager.exists(USER_NAME)) {
			consoleSystem.printMessageLine("Test user for hello XMPP tutorial has already existed in system. Ignore to execute the command.");			
		} else {
			accountManager.add(USER_NAME, USER_PASSWORD);
			
			consoleSystem.printMessageLine("The test user for hello XMPP tutorial has been created.");
		}
	}
}
