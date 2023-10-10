package fr.jasmin.model.dao.interfaces;

import java.util.List;

import fr.jasmin.entity.Address;
import fr.jasmin.entity.BankCard;
import fr.jasmin.entity.User;

public interface IUserDao {
	
	User addUser(User user) throws Exception ;
	User getUser(Integer id) throws Exception;
	User getUserByEmail(String email) throws Exception;
	void updateUser(User user) throws Exception;
	void removeUser(Integer id) throws Exception;
	void removeUser(User user) throws Exception;
	List<User> getUsers() throws Exception;
	List<User> getUsersByProfile(String profile) throws Exception;
	void addAddress(Address address) throws Exception;
	Address getAddressByUserId(Integer userId) throws Exception;
	void updateAddress(Address address) throws Exception;
	BankCard addCarte(BankCard carte) throws Exception;
	BankCard updateCarte(BankCard carte) throws Exception;
	BankCard getCarteByUserId(Integer userId) throws Exception;


}
