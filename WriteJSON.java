package com.mgg;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONArray;

public class WriteJSON {

	private String fileName;
	private ArrayList data;
	private String type;
	private static FileWriter file;

	public WriteJSON(String fileName, ArrayList data, String type) {
		this.fileName = fileName;
		this.data = data;
		this.type = type;

		JSONArray body = new JSONArray();
		for (int k = 0; k < data.size(); k++) {
			JSONObject inner = new JSONObject();
			if (this.type.equals("items")) {
				Item item = (Item) data.get(k);
				if (item.getType().equals("GiftCard")) {
				} else if (item.getType().equals("Service")) {
					inner.put("hourlyRate", item.getBasePrice());
				} else if (item.getType().equals("Subscription")) {
					inner.put("annualFee", item.getBasePrice());
				} else {
					inner.put("basePrice", item.getBasePrice());
				}
				inner.put("itemCode", item.getCode());
				inner.put("name", item.getName());
			} else if (this.type.equals("persons")) {
				inner = makePerson((Person) data.get(k));
			} else if (this.type.equals("stores")) {
				Store store = (Store) data.get(k);
				inner.put("storeCode", store.getStoreCode());
				inner.put("manager", makeManager(store.getManagerCode()));
				inner.put("address", makeAddress(store.getAddress()));
			}

			body.put(inner);

		}
		JSONObject outer = new JSONObject();
		outer.put(this.type, body);

		try {
			file = new FileWriter(this.fileName);
			file.write(outer.toString(4));
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static JSONObject makeAddress(Address addy) {
		JSONObject address = new JSONObject();
		address.put("street", addy.getStreet());
		address.put("city", addy.getCity());
		address.put("state", addy.getState());
		address.put("zipCode", addy.getZipCode());
		address.put("country", addy.getCountry());
		return address;
	}

	public static JSONArray makeEmails(HashSet emailSet) {
		JSONArray emails = new JSONArray();
		Iterator<String> items = emailSet.iterator();
		while (items.hasNext()) {
			emails.put(items.next());
		}
		return emails;
	}

	public static JSONObject makeManager(String managerCode) {

		ReadCSV personsCSV = new ReadCSV("data/Persons.csv");
		ArrayList<String> personsCSVArray = personsCSV.getData();
		JSONObject manager = new JSONObject();
		for (int n = 0; n < personsCSV.getMemberCount(); n++) {
			String personTokens[] = personsCSVArray.get(n).split(",");
			String code = personTokens[0];
			if (code.equals(managerCode)) {
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
				manager = makePerson(new Person(code, "manager", lastName, firstName, street, city, state, zipCode,
						country, emails));
			}
		}
		return manager;
	}

	public static JSONObject makePerson(Person person) {
		JSONObject personObject = new JSONObject();
		personObject.put("personCode", person.getCode());
		personObject.put("lastName", person.getLastName());
		personObject.put("firstName", person.getFirstName());
		personObject.put("address", makeAddress(person.getAddress()));
		personObject.put("emails", makeEmails(person.getEmails()));
		return personObject;
	}

}
