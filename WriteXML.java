package com.mgg;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WriteXML {

	private String fileName;
	private ArrayList data;
	private String type;
	private static Document doc;

	public WriteXML(String fileName, ArrayList data, String typeOfData) throws ParserConfigurationException {
		this.fileName = fileName;
		this.data = data;
		this.type = typeOfData;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.newDocument();
		Element rootElement = doc.createElement(this.type);
		doc.appendChild(rootElement);

		try {

			if (this.type == "items") {
				for (int k = 0; k < data.size(); k++) {
					Item item = (Item) data.get(k);
					rootElement.appendChild(makeItem(item));
				}
			} else if (this.type == "persons") {
				for (int k = 0; k < data.size(); k++) {
					Person person = (Person) data.get(k);
					rootElement.appendChild(makePerson(person));
				}
			} else if (this.type == "stores") {
				for (int k = 0; k < data.size(); k++) {
					Store store = (Store) data.get(k);
					rootElement.appendChild(makeStore(store));
				}
			} else {
				System.out.println("Type not recognized");
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			// write to console or file
			StreamResult file = new StreamResult(new File(this.fileName));
			transformer.transform(source, file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Node makeElement(String elementName, String elementValue) {
		Element element = doc.createElement(elementName);
		element.appendChild(doc.createTextNode(elementValue));
		return element;
	}

	public static Node makePerson(Person person) {
		Element output = doc.createElement(person.getType());
		output.appendChild(makeElement("personCode", person.getCode()));
		output.appendChild(makeElement("lastName", person.getLastName()));
		output.appendChild(makeElement("firstName", person.getFirstName()));
		output.appendChild(makeAddress(person.getAddress()));
		output.appendChild(makeEmails(person.getEmails()));
		return output;
	}

	public static Node makeStore(Store store) {
		Element output = doc.createElement("Store");
		output.appendChild(makeElement("storeCode", store.getStoreCode()));

		ReadCSV personsCSV = new ReadCSV("data/Persons.csv");
		ArrayList<String> personsCSVArray = personsCSV.getData();
		for (int n = 0; n < personsCSV.getMemberCount(); n++) {
			String personTokens[] = personsCSVArray.get(n).split(",");
			String code = personTokens[0];
			if (code.equals(store.getManagerCode())) {
				String lastName = personTokens[2];
				String firstName = personTokens[3];
				String street = personTokens[4];
				String city = personTokens[5];
				String state = personTokens[6];
				String zipCode = personTokens[7];
				String country = personTokens[8];
				HashSet<String> emails = new HashSet<>();
				for (int m = 9; m < personTokens.length; m++) {
					emails.add(personTokens[m]);
				}
				output.appendChild(makePerson(new Person(code, "manager", lastName, firstName, street, city, state,
						zipCode, country, emails)));
			}
		}
		output.appendChild(makeAddress(store.getAddress()));
		return output;
	}

	public static Node makeItem(Item item) {
		Element output = doc.createElement(item.getType());
		output.appendChild(makeElement("itemCode", item.getCode()));
		output.appendChild(makeElement("name", item.getName()));
		if (!item.getType().equals("GiftCard")) {
			output.appendChild(makeElement("BasePrice", String.valueOf(item.getBasePrice())));
		}
		return output;
	}

	public static Node makeAddress(Address addy) {

		Element address = doc.createElement("address");
		address.appendChild(makeElement("street", addy.getStreet()));
		address.appendChild(makeElement("city", addy.getCity()));
		address.appendChild(makeElement("state", addy.getState()));
		address.appendChild(makeElement("zipCode", addy.getZipCode()));
		address.appendChild(makeElement("country", addy.getCountry()));

		return address;

	}

	public static Node makeEmails(HashSet<String> emails) {
		Element emailNode = doc.createElement("emails");
		for (String em : emails) {
			emailNode.appendChild(makeElement("email", em));
		}
		return emailNode;
	}

}
