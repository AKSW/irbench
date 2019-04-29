package org.aksw.orbit.benchmark.prompt;

public interface Command {
	
	public abstract boolean canProcess(String[] args);
	
	public abstract void process(String[] args) throws Exception;
}
