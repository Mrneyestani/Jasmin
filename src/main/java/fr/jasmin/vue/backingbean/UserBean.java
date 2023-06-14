package fr.jasmin.vue.backingbean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import fr.jasmin.control.impl.UserMetier;
import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.entity.Address;
import fr.jasmin.entity.User;
import fr.jasmin.enums.ProfileUserEnum;

@ManagedBean(name = "userbean")
@SessionScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private final IUserMetier userMetier = new UserMetier();
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String email;
	private String password;
	private String passwordConfirmation;
	private Boolean isActif;
	private String profile;
	private String phone;
	private String number;
	private String street;
	private String city;
	private String postalCode;
	private String messageSuccess;
	private String messageError;
	private User connectedUser;

	public UserBean() {
		messageSuccess = "";
		messageError = "";
	}

	public String addUserClient() throws Exception {
		if (!password.equals(passwordConfirmation)) {
			messageError = "Les deux mots de passe ne correspondent pas ! Veuillez réessayer.";
			messageSuccess = "";
			return "";
		}

		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setBirthDate(birthDate);
		user.setPhone(phone);
		user.setProfile(ProfileUserEnum.CLIENT.getValue());
		user.setIsActif(true);
		Address address = new Address();
		address.setNumber(number);
		address.setCity(city);
		address.setStreet(street);
		address.setPostalCode(postalCode);

		address.setUser(user);
		user.getAddresses().add(address);

		userMetier.addUser(user, password);
		messageSuccess = "User créé avec succès.";
		messageError = "";
		initializeUser();

		return "signIn.xhtml";

	}
	public String addUser() throws Exception {
		if (!password.equals(passwordConfirmation)) {
			messageError = "Les deux mots de passe ne correspondent pas ! Veuillez réessayer.";
			messageSuccess = "";
			return "";
		}
		
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setBirthDate(birthDate);
		user.setPhone(phone);
		user.setProfile(profile);
		user.setIsActif(true);
		userMetier.addUser(user, password);
		messageSuccess = "User créé avec succès.";
		messageError = "";
		initializeUser();
		
		
		return "";
		
	}

	
	public void initializeUser() {
		this.lastName = "";
		this.firstName = "";
		this.email = "";
		this.phone = "";
		this.birthDate = null;
		this.isActif = null;
		this.password = "";
		this.passwordConfirmation = "";
		this.city = "";
		this.street = "";
		this.number = "";
		this.postalCode = "";
//		this.messageSuccess = "";
//		this.messageError = "";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Boolean getIsActif() {
		return isActif;
	}

	public void setIsActif(Boolean isActif) {
		this.isActif = isActif;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMessageSuccess() {
		return messageSuccess;
	}

	public void setMessageSuccess(String messageSuccess) {
		this.messageSuccess = messageSuccess;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public User getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(User connectedUser) {
		this.connectedUser = connectedUser;
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

}
