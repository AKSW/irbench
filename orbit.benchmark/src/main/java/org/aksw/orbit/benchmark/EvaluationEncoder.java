package org.aksw.orbit.benchmark;

import java.util.Map;

public interface EvaluationEncoder {
	public String encode(Map<String, QuestionScoreMap> benchmark) throws Exception;
}
