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
import fr.jasmin.utils.Utils;

public class MainCRUDitemCart {

	public static void main(String[] args) {
		// ADD ItemCart avec lien entre user et item ok
		// Remove ItemCart by Id and ItemCart ok
		// get ItemCart ok
		// Update ItemCart ok

		System.out.println("***********************début de test**********************");

		IItemCartDao itemCartDao = new ItemCartDao();
		ICategoryDao categoryDao = new CategoryDao();
		IUserDao userDao = new UserDao();

		try {

//			ItemCart itemCart1 = new ItemCart();
//			// récuperer l'utilisateur
//			itemCart1.setQuantite(5);
//			itemCart1.setUser(userDao.getUser(2));
//
//			// récuperer l'article d'un catégorie
//			List<Item> listItem1 = categoryDao.getCategory(30).getItems();
//			itemCart1.setItem(listItem1.get(0));
//
//			Utils.trace("ItemCart1  : %s\n", itemCart1);
//
//			System.out.println(" **********************************");
//
//			Utils.trace("listItem ajoute au panier au panier  : %s\n", listItem1);
//
//			System.out.println(" ****************Add ItemCart******************");
//
//			itemCartDao.addItemCart(itemCart1);
//
//			System.out.println(" ****************Get List ItemCart******************");
//			List<ItemCart> listItemCart = itemCartDao.getItemCartList();
//
//			Utils.trace("listItemCart  : %s\n", listItemCart);
//
//			
//			System.out.println(" ****************Delet ItemCart******************");
//
//			Utils.trace("ItemCart2 avant suppresion  : %s\n", itemCartDao.getItemCart(1));
//
//			itemCartDao.removeItemCart(1);
//
//			Utils.trace("ItemCart2 après suppresion  : %s\n", itemCartDao.getItemCart(1));
//
//			
//			System.out.println(" ****************Update ItemCart******************");
//
//			Utils.trace("ItemCart avant update  : %s\n", itemCartDao.getItemCart(1));
//			ItemCart itemCart2 = new ItemCart();
//			itemCart2 = itemCartDao.getItemCart(1);
//			itemCart2.setQuantite(3);
//			itemCart2.setUser(userDao.getUser(3));
//
//			// récuperer l'article d'un catégorie
//			listItem1 = categoryDao.getCategory(30).getItems();
//			itemCart2.setItem(listItem1.get(1));
//			itemCartDao.updateItemCart(itemCart2);
//
//			Utils.trace("ItemCart2 après update  : %s\n", itemCartDao.getItemCart(1));
			
			System.out.println(" ****************Get ItemCart ByUserId******************");
			
//			ItemCart itemCart = new ItemCart();
			List<ItemCart> panier = itemCartDao.getPanierByUserId(2);
			Utils.trace("Panier : %s\n", panier.size());
			Utils.trace("Panier : %s\n", panier);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
