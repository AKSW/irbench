package org.aksw.orbit.benchmark.prompt;

import java.util.Map;

public abstract class AbstractSimpleMapCommandOption extends MapCommandOption {

	private String option;
	
	public AbstractSimpleMapCommandOption(String option) {
		this.option = option;
	}

	@Override
	public boolean canProcess(Map<String, String[]> commands) {
		return commands.containsKey(option);
	}
	
	public String getOption() {
		return option;
	}
}
