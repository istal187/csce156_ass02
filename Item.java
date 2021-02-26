package com.mgg;

public class Item extends Object {

	private String code;
	private String type;
	private String name;
	private double basePrice;

	public Item(String code, String type, String name, double basePrice) {
		this.code = code;
		if (type.equals("PN")) {
			this.type = "NewProduct";
		} else if (type.equals("PU")) {
			this.type = "UsedProduct";
		} else if (type.equals("PG")) {
			this.type = "GiftCard";
		} else if (type.equals("SV")) {
			this.type = "Service";
		} else if (type.equals("SB")) {
			this.type = "Subscription";
		} else {
			System.out.println("Type not recognized");
		}
		this.name = name;
		this.basePrice = basePrice;
	}

	public Item(String code, String type, String name) {
		this(code, type, name, 0.0);
	}

	@Override
	public String toString() {
		return "Item [code=" + code + ", type=" + type + ", name=" + name + ", basePrice=" + basePrice + "]";
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public double getBasePrice() {
		return basePrice;
	}

}
