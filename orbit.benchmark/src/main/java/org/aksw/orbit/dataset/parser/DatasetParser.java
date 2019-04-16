package org.aksw.orbit.dataset.parser;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.aksw.orbit.benchmark.qald.schema.Dataset;

public interface DatasetParser {
	public Dataset parse(InputStream is) throws Exception;
	public Dataset parse(File file) throws Exception;
	public Dataset parse(URL file) throws Exception;
}
