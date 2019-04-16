package org.aksw.orbit.dataset.parser;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.aksw.orbit.benchmark.qald.schema.Dataset;

public class QALDXMLParser extends AbstractDatasetParser {

	@Override
	public Dataset parse(InputStream is) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Dataset.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Dataset instance = (Dataset) jaxbUnmarshaller.unmarshal(is);
		return instance;
	}

}
