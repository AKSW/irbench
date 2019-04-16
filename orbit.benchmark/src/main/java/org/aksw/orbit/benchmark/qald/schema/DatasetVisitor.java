package org.aksw.orbit.benchmark.qald.schema;

public interface DatasetVisitor {

	public void visit(Dataset dataset) throws Exception;

	public void visit(Question q) throws Exception;

}
