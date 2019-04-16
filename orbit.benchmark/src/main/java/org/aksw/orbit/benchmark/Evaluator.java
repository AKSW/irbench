package org.aksw.orbit.benchmark;

import java.util.HashMap;
import java.util.Map;

import org.aksw.orbit.benchmark.measure.Measure;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.DatasetVisitor;
import org.aksw.orbit.benchmark.qald.schema.Question;
import org.aksw.orbit.utils.DatasetUtils;

public class Evaluator implements DatasetVisitor {
	
	private Dataset system;
	private Map<String, Measure> measures;
	private Map<String, QuestionScoreMap> result = new HashMap<String, QuestionScoreMap>(); 

	public Evaluator(Dataset system, Map<String, Measure> measures) {
		this.system = system;
		this.measures = measures;
	}
	
	@Override
	public void visit(Dataset dataset) throws Exception {
	}

	@Override
	public void visit(Question q) throws Exception {
		for(String measure : measures.keySet()) {
			Measure m = measures.get(measure);
			QuestionScoreMap questionScoreMap = result.get(measure);
			if(questionScoreMap == null) {
				questionScoreMap = new QuestionScoreMap();
				result.put(measure, questionScoreMap);
			}
			double value = 0.;
			String questionID = q.getId();
			Question systemQ = DatasetUtils.getQuestion(questionID, system);
			if(systemQ != null && m != null) {
				value = m.evaluate(q, systemQ);
				questionScoreMap.put(questionID, value);
			}
		}
	}
	
	public Map<String, QuestionScoreMap> getEvaluation() {
		return result;
	}	
	
	public String getEvaluation(EvaluationEncoder encoder) throws Exception {
		return encoder.encode(result);
	}
}
