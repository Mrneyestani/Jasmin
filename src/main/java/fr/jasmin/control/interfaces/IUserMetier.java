package fr.jasmin.control.interfaces;

import java.util.List;

import fr.jasmin.entity.Address;
import fr.jasmin.entity.BankCard;
import fr.jasmin.entity.ItemCart;
import fr.jasmin.entity.User;

public interface IUserMetier {

	User addUser(User user, String password) throws Exception;

	User seLoguer(String email, String password) throws Exception;

	void deleteUser(User user) throws Exception;

	void seDeconncter(User user) throws Exception;

	void updateUser(User user, String password) throws Exception;

	void addPanier(List<ItemCart> panier) throws Exception;

	User getUserById(Integer userId) throws Exception;

	Address getAddressByUserId(Integer userId) throws Exception;

	void addAddress(Address address) throws Exception;
	
	void updateAddress(Address address) throws Exception;

	void addCarte(BankCard carte, String reelNumber, String reelCryptogram) throws Exception;

	void updateCarte(BankCard carte, String reelNumber, String reelCryptogram) throws Exception;

	BankCard getCarteByUserId(Integer userId) throws Exception;

	List<User> getUsers() throws Exception;

	List<User> getUsersByProfile(String profile) throws Exception;

	void desactiveUser(User user) throws Exception;

}
