package org.aksw.orbit.dataset.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.aksw.orbit.benchmark.qald.schema.Dataset;

public abstract class AbstractDatasetParser implements DatasetParser {
	@Override
	public Dataset parse(File file) throws Exception {
		try(FileInputStream fis = new FileInputStream(file)) {
			return parse(fis);
		}
	}
	
	@Override
	public Dataset parse(URL url) throws Exception {
		try(InputStream fis = url.openStream()) {
			return parse(fis);
		}
	}
}
