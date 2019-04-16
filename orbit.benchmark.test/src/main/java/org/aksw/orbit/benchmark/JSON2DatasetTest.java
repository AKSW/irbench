package org.aksw.orbit.benchmark;

import java.io.InputStream;

import org.aksw.orbit.benchmark.qald.schema.Dataset;
import org.aksw.orbit.benchmark.qald.schema.Question;
import org.aksw.orbit.dataset.parser.QALDJSONParser;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

public class JSON2DatasetTest {

	@Test
	public void testJSON2Dataset() throws JSONException {
		InputStream is = JSON2DatasetTest.class.getResourceAsStream("qald-7-train-multilingual.json");
		QALDJSONParser parser = new QALDJSONParser();
		Dataset dataset = parser.parse(is);
		
		Assert.assertTrue(dataset.getId().equals("qald-7-train-multilingual"));
		
		Question q0 = dataset.getQuestion().get(0);
		
		Assert.assertTrue(q0.getAggregation().equals("false"));
		Assert.assertTrue(q0.getAnswertype().equals("date"));
		Assert.assertTrue(q0.getId().equals("0"));
		Assert.assertTrue(q0.getOnlydbo().equals("true"));
		Assert.assertTrue(q0.getHybrid().equals("false"));
		
		Assert.assertTrue(q0.getQuery().equals("PREFIX dbo: <http://dbpedia.org/ontology/>\nPREFIX res: <http://dbpedia.org/resource/>\nSELECT DISTINCT ?date \nWHERE { \n       res:Battle_of_Gettysburg dbo:date ?date .\n}"));
		
		Assert.assertTrue(q0.getString().get(0).getValue().equals("When was the Battle of Gettysburg?"));
		Assert.assertTrue(q0.getString().get(0).getLang().equals("en"));
		
		Assert.assertTrue(q0.getKeywords().get(0).getValue().equals("Battle of Gettysburg"));
		Assert.assertTrue(q0.getKeywords().get(0).getLang().equals("en"));
		
		Assert.assertTrue(q0.getAnswers().getAnswer().get(0).getLiteral().equals("1863-07-03"));
	}
}
