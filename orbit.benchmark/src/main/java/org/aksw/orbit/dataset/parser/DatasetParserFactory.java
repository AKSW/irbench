package org.aksw.orbit.dataset.parser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DatasetParserFactory {
	
	private final static String TREC_ANSWERS = "a.trec";
	private final static String TREC_QUESTIONS = "q.trec";
	private final static String QALD_XML = "qald.xml";
	private final static String QALD_JSON = "qald.json";
	
	
	public DatasetParser getDatasetParser(URL fileURL) {
		String name = fileURL.getFile();
		if(name.endsWith(TREC_QUESTIONS)) {
			return new TRECQueriesParser();
		} else if (name.endsWith(TREC_ANSWERS)) {
			return new TRECAnswersParser();
		} else if (name.endsWith(QALD_XML)) {
			return new QALDXMLParser();
		} else if (name.endsWith(QALD_JSON)) {
			return new QALDJSONParser();
		}
		return null;
	}
	
	public DatasetParser getDatasetParser(File file) throws MalformedURLException {
		URL url = file.toURI().toURL();
		return getDatasetParser(url);
	}
}
