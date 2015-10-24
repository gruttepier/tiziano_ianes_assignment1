package marshallOperations;

import model.generated.PeopleType;
import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

public class JSONConverter {

	/**
	 * A method that takes the content of a fixed xml file and converts it
	 * producing a json file
	 * 
	 * @throws Exception
	 */
	public static void convert(File xmlDocument) throws Exception {

		// unmarshall the xml file
		JAXBUnMarshaller uM = new JAXBUnMarshaller();
		PeopleType people = uM.unMarshall(xmlDocument);

		// Jackson Object Mapper
		ObjectMapper mapper = new ObjectMapper();

		// Adding the Jackson Module to process JAXB annotations
		JaxbAnnotationModule module = new JaxbAnnotationModule();

		// configure as necessary
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		
		
		
		String result = mapper.writeValueAsString(people);
		System.out.println(result);
		mapper.writeValue(new File("people.json"), people);
	}

	public static void main(String[] args) throws Exception {
		convert(new File("marshalledPeople.xml"));
	}
}
