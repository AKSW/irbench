package org.aksw.orbit.benchmark.prompt;

import java.util.Arrays;
import java.util.List;

public class CommandFactory {
	List<CommandOption> options;
	public CommandFactory(List<CommandOption> options) {
		this.options = options;
	}
	
	public CommandFactory(CommandOption... options) {
		this.options = Arrays.asList(options);
	}
	
	public CommandOption getCommandOption(String args[]) {
		for(CommandOption command : options) {
			if(command.canProcess(args)) {
				return command;
			}
		}
		return null;
	}
}
