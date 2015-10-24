package utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Contains methods for the randomized generation of the parameters of PersonType and HealthprofileType
 * @author Tiziano Ianes
 */
public class ParameterGenerator {
	private double minHeight;
	private double maxHeight;
	private double minWeight;
	private double maxWeight;
	private int minAge;
	private int maxAge;
	
	/**
	 * Initialize parameters to set values
	 */
	public ParameterGenerator() {
		this.minHeight = 1.0;
		this.maxHeight = 2.1;
		this.minWeight = 25;
		this.maxWeight = 120;
		this.minAge = 18;
		this.maxAge = 90;
	}
	
	/**
	 * Generates a random value for the the height, comprised between set maximum and minimum values
	 * The value is rounded to contain 2 decimals
	 * @return the generated height
	 */
	public double generateHeight() {
		double height = minHeight + Math.random() * (maxHeight - minHeight);
		return Math.floor(height * 1e2) / 1e2;
	}

	/**
	 * Generates a random value for the the weight, comprised between set maximum and minimum values
	 * The value is rounded to contain 2 decimals 
	 * @return the generated height
	 */
	public double generateWeight() {
		double weight = minWeight + Math.random() * (maxWeight - minWeight);
		return Math.floor(weight * 1e2) / 1e2;
	}
	
	/**
	 * Calculates the value of the B.M.I., given the height and weight of the subject
	 * @param height the height to use for the calculation
	 * @param weight the weight to use for the calculation
	 * @return the calculated value
	 */
	public double generateBmi(double height, double weight) {
		double bmi = weight / Math.pow(height, 2);
		return Math.floor(bmi * 1e2) / 1e2;

	}

	/**
	 * Generates a random birth date such that the age of the subject is comprised between set maximum and minimum values
	 * @return the generated birth date
	 * @throws DatatypeConfigurationException
	 */
	public XMLGregorianCalendar generateBirthday() throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();

		int minYear = Calendar.getInstance().get(Calendar.YEAR) - maxAge;
		int maxYear = Calendar.getInstance().get(Calendar.YEAR) - minAge;

		int year = (int) Math.round(minYear + Math.random() * (maxYear - minYear));
		c.set(Calendar.YEAR, year);
		int dayOfYear = (int) Math.round(1 + Math.random() * (c.getActualMaximum(Calendar.DAY_OF_YEAR) - 1));
		c.set(Calendar.DAY_OF_YEAR, dayOfYear);

		return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	}

	/**
	 * Given the birth date, generates a random date that is comprised between the birth date and the current date
	 * @param birthday the birth date that sets the earliest date possible
	 * @return the generated date
	 * @throws DatatypeConfigurationException
	 */
	public XMLGregorianCalendar generatLastUpdate(XMLGregorianCalendar birthday) throws DatatypeConfigurationException {
		GregorianCalendar c = new GregorianCalendar();

		int minYear = birthday.getYear();
		int maxYear = Calendar.getInstance().get(Calendar.YEAR);

		int year = (int) Math.round(minYear + Math.random() * (maxYear - minYear));
		c.set(Calendar.YEAR, year);
		int dayOfYear = (int) Math.round(1 + Math.random() * (c.getActualMaximum(Calendar.DAY_OF_YEAR) - 1));
		c.set(Calendar.DAY_OF_YEAR, dayOfYear);

		return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
	}

}
