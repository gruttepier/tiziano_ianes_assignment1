
import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import marshallOperations.JAXBMarshaller;
import model.generated.HealthprofileType;
import model.generated.ObjectFactory;
import model.generated.PeopleType;
import model.generated.PersonType;
import utils.ParameterGenerator;

public class HealthProfileWriter {
	/**
	 * fills and generate the people.xml file
	 * @throws JAXBException
	 * @throws DatatypeConfigurationException
	 */
	public static void initializeDB() throws JAXBException, DatatypeConfigurationException {
		String xmlDocument = "people.xml";
		JAXBMarshaller jaxbMarshaller = new JAXBMarshaller();
		ObjectFactory factory = new ObjectFactory();

		PeopleType people = factory.createPeopleType();

		// Since generating truly randomized names goes beyond the scope of the
		// assignment, the values are obtained by mapping these arrays of names
		String[] fnames = { "Muriel", "Catherine", "Melissa", "Larry", "William", "Iloerika", "Chukwubuikem", "Charlie",
				"Sienna", "Harley", "Renato", "Facino", "W'Anar", "Angela", "Joseph", "James", "Mary", "Susan",
				"Octavio", "Carol" };
		String[] lnames = { "Hickmon", "Hawkins", "Sheppard", "Farmer", "Gabrielson", "Chiedozie", "Amaechi", "Chan",
				"O'Neill", "Kaur", "DeRose", "Padovano", "Regoric", "Sibley", "Shillings", "McDonald", "Riley",
				"Simmons", "Crittendon", "Noll" };
		/////////////////////////////////////////////////////////////////////////

		List<PersonType> personList;
		PersonType person;
		HealthprofileType healthprofile;

		ParameterGenerator g = new ParameterGenerator();

		for (int i = 0; i < fnames.length; i++) {

			person = factory.createPersonType();

			person.setId((long) i);
			person.setFirstname(fnames[i]);
			person.setLasttname(lnames[i]);
			person.setBirthday(g.generateBirthday());

			healthprofile = factory.createHealthprofileType();

			healthprofile.setHeight(g.generateHeight());
			healthprofile.setWeight(g.generateWeight());
			healthprofile.setBmi(g.generateBmi(healthprofile.getHeight(), healthprofile.getWeight()));
			healthprofile.setLastupdate(g.generatLastUpdate(person.getBirthday()));

			person.setHealthprofile(healthprofile);

			personList = people.getPerson();
			personList.add(person);
		}

		jaxbMarshaller.generateXMLDocument(new File(xmlDocument), people);

	}

	public static void main(String[] args) throws Exception {
		initializeDB();
	}
}
