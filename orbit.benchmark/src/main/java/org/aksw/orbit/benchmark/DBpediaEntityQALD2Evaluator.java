package org.aksw.orbit.benchmark;

import java.util.Map;

import org.aksw.orbit.benchmark.measure.Measure;
import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.Question;

public class DBpediaEntityQALD2Evaluator extends Evaluator {
	
	public static final String DATASET_PRAGMA = "QALD2";
	
	public DBpediaEntityQALD2Evaluator(Dataset system, 
			Map<String, Measure> measures) {
		super(system, measures);
	}
	
	@Override
	public void visit(Question q) throws Exception {
		if(q.getId().contains(DATASET_PRAGMA)) {
			super.visit(q);
		}
	}
}