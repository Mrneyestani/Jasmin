package fr.jasmin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "number", length = 6, nullable = false)
	private String number;

	@Column(name = "street", length = 45, nullable = false)
	private String street;

	@Column(name = "city", length = 45, nullable = false)
	private String city;

	@Column(name = "country", length = 45)
	private String country;
	
	@Column(name = "postal_code", length = 5, nullable = false)
	private String postalCode;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Address() {

	}

	public Address(String number, String street, String postalCode, String city, String country) {
		this.number = number;
		this.street = street;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", number=" + number + ", street=" + street + ", city=" + city + ", country="
				+ country + ", postalCode=" + postalCode + ", user=" + user + "]";
	}

}
