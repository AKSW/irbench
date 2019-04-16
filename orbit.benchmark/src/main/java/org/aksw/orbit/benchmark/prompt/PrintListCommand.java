package org.aksw.orbit.benchmark.prompt;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrintListCommand extends AbstractSimpleCommandOption {
	
	List<String> list;

	public PrintListCommand(String command, List<String> list) {
		super(command);
		this.list = list;
	}
	
	public PrintListCommand(String command, Set<String> list) {
		this(command, new ArrayList<String>(list));
	}
	
	@Override
	public void process(String[] args) throws Exception {
		for(String entry : list) {
			System.out.println(entry);
		}
	}

}
