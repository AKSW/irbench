package org.aksw.orbit.benchmark;

import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.DatasetVisitor;
import org.aksw.orbit.benchmark.qald.schema.Question;

public class DBpediaEntityQALD2Dataset extends Dataset {
		
	/**
	 *
	 */
	private static final long serialVersionUID = 4177559076373859531L;
	public static final String DATASET_PRAGMA = "QALD2";
	
	public DBpediaEntityQALD2Dataset(Dataset dataset) {
		setId(dataset.getId());
		getQuestion().addAll(dataset.getQuestion());
	}

	@Override
	public void accept(DatasetVisitor visitor) throws Exception {
		visitor.visit(this);
    	for(Question q : question) {
    		if(q.getId().contains(DATASET_PRAGMA)) {
    			visitor.visit(q);
    		}
    	}
	}
}
