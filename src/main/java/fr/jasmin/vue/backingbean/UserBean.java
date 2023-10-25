package fr.jasmin.vue.backingbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import fr.jasmin.control.impl.UserMetier;
import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.encryption.algo.EncryptionAlgorithm;
import fr.jasmin.entity.Address;
import fr.jasmin.entity.BankCard;
import fr.jasmin.entity.Item;
import fr.jasmin.entity.User;
import fr.jasmin.enums.ProfileUserEnum;
import fr.jasmin.utils.Dates;
import fr.jasmin.utils.Utils;

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
	private String profileSelected;
	private String phone;
	private String number;
	private String street;
	private String city;
	private String country;
	private String postalCode;
	private String nameOwner;
	private String firstNameOwner;
	private String reelNumber;
	private Date dateFinValidite;
	private String reelCryptogram;
	private String messageSuccess;
	private String messageError;
	private User connectedUser;
	private User UserActuel;
	private List<User> listUsersByProfile;
	
	public UserBean() {
		messageSuccess = "";
		messageError = "";
	setProfile(ProfileUserEnum.CLIENT.getValue());
		
		try {
			listUsersByProfile = userMetier.getUsersByProfile(this.getProfile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	//==========================================================
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
		address.setCountry(country);
		address.setStreet(street);
		address.setPostalCode(postalCode);
		address.setUser(user);

		userMetier.addUser(user, password);
		userMetier.addAddress(address);
		messageSuccess = "User créé avec succès.";
		messageError = "";
		initializeUser();

		return "signIn.xhtml";

	}
	
	//==========================================================
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
		user.setIsActif(isActif);
		
		Address address = new Address();
		address.setNumber(number);
		address.setCity(city);
		address.setCountry(country);
		address.setStreet(street);
		address.setPostalCode(postalCode);
		address.setUser(user);
		
		userMetier.addUser(user, password);
		userMetier.addAddress(address);
		messageSuccess = "User : "+ user.getFirstName()+" "+user.getLastName()+" a créé avec succes";
		messageError = "";
		initializeUser();
		
		return "gestion_admin.xhtml";
		
	}
	
	//==========================================================
	
	public String profileChange(ValueChangeEvent e) {
		listUsersByProfile = new ArrayList<User>();
		this.setProfile((String) e.getNewValue());
	
		try {
			listUsersByProfile = userMetier.getUsersByProfile(this.getProfile());
			this.getListUsersByProfile().forEach(u -> System.out.println("  -> " + u.getFirstName()+ " " +u.getLastName()));
			Utils.trace("ListUsersByProfile : %s\n", this.getListUsersByProfile());
		} catch (Exception exception) {
				messageSuccess = "";
				messageError = "Erreur lors de la récupération de categorie !\n" + exception.getMessage();
				exception.printStackTrace();
		}
		initializeUser();
		return "";
}
	//==========================================================
	
	public String selectConectedUser() {
		try {
		
			setConnectedUser(getUserClientById());
			Utils.trace("connected user  : %s\n", getConnectedUser());
			Utils.trace("Adresse user  : %s\n", getUserClientById().getAddresses());
			this.firstName = getConnectedUser().getFirstName();
			this.lastName = getConnectedUser().getLastName();
			this.email = getConnectedUser().getEmail();
			this.birthDate = getConnectedUser().getBirthDate();
			this.phone = getConnectedUser().getPhone();
			Address addressConnectedUser = new Address();
			addressConnectedUser = userMetier.getAddressByUserId(getUserClientById().getId());
			this.number = addressConnectedUser.getNumber();
			this.street = addressConnectedUser.getStreet();
			this.postalCode = addressConnectedUser.getPostalCode();
			this.city = addressConnectedUser.getCity();
			this.country = addressConnectedUser.getCountry();

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la selection de user actuel!\n" + e.getMessage();
			e.printStackTrace();
		}
		return "modificationUserAdress.xhtml";
	}
	//==========================================================
	
	public String selectUserActuel() {
		try {
			
			
			Utils.trace("connected user  : %s\n", getUserActuel());
		
			this.firstName = getUserActuel().getFirstName();
			this.lastName = getUserActuel().getLastName();
			this.email = getUserActuel().getEmail();
			this.birthDate = getUserActuel().getBirthDate();
			this.phone = getUserActuel().getPhone();
			this.isActif = getUserActuel().getIsActif();
			this.profile = getUserActuel().getProfile();
			Address addressUserActuel = new Address();
			addressUserActuel = userMetier.getAddressByUserId(getUserActuel().getId());
			this.number = addressUserActuel.getNumber();
			this.street = addressUserActuel.getStreet();
			this.postalCode = addressUserActuel.getPostalCode();
			this.city = addressUserActuel.getCity();
			this.country = addressUserActuel.getCountry();
			
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la selection de user actuel!\n" + e.getMessage();
			e.printStackTrace();
		}
		return "gestion_admin_updateUser.xhtml";
	}
	
//=========================================================================
	public String selectCarteUser() {
		
		BankCard carte = new BankCard();
		try {
			carte = userMetier.getCarteByUserId(LoginBean.connectedUser.getId());
			if (carte == null) {
				return "carteBancaire.xhtml";
			}
			
			Utils.trace("Carte user  : %s\n", carte);
			this.firstNameOwner = carte.getFirstNameOwner();
			this.nameOwner = carte.getNameOwner();
			this.dateFinValidite = carte.getDateFinValidite();
			this.reelNumber = EncryptionAlgorithm.decrypt(carte.getNumber());
			this.reelCryptogram = EncryptionAlgorithm.decrypt(carte.getCryptogram());

		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la selection de user actuel!\n" + e.getMessage();
			e.printStackTrace();
		}
		initializeMessages();
		return "carteBancaire.xhtml";
	}
	

//==============================================================

	public String updateConnectedUser() {
		if (!password.equals(passwordConfirmation)) {
			messageError = "Les deux mots de passe ne correspondent pas ! Veuillez réessayer.";
			messageSuccess = "";
			return "";
		}
		try {
			setConnectedUser(getUserClientById());
			getConnectedUser().setFirstName(this.firstName);
			getConnectedUser().setLastName(this.lastName);
			getConnectedUser().setEmail(this.email);
			getConnectedUser().setBirthDate(this.birthDate);
			getConnectedUser().setPhone(this.phone);
			
			userMetier.updateUser(getConnectedUser(), this.password);

			Address addressUpdated = new Address();
			addressUpdated = userMetier.getAddressByUserId(LoginBean.connectedUser.getId());
			addressUpdated.setNumber(this.number);
			addressUpdated.setStreet(this.street);
			addressUpdated.setPostalCode(this.postalCode);
			addressUpdated.setCity(this.city);
			addressUpdated.setCountry(this.country);
			userMetier.updateAddress(addressUpdated);

			messageSuccess = "La modification a été fait avec succès.";
			messageError = "";

	} catch (Exception e) {
		messageSuccess = "";
		messageError = "Erreur lors de la modification de user actuel!\n" + e.getMessage();
		e.printStackTrace();
	}
	return "";
	
	}
//==============================================================
	
	public String updateUserActuel() {
		
		if (!password.equals(passwordConfirmation)) {
			messageError = "Les deux mots de passe ne correspondent pas ! Veuillez réessayer.";
			messageSuccess = "";
			return "";
		}

		try {
			
			getUserActuel().setProfile(this.profile);
			getUserActuel().setFirstName(this.firstName);
			getUserActuel().setLastName(this.lastName);
			getUserActuel().setEmail(this.email);
			getUserActuel().setBirthDate(this.birthDate);
			getUserActuel().setPhone(this.phone);
			getUserActuel().setIsActif(this.isActif);
			
			userMetier.updateUser(getUserActuel(), this.password);
			
			Address addressUpdated = new Address();
			addressUpdated = userMetier.getAddressByUserId(getUserActuel().getId());
			addressUpdated.setNumber(this.number);
			addressUpdated.setStreet(this.street);
			addressUpdated.setPostalCode(this.postalCode);
			addressUpdated.setCity(this.city);
			addressUpdated.setCountry(this.country);
			userMetier.updateAddress(addressUpdated);
			
			messageSuccess = "La modification a été fait avec succès.";
			messageError = "";
			
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la modification de user actuel!\n" + e.getMessage();
			e.printStackTrace();
		}
		return "gestion_admin.xhtml";
		
	}
	
//=============================================================
	public User getUserClientById() {
		
		try {
		this.setConnectedUser(userMetier.getUserById(LoginBean.connectedUser.getId()));
		
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors de la récupération du user!\n" + e.getMessage();
			e.printStackTrace();
		}
		return connectedUser;
	}
	
	//=============================================================
		public List<User> getListUsers() {
			
			List<User> users = new ArrayList<User>();
			try {
				users = userMetier.getUsers();
			
			} catch (Exception e) {
				messageSuccess = "";
				messageError = "Erreur lors de la récupération du user!\n" + e.getMessage();
				e.printStackTrace();
			}
			return users;
		}

	//=============================================================
		
		public String deleteUser() {
			
			try {
			userMetier.deleteUser(getUserActuel());
			
			} catch (Exception e) {
				messageSuccess = "";
				messageError = "Erreur lors de la suppression du user!\n" + e.getMessage();
				e.printStackTrace();
			}
			return "";
		}
	//=============================================================
		
		public String desactiveUser() {
			
			try {
				userMetier.desactiveUser(getUserActuel());
				
			} catch (Exception e) {
				messageSuccess = "";
				messageError = "Erreur lors désactivation du user!\n" + e.getMessage();
				e.printStackTrace();
			}
			return "";
		}
		
		
	//=============================================================
		public BankCard getCarteByUserId() {
			
			BankCard carte = new BankCard();
			try {
				
				carte = userMetier.getCarteByUserId(LoginBean.connectedUser.getId());
				
				if (userMetier.getCarteByUserId(LoginBean.connectedUser.getId()) == null) {
					messageError = "Vous n'avez pas enregistré une carte !\n Veuillez ajouter une carte !";
					messageSuccess = "";
					return carte;
				}
			
			} catch (Exception e) {
				messageSuccess = "";
				messageError = "Erreur lors de récupération de la carte!\n" + e.getMessage();
				e.printStackTrace();
			}
			return carte;
		}
	//=============================================================
		public String decrypteNumberCarte() {
			
			BankCard carte = new BankCard();
			try {
				
				if (userMetier.getCarteByUserId(LoginBean.connectedUser.getId()) == null) {
					messageError = "Vous n'avez pas enregistré une carte ! Veuillez ajouter une carte !";
					messageSuccess = "";
					return "";
				}
				carte = userMetier.getCarteByUserId(LoginBean.connectedUser.getId());
				this.reelNumber = EncryptionAlgorithm.decrypt(carte.getNumber());
				
			} catch (Exception e) {
				messageSuccess = "";
				messageError = "Erreur lors de récupération de la carte!\n" + e.getMessage();
				e.printStackTrace();
			}
			return reelNumber ;
		}
//=============================================================	

	public String addCarte() throws Exception {
		
		try {
			BankCard carte = new BankCard();
			
			if (userMetier.getCarteByUserId(LoginBean.connectedUser.getId()) != null) {
				messageError = "il existe déjà une carte vous pouvez la modifier !";
				messageSuccess = "";
				return "";
			}
			
			carte.setNameOwner(this.nameOwner);
			carte.setFirstNameOwner(this.firstNameOwner);
			carte.setDateFinValidite(Dates.convertDateUtilToSql(dateFinValidite));
			carte.setUser(getUserClientById());
			userMetier.addCarte(carte, reelNumber, reelCryptogram);
			messageSuccess = "La carte a été ajoutée avec succès.";
			messageError = "";
			
		} catch (Exception e) {
			messageSuccess = "";
			messageError = "Erreur lors d'ajouter une carte!\n" + e.getMessage();
			e.printStackTrace();
		}
		return "";
		
	}
	//=============================================================	
		public String updateCarte() throws Exception {
				
				BankCard carte = new BankCard();
				try {
					carte = userMetier.getCarteByUserId(LoginBean.connectedUser.getId());
					
					if (carte == null) {
						messageError = "Il n'existe pas une carte à modifier ! Veuillez ajouter une carte !";
						messageSuccess = "";
						return "";
					}
				
				carte.setNameOwner(nameOwner);
				carte.setFirstNameOwner(firstNameOwner);
				carte.setDateFinValidite(Dates.convertDateUtilToSql(dateFinValidite));
				carte.setUser(getUserClientById());
				userMetier.updateCarte(carte, reelNumber, reelCryptogram);
				messageSuccess = "La carte a été mise à jour avec succès.";
				messageError = "";
			} catch (Exception e) {
				messageSuccess = "";
				messageError = "Erreur lors de la mise à jour de la carte!\n" + e.getMessage();
				e.printStackTrace();
			}
			return "";
			
		}
//===========================================================	
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
		this.country = "";
		this.street = "";
		this.number = "";
		this.postalCode = "";
		this.messageSuccess = "";
		this.messageError = "";
	}
//===========================================================	
	public void initializeMessages() {
	
		this.messageSuccess = "";
		this.messageError = "";
	}
//===========================================================	
		public String retourEnCommand() {
		
			messageSuccess = "";
			messageError = "";
			
			return "command.xhtml";
		}
//===========================================================	
		public String goToAddUserAdmin() {
			
			initializeUser();
			
			return "gestion_admin_addUser.xhtml";
		}
		
//===========================================================	
		public String BackToGestionAdmin() {
			
			initializeUser();
			
			return "gestion_admin.xhtml";
		}

//=======================getter and setter ===================
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNameOwner() {
		return nameOwner;
	}

	public void setNameOwner(String nameOwner) {
		this.nameOwner = nameOwner;
	}

	public String getFirstNameOwner() {
		return firstNameOwner;
	}

	public void setFirstNameOwner(String firstNameOwner) {
		this.firstNameOwner = firstNameOwner;
	}

	public String getReelNumber() {
		return reelNumber;
	}

	public void setReelNumber(String reelNumber) {
		this.reelNumber = reelNumber;
	}

	public Date getDateFinValidite() {
		return dateFinValidite;
	}

	public void setDateFinValidite(Date dateFinValidite) {
		this.dateFinValidite = dateFinValidite;
	}

	public String getReelCryptogram() {
		return reelCryptogram;
	}

	public void setReelCryptogram(String reelCryptogram) {
		this.reelCryptogram = reelCryptogram;
	}
	
	public List<User> getListUsersByProfile() {
		return listUsersByProfile;
	}
	public void setListUsersByProfile(List<User> listUsersByProfile) {
		this.listUsersByProfile = listUsersByProfile;
	}
	public String getProfileSelected() {
		return profileSelected;
	}
	public void setProfileSelected(String profileSelected) {
		this.profileSelected = profileSelected;
	}
	public List<String> getProfiles() {
		return ProfileUserEnum.getProfiles();
	}
	public User getUserActuel() {
		return UserActuel;
	}
	public void setUserActuel(User userActuel) {
		UserActuel = userActuel;
	}
	
}
