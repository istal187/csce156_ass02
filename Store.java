package com.mgg;

public class Store {

	private String storeCode;
	private String managerCode;
	private Address address;

	public Store(String code, String managerCode, String street, String city, String state, String zipCode,
			String country) {
		this.storeCode = code;
		this.managerCode = managerCode;
		this.address = new Address(street, city, state, zipCode, country);
	}

	@Override
	public String toString() {
		return "Store [storeCode=" + storeCode + ", managerCode=" + managerCode + ", address=" + address + "]";
	}

	public String getStoreCode() {
		return storeCode;
	}

	public String getManagerCode() {
		return managerCode;
	}

	public Address getAddress() {
		return address;
	}

}
