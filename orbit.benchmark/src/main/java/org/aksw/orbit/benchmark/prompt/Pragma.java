package org.aksw.orbit.benchmark.prompt;

public class Pragma {
	String option;
	public Pragma(String option) {
		this.option = option;
	}
	
	public boolean contains(String args[]) {
		for(String arg : args) {
			if(arg.equals(option)) {
				return true;
			}
		}
		return false;
	}
}
