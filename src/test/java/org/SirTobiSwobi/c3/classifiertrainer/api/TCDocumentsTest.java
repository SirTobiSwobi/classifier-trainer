package org.SirTobiSwobi.c3.classifiertrainer.api;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.SirTobiSwobi.c3.classifiertrainer.db.Document;
import org.SirTobiSwobi.c3.classifiertrainer.db.DocumentManager;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

public class TCDocumentsTest {

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	@Test
	public void serializesToJSON() throws Exception {
		
		DocumentManager docMan =  new DocumentManager();
		docMan.addDocument(new Document(0,"first label","first content"));
		docMan.addDocument(new Document(1,"second label","second content"));
		docMan.addDocument(new Document(2,"third label","third content"));
		Document[] documents = docMan.getDocumentArray();
		TCDocument[] TCdocumentArray = new TCDocument[documents.length];
		for(int i=0; i<documents.length;i++){
			Document doc = documents[i];
			TCDocument TCdoc = new TCDocument(doc.getId(),doc.getLabel(),doc.getContent());
			TCdocumentArray[i]=TCdoc;
		}
		TCDocuments TCdocuments = new TCDocuments(TCdocumentArray);
	
		final String expected = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/TCDocuments.json"), TCDocuments.class));
		
		assertThat(MAPPER.writeValueAsString(TCdocuments)).isEqualTo(expected);
		
	}

}
