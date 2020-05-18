package org.aksw.orbit.benchmark.prompt;

import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class DebugCommand extends AbstractSimpleMapCommand {

	public DebugCommand(String command) {
		super(command);
	}

	@Override
	public void process(Map<String, String[]> commands) throws Exception {
		Logger root = Logger.getRootLogger();
		root.setLevel(Level.WARN);
	}

}
