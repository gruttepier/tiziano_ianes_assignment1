
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utils.MyListFormatter;

public class HealthProfileReader {

	private Document doc;
	private XPath xpath;

	public void loadXML() throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		doc = builder.parse("people.xml");

		// creating xpath object
		getXPathObj();
	}

	public XPath getXPathObj() {

		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
		return xpath;
	}

	/**
	 * Verifies of a
	 * 
	 * @param str
	 * @return
	 */
	private boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the attribute Weight of a person with given id
	 * 
	 * @param id
	 * @return
	 * @throws XPathExpressionException
	 */
	private double getWeight(long id) throws XPathExpressionException {

		String path = "/people/person[@id=" + id + "]/healthprofile/weight";

		System.out.println("Reading weight of person with id = " + id + "...");
		System.out.println("(using xpath = " + path);

		XPathExpression expr = xpath.compile(path);
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		if (node != null)
			return Double.parseDouble(node.getTextContent());
		else
			return -1;
	}

	/**
	 * Returns the attribute Weight of a person with given id
	 * 
	 * @param id
	 * @return
	 * @throws XPathExpressionException
	 */
	private double getHeight(long id) throws XPathExpressionException {
		String path = "/people/person[@id='" + id + "']/healthprofile/height";

		System.out.println("Reading height of person with id = " + id + "...");
		System.out.println("(using xpath = " + path);

		XPathExpression expr = xpath.compile(path);
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		if (node != null)
			return Double.parseDouble(node.getTextContent());
		else
			return -1;
	}

	/**
	 * Print the content of the file in detail
	 * 
	 * @throws XPathExpressionException
	 */
	private void printDetail() throws XPathExpressionException {
		String path = "/people/person";

		System.out.println("Reading people details...");
		System.out.println("(using xpath = " + path);

		XPathExpression expr = xpath.compile(path);
		NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		
		if (nodes.getLength() > 0) {
			for (int i = 0; i < nodes.getLength(); i++) {
				NamedNodeMap att = nodes.item(i).getAttributes();
				System.out.println("----"+nodes.item(i).getNodeName() + " " + att.item(0)+"----"+nodes.item(i).getTextContent());
			}
		} else {
			System.out.println("No people in db");
		}
	}

	/**
	 * Prints the healthprofile of a person with given id
	 * 
	 * @param id
	 * @throws XPathExpressionException
	 */
	private void printHealthprofile(long id) throws XPathExpressionException {
		String path = "/people/person[@id='" + id + "']/healthprofile";

		System.out.println("Reading HealthProfile of person with id = " + id + "...");
		System.out.println("(using xpath = " + path);

		XPathExpression expr = xpath.compile(path);
		Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
		if (node != null)
			System.out.println("HealthProfile of person " + id + ": " + node.getTextContent());
		else
			System.out.println("No result found for the specified id");
	}

	/**
	 * Prints all the people that satisfy the criteria expressed by an
	 * expression formed by a given operator and a given weight. Operator are
	 * checked to be contained within a selected array of accepted operators.
	 * 
	 * @param operator
	 *            The specified operator to be used in the expression
	 * @param weight
	 *            The specified weight to be used in the expression
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	private void printOperatorWeight(String operator, double weight)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		ArrayList<String> acceptedOperators = new ArrayList<String>(Arrays.asList("<", ">", "="));
		if (acceptedOperators.contains(operator)) {
			String path = "/people/person[./healthprofile/weight" + operator + "'" + weight + "']";
			System.out.println("Reading list of people with weight " + operator + " " + weight + "...");
			System.out.println("(using xpath = " + path);

			XPathExpression expr = xpath.compile(path);
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

			if (nodes.getLength() > 0) {
				for (int i = 0; i < nodes.getLength(); i++)
					System.out.print(nodes.item(i).getTextContent());
			} else {
				System.out.println("No result found for the specified property");
			}
		} else {
			MyListFormatter lf = new MyListFormatter();
			System.out.print("Invalid operator. Please specify ");
			lf.printListCommaOr(acceptedOperators);
			System.out.print(" operator");
		}
	}

	public static void main(String[] args) throws Exception {

		HealthProfileReader hpReader = new HealthProfileReader();
		hpReader.loadXML();

		String method = args[0];
		switch (method) {
		case "Weight":
			if (hpReader.isNumeric(args[1])) {

				double w = hpReader.getWeight(Long.parseLong(args[1]));
				if (w != (-1))
					System.out.println(">>>Weight: " + w);
				else
					System.out.println("No result found for selected id");

			} else
				System.out.println("ERROR: Invalid argument for method " + method);

			break;
		case "Height":

			if (hpReader.isNumeric(args[1])) {

				double h = hpReader.getHeight(Long.parseLong(args[1]));
				if (h != (-1))
					System.out.println(">>>Height: " + h);
				else
					System.out.println(">>>No result found for selected id");

			} else
				System.out.println("ERROR: Invalid argument for method " + method);

			break;
		case "Detail":

			hpReader.printDetail();

			break;
		case "Healthprofile":

			if (hpReader.isNumeric(args[1])) {

				hpReader.printHealthprofile(Long.parseLong(args[1]));

			} else
				System.out.println("ERROR: Invalid argument for method " + method);

			break;
		case "OperatorWeight":

			if (hpReader.isNumeric(args[2]))

				hpReader.printOperatorWeight(args[1], Double.parseDouble(args[2]));

			else
				System.out.println("ERROR: Invalid argument for method " + method);
			break;
		default:
			System.out.print("Unsupported methos");
			break;
		}
	}
}
