package fr.jasmin.vue.backingbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import fr.jasmin.entity.Address;

@ManagedBean(name = "addressbean")
public class AddressBean {

	private String number;
	private String street;
	private String city;
	private String postalCode;

	private static List<Address> addresses = new ArrayList<Address>();

	public AddressBean() {
	}

	public void addAddress() {
		Address address = new Address(number, street, city, postalCode);
		addresses.add(address);
		initializeAdress();
	}

	public void initializeAdress() {
		this.number = "";
		this.street = "";
		this.postalCode = "";
		this.city = "";
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

}
