package org.aksw.orbit.benchmark.prompt;

public abstract class AbstractSimpleCommandOption implements CommandOption {

	private String option;
	
	public AbstractSimpleCommandOption(String option) {
		this.option = option;
	}
	
	public boolean canProcess(String[] args) {		
		return args.length == 1 && args[0].contains(option); 
	}
	
}
