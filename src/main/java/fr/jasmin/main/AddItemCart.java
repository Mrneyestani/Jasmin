package fr.jasmin.main;

import java.util.List;

import fr.jasmin.entity.Item;
import fr.jasmin.entity.ItemCart;
import fr.jasmin.model.dao.impl.CategoryDao;
import fr.jasmin.model.dao.impl.ItemCartDao;
import fr.jasmin.model.dao.impl.UserDao;
import fr.jasmin.model.dao.interfaces.ICategoryDao;
import fr.jasmin.model.dao.interfaces.IItemCartDao;
import fr.jasmin.model.dao.interfaces.IUserDao;

public class AddItemCart {

	public static void main(String[] args) {
		// ADD ItemCart avec lien entre user et item

		System.out.println("***********************début de test**********************");

		IItemCartDao itemCartDao = new ItemCartDao();
		ICategoryDao categoryDao = new CategoryDao();
		IUserDao userDao = new UserDao();

		try {

			ItemCart itemCart = new ItemCart(5);
			// récuperer l'utilisateur
			itemCart.setUser(userDao.getUser(3));

			// récuperer l'article d'un catégorie
			List<Item> listItem = categoryDao.getCategory(3).getItems();
			itemCart.setItem(listItem.get(0));

//			for (Item item : listItem) {
//					System.out.println(item);
//					
//						}
//			System.out.println(" **********************************");
//			System.out.println (listItem.get(0));

			itemCartDao.addItemCart(itemCart);
			System.out.println(itemCart);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
