package com.mgg;

import java.util.HashSet;

public class Person {

	private String code;
	private String type;
	private String lastName;
	private String firstName;
	private Address address;
	private HashSet<String> emails;

	public Person(String code, String type, String lastName, String firstName, String street, String city, String state,
			String zipCode, String country, HashSet<String> emails) {

		this.code = code;
		if (type.equals("C")) {
			this.type = "Customer";
		} else if (type.equals("G")) {
			this.type = "GoldCustomer";
		} else if (type.equals("P")) {
			this.type = "PlatinumCustomer";
		} else if (type.equals("E")) {
			this.type = "Employee";
		} else if (type.equals("manager")) {
			this.type = "manager";
		} else {
			System.out.println("Type not recognized");
		}
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = new Address(street, city, state, zipCode, country);
		this.emails = emails;
	}

	@Override
	public String toString() {
		return "Person [code=" + code + ", type=" + type + ", lastName=" + lastName + ", firstName=" + firstName
				+ ", address=" + address + ", emails=" + emails + "]";
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public Address getAddress() {
		return address;
	}

	public HashSet<String> getEmails() {
		return emails;
	}

}
