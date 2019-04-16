package org.aksw.orbit.benchmark;

import java.util.HashMap;

public class QuestionScoreMap extends HashMap<String, Double> {
	
	private static final long serialVersionUID = 1L;
	
	public double getAVG() {		
		if(size() == 0) {
			return 0;
		}		
		double value = 0;
		for(Double qResult: values()) {
			value += qResult;
		}
		return value / size();
	}
	
}
