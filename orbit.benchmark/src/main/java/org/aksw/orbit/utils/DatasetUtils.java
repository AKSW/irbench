package org.aksw.orbit.utils;

import java.io.File;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.Question;
import org.aksw.orbit.dataset.parser.DatasetParser;
import org.aksw.orbit.dataset.parser.DatasetParserFactory;

public class DatasetUtils {
	public static Question getQuestion(String id, Dataset dataset) {
		return getQuestion(id, dataset.getQuestion());
	}
	
	public static Question getQuestion(String id, List<Question> questions) {
		for(Question question : questions) {
			if(question.getId().equals(id)) {
				return question;
			}
		}
		return null;
	}

	public static Dataset getDataset(URL datasetURL) throws Exception {
		DatasetParserFactory paserFactory = new DatasetParserFactory();
		DatasetParser parser = paserFactory.getDatasetParser(datasetURL);
		Dataset dataset = parser.parse(datasetURL);
		return dataset;
	}
	
	public static void serialize(Dataset dataset, OutputStream out) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Dataset.class);
		Marshaller m = context.createMarshaller();
		m.marshal(dataset, out);
	}
}
