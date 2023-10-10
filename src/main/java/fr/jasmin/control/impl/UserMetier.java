package fr.jasmin.control.impl;

import java.util.List;

import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.encryption.algo.EncryptionAlgorithm;
import fr.jasmin.entity.Address;
import fr.jasmin.entity.BankCard;
import fr.jasmin.entity.ItemCart;
import fr.jasmin.entity.User;
import fr.jasmin.model.dao.impl.UserDao;
import fr.jasmin.model.dao.interfaces.IUserDao;

public class UserMetier implements IUserMetier {
	IUserDao userDao = new UserDao();
	User user = null;

	@Override
	public User addUser(User user, String password) throws Exception {
		if (user == null) {
			throw new NullPointerException("Le user à créer ne doit pas être null !");
		}

		// cryptage du password et ajouter le mot de passe crypté dans le user
		user.setPassword(EncryptionAlgorithm.encrypt(password));

//		List<BankCard> bankCardList = user.getBankCardList();
//		for (BankCard bk : bankCardList) {
//			bk.encryptCryptogram();
//			bk.encryptNumber();
//		}

		user.setLastName(user.getLastName().toUpperCase());
		user.setFirstName(user.getFirstName().substring(0, 1).toUpperCase()
				.concat(user.getFirstName().substring(1).toLowerCase()));

		// appeler la dao

		userDao.addUser(user);

		return user;
	}

	//=============================================================
	@Override
	public User getUserById(Integer userId) throws Exception {
		
		User user = userDao.getUser(userId);
		if (user == null) {
			return null;
		}
		return user;
	}
	//=============================================================
	@Override
	public List<User> getUsers() throws Exception {

		return userDao.getUsers();
	}
	//=============================================================
	@Override
	public List<User> getUsersByProfile(String profile) throws Exception {
		
		return userDao.getUsersByProfile(profile);
	}
	
	//=============================================================
	@Override
	public Address getAddressByUserId(Integer userId) throws Exception {
				
	Address address = userDao.getAddressByUserId(userId);
		if (address == null) {
			return null;
		}
		return address;
		}
	//============================================================
	@Override
	public BankCard getCarteByUserId(Integer userId) throws Exception {
				
		BankCard carte = userDao.getCarteByUserId(userId);
			if (carte == null) {
			return null;
		}
			return carte;
	}
			
	//=============================================================
	
	@Override
	public User seLoguer(String email, String password) throws Exception {
		User user = userDao.getUserByEmail(email);

		if (user != null) {
			String passwordDecrypt = EncryptionAlgorithm.decrypt(user.getPassword());
			if (!password.equals(passwordDecrypt) || !user.getEmail().equals(email)) {

				return null;
			}
		}
		return user;

	}
	//=============================================================
	
	@Override
	public void deleteUser(User user) throws Exception {
		userDao.removeUser(user);
	}
	//=============================================================
	
	@Override
	public void desactiveUser(User user) throws Exception {
		user.setIsActif(false);
		userDao.updateUser(user);
	}
	//=============================================================
	
	@Override
	public void seDeconncter(User user) throws Exception {
		user = null;

	}
	//=============================================================
	
	@Override
	public void updateUser(User user, String password) throws Exception {
		if (user == null) {
			throw new NullPointerException("Le user à modifier ne doit pas être null !");
		}
		if (!password.isEmpty() && password!= null) {
		user.setPassword(EncryptionAlgorithm.encrypt(password));
		}
		user.setLastName(user.getLastName().toUpperCase());
		user.setFirstName(user.getFirstName().substring(0, 1).toUpperCase()
				.concat(user.getFirstName().substring(1).toLowerCase()));

		userDao.updateUser(user);
	}
	//=============================================================
	
	@Override
	public void updateAddress(Address address) throws Exception {
		userDao.updateAddress(address);
	}
	
	//=============================================================
	
	@Override
	public void addCarte(BankCard carte, String reelNumber, String reelCryptogram) throws Exception {
		
		if (carte == null) {
			throw new NullPointerException("La carte ne doit pas être null !");
		}
		
		carte.setCryptogram(EncryptionAlgorithm.encrypt(reelCryptogram));
		carte.setNumber(EncryptionAlgorithm.encrypt(reelNumber));
		
		userDao.addCarte(carte);
	}
	//=============================================================
	
		@Override
		public void updateCarte(BankCard carte, String reelNumber, String reelCryptogram) throws Exception {
			
			if (carte == null) {
				throw new NullPointerException("La carte ne doit pas être null !");
			}
			
			carte.setCryptogram(EncryptionAlgorithm.encrypt(reelCryptogram));
			carte.setNumber(EncryptionAlgorithm.encrypt(reelNumber));
			
			userDao.updateCarte(carte);
		}
	//=============================================================
	
	@Override
	public void addPanier(List<ItemCart> panier) throws Exception {
//		user.getPanier();
		userDao.updateUser(user);

	}


//=============================================================

	@Override
	public void addAddress(Address address) throws Exception {
		
		if (address == null) {
			throw new NullPointerException("L'address ne doit pas être null !");
		}
		userDao.addAddress(address);
		
	}

}
