package marshallOperations;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import model.generated.*;

public class JAXBUnMarshaller {
	public PeopleType unMarshall(File xmlDocument) {
		PeopleType people=new PeopleType();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("model.generated");

			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = schemaFactory.newSchema(new File("people.xsd"));
			unMarshaller.setSchema(schema);
			CustomValidationEventHandler validationEventHandler = new CustomValidationEventHandler();
			unMarshaller.setEventHandler(validationEventHandler);

			@SuppressWarnings("unchecked")
			JAXBElement<PeopleType> peopleElement = (JAXBElement<PeopleType>) unMarshaller.unmarshal(xmlDocument);

			people = peopleElement.getValue();

		} catch (JAXBException e) {
			System.out.println(e.toString());
		} catch (SAXException e) {
			System.out.println(e.toString());
		}
		return people;
	}

	public static void main(String[] argv) {

		JAXBUnMarshaller jaxbUnmarshaller = new JAXBUnMarshaller();
		PeopleType people=jaxbUnmarshaller.unMarshall(new File("marshalledPeople.xml"));
		
		System.out.println("UnMarshalled Java item printed for reference");
		List<PersonType> personList=people.getPerson();
		for(PersonType p:personList){
			System.out.println(">>>PERSON "+p.getId()+":");
			System.out.println(">>>-first name: "+p.getFirstname());
			System.out.println(">>>-last name:  "+p.getLasttname());
			System.out.println(">>>-birthday:   "+p.getBirthday());
			System.out.println(">>>-healthprofile");
			System.out.println(">>>-------------last update: "+p.getHealthprofile().getLastupdate());
			System.out.println(">>>-------------weight:      "+p.getHealthprofile().getWeight());
			System.out.println(">>>-------------height:      "+p.getHealthprofile().getHeight());
			System.out.println(">>>-------------bmi:         "+p.getHealthprofile().getBmi());
		}

	}

	class CustomValidationEventHandler implements ValidationEventHandler {
		public boolean handleEvent(ValidationEvent event) {
			if (event.getSeverity() == ValidationEvent.WARNING) {
				return true;
			}
			if ((event.getSeverity() == ValidationEvent.ERROR)
					|| (event.getSeverity() == ValidationEvent.FATAL_ERROR)) {

				System.out.println("Validation Error:" + event.getMessage());

				ValidationEventLocator locator = event.getLocator();
				System.out.println("at line number:" + locator.getLineNumber());
				System.out.println("Unmarshalling Terminated");
				return false;
			}
			return true;
		}

	}
}
