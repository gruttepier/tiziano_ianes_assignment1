package marshallOperations;

import model.generated.*;
import utils.ParameterGenerator;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class JAXBMarshaller {
	/**
	 * Generate the XML Document given a file and a PeopleType array of people
	 * 
	 * @param xmlDocument
	 * @param people
	 * @throws DatatypeConfigurationException
	 */
	public void generateXMLDocument(File xmlDocument, PeopleType people) throws DatatypeConfigurationException {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance("model.generated");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
			model.generated.ObjectFactory factory = new ObjectFactory();

			JAXBElement<PeopleType> peopleElement = factory.createPeople(people);

			System.out.println("Marshalling on " + xmlDocument.getName());

			marshaller.marshal(peopleElement, new FileOutputStream(xmlDocument));

		} catch (IOException e) {
			System.out.println(e.toString());

		} catch (JAXBException e) {
			System.out.println(e.toString());

		}

	}

	/**
	 * A self contained method for generating people and marshalling them into
	 * an XML file
	 * 
	 * @param xmlDocument
	 * @throws DatatypeConfigurationException
	 * @throws JAXBException
	 * @throws FileNotFoundException
	 */
	private static void myMarshal(File xmlDocument)
			throws DatatypeConfigurationException, JAXBException, FileNotFoundException {

		JAXBContext jaxbContext = JAXBContext.newInstance("model.generated");
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty("jaxb.formatted.output", new Boolean(true));
		model.generated.ObjectFactory factory = new ObjectFactory();

		PeopleType people = factory.createPeopleType();

		// Since generating truly randomized names goes beyond the scope of the
		// assignment, the values are obtained by mapping these arrays of names
		String[] fnames = { "Cora", "Joshua", "Azhurlar" };
		String[] lnames = { "Crawford", "Briones", "Trisra" };
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

		JAXBElement<PeopleType> peopleElement = factory.createPeople(people);

		System.out.println("Marshalling on " + xmlDocument);
		System.out.println("Marshalling on main system output (for reference)");

		marshaller.marshal(peopleElement, new FileOutputStream(xmlDocument));
		marshaller.marshal(peopleElement, System.out);
	}

	public static void main(String[] args) throws DatatypeConfigurationException, FileNotFoundException, JAXBException {
		myMarshal(new File("marshalledPeople.xml"));
	}

}
