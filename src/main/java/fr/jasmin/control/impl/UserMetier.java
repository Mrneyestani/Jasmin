package fr.jasmin.control.impl;

import java.util.List;

import fr.jasmin.control.interfaces.IUserMetier;
import fr.jasmin.encryption.algo.EncryptionAlgorithm;
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

		List<BankCard> bankCardList = user.getBankCardList();
		for (BankCard bk : bankCardList) {
			bk.encryptCryptogram();
			bk.encryptNumber();
		}

		user.setLastName(user.getLastName().toUpperCase());
		user.setFirstName(user.getFirstName().substring(0, 1).toUpperCase()
				.concat(user.getFirstName().substring(1).toLowerCase()));

		// appeler la dao

		userDao.addUser(user);

		return user;
	}

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

	@Override
	public void deleteUser(User user) throws Exception {
		user.setIsActif(false);
		userDao.updateUser(user);
	}

	@Override
	public void seDeconncter(User user) throws Exception {
		user = null;

	}

	@Override
	public void updateUser(User user) throws Exception {

	}

	@Override
	public void addPanier(List<ItemCart> panier) throws Exception {
		user.getPanier();
		userDao.updateUser(user);

	}

}
