package org.aksw.orbit.benchmark.measure;

import org.aksw.orbit.benchmark.QuestionScoreMap;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.Question;

public interface Measure {
	public double evaluate(Question benchmark, Question system);
	public QuestionScoreMap evaluate(Dataset benchmark, Dataset system);
}
