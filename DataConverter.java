package com.mgg;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import java.util.HashSet;

public class DataConverter {

	public static void main(String args[]) {

		// Persons section
		ReadCSV personsCSV = new ReadCSV("data/Persons.csv");
		ArrayList<String> personsCSVArray = personsCSV.getData();

		ArrayList<Person> persons = new ArrayList<>();
		for (int n = 0; n < personsCSV.getMemberCount(); n++) {
			String personTokens[] = personsCSVArray.get(n).split(",");
			String code = personTokens[0];
			String type = personTokens[1];
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
			persons.add(new Person(code, type, lastName, firstName, street, city, state, zipCode, country, emails));
		}

		// Stores section
		ReadCSV storesCSV = new ReadCSV("data/Stores.csv");
		ArrayList<String> storesCSVArray = storesCSV.getData();

		ArrayList<Store> stores = new ArrayList<>();
		for (int k = 0; k < storesCSV.getMemberCount(); k++) {
			String storeTokens[] = storesCSVArray.get(k).split(",");
			String storeCode = storeTokens[0];
			String managerCode = storeTokens[1];
			String street = storeTokens[2];
			String city = storeTokens[3];
			String state = storeTokens[4];
			String zipCode = storeTokens[5];
			String country = storeTokens[6];
			stores.add(new Store(storeCode, managerCode, street, city, state, zipCode, country));
		}

		// Items section
		ReadCSV itemsCSV = new ReadCSV("data/Items.csv");
		ArrayList<String> itemCSVArray = itemsCSV.getData();

		ArrayList<Item> items = new ArrayList<>();
		for (int k = 0; k < itemsCSV.getMemberCount(); k++) {
			String itemTokens[] = itemCSVArray.get(k).split(",");
			String code = itemTokens[0];
			String type = itemTokens[1];
			String name = itemTokens[2];
			if (itemTokens.length > 3) {
				double basePrice = Double.parseDouble(itemTokens[3]);
				items.add(new Item(code, type, name, basePrice));
			} else {
				items.add(new Item(code, type, name));
			}
		}

		// Write everything to its respective file
		try {
			new WriteXML("data/Persons.xml", persons, "persons");
			new WriteXML("data/Stores.xml", stores, "stores");
			new WriteXML("data/Items.xml", items, "items");
			new WriteJSON("data/Persons.json", persons, "persons");
			new WriteJSON("data/Stores.json", stores, "stores");
			new WriteJSON("data/Items.json", items, "items");
		} catch (ParserConfigurationException p) {
			p.printStackTrace();
		}
		
		System.out.println("complete");

	}

}