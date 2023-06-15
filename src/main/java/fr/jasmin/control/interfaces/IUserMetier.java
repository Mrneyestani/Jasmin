package fr.jasmin.control.interfaces;

import java.util.List;

import fr.jasmin.entity.ItemCart;
import fr.jasmin.entity.User;

public interface IUserMetier {

	User addUser(User user, String password) throws Exception;

	User seLoguer(String email, String password) throws Exception;

	void deleteUser(User user) throws Exception;

	void seDeconncter(User user) throws Exception;

	void updateUser(User user) throws Exception;

	void addPanier(List<ItemCart> panier) throws Exception;
}
